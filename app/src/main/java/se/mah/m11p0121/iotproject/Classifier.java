package se.mah.m11p0121.iotproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.*;

public class Classifier {
    private J48 tree = new J48();         // new instance of tree
    private int classIndex;

    public void setupClassifier() throws Exception {
        // load training data
        BufferedReader breader = null;
        // put the address of your training file here
        breader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/bin/weka/api/train.arff"));
        Instances train = new Instances(breader);
        classIndex = train.numAttributes() - 1;
        train.setClassIndex(classIndex);

        // build classifier
        tree.buildClassifier(train);
    }

    public Instances newInstances() {
        // create a DenseInstance based on your live Acc and Gyr data
        DenseInstance instance = new DenseInstance(121); // assuming that you have 120 values + one class label



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

    public void classify(Instances unclassified) throws Exception {

        double clsLabel = tree.classifyInstance(unclassified.instance(0));
        unclassified.instance(0).setClassValue(clsLabel);
        System.out.println("Detected Gesture: " + unclassified.instance(0).attribute(classIndex).value((int) clsLabel));
    }
}
