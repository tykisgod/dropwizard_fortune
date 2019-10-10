package com.example.helloworld;

import com.example.helloworld.resources.FortuneContainer;

import java.io.*;

public class ObjectIO {

    private static String fortuneContainerLocation = "fortuneContainer.fort";

    public static void WriteObjectToFile(Object serObj) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fortuneContainerLocation);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object was succesfully written to a file");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FortuneContainer initFortuneContainer() {
        FortuneContainer fortuneContainer = new FortuneContainer(-1);
        try {
            System.out.println("Try Reading FortuneContainer!");
            FileInputStream fis = new FileInputStream(fortuneContainerLocation);
            ObjectInputStream ois = new ObjectInputStream(fis);
            fortuneContainer = (FortuneContainer) ois.readObject();
            System.out.println("Value of fortuneContainer is: " + fortuneContainer.toString());
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("Init FortuneContainer!");
            fortuneContainer = new FortuneContainer(100);
            WriteObjectToFile(fortuneContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fortuneContainer;
    }
}
