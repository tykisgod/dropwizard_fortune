package com.example.helloworld;

import com.example.helloworld.resources.FortuneContainer;

import java.io.*;

public class ObjectIO {

    private static final String fortuneContainerLocation = "fortuneContainer.fort";

    public static void WriteObjectToFile(Object serObj) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fortuneContainerLocation);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object was successfully written to a file");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FortuneContainer getFortuneContainer() {
        /*
        * If fortuneContainer object hasn't serialized to local disk, initialize it and serialize it;
        * Otherwise, read it to a new fortuneContainer from disk.
        * */
        FortuneContainer fortuneContainer = new FortuneContainer();
        try {
            System.out.println("Try Reading FortuneContainer!");
            FileInputStream fis = new FileInputStream(fortuneContainerLocation);
            ObjectInputStream ois = new ObjectInputStream(fis);
            fortuneContainer = (FortuneContainer) ois.readObject();
            System.out.println("Value of fortuneContainer is: " + fortuneContainer.toString());
            ois.close();
        } catch (FileNotFoundException | InvalidClassException e) {
            System.out.println("Init FortuneContainer!");
            fortuneContainer = new FortuneContainer();
            System.out.println("Value of fortuneContainer is: " + fortuneContainer.toString());
            WriteObjectToFile(fortuneContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fortuneContainer;
    }
}
