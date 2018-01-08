package se.mah.m11p0121.iotproject;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

class Classifier {
    private J48 tree = new J48();         // new instance of tree
    private int classIndex;
    // create a DenseInstance based on your live Acc and Gyr data
    private DenseInstance instance = new DenseInstance(121); // assuming that you have 120 values + one class label
    private int newValueIndex = 0;

    Classifier(Context context) {
        // load training data
        BufferedReader breader;
        InputStream arffStream = context.getResources().openRawResource(R.raw.train_small);
        // put the address of your training file here
        try {

            breader = new BufferedReader(new InputStreamReader(arffStream));
            Instances train = new Instances(breader);
            classIndex = train.numAttributes() - 1;
            train.setClassIndex(classIndex);

            // build classifier
            tree.buildClassifier(train);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                arffStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    void addValues(String[] newValues) {
        for(int i = 1; i < newValues.length; i++) {
            instance.setValue(newValueIndex++, Integer.parseInt(newValues[i]));
            if(newValueIndex== 120) {
                try {
                    classify(newInstances(instance));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                instance = new DenseInstance(121);
                newValueIndex = 0;
            }
        }
    }

    private Instances newInstances(DenseInstance instance) {
        // create instances for the live instance: first you need to create the attributes
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            attributes.add(new Attribute("AccX" + i));
            attributes.add(new Attribute("AccY" + i));
            attributes.add(new Attribute("AccZ" + i));
            attributes.add(new Attribute("GyrX" + i));
            attributes.add(new Attribute("GyrY" + i));
            attributes.add(new Attribute("GyrZ" + i));
        }
        // pay attention to the order of the gestures that should match your training file
        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("up");
        classValues.add("down");
        classValues.add("left");
        classValues.add("right");
        attributes.add(new Attribute("gesture", classValues));

        // now create the instances
        Instances unlabeled = new Instances("testData", attributes, 120);

        // and here you should add your DenseInstance to the instances
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

        //	Instances unlabeled = new Instances(test);
        unlabeled.add(instance);
        return unlabeled;
    }

    private void classify(Instances unclassified) throws Exception {

        double clsLabel = tree.classifyInstance(unclassified.instance(0));
        unclassified.instance(0).setClassValue(clsLabel);
        Log.d("GESTURECLASS" , "Detected Gesture: " + unclassified.instance(0).attribute(classIndex).value((int) clsLabel));
    }
}
