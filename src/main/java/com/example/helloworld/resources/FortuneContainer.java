package com.example.helloworld.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FortuneContainer implements Serializable {
    private ArrayList<String> fortuneList = new ArrayList<>();
    private int rightmostIndex = -1;
    private Random rand = new Random();

    public FortuneContainer() {

    }

    private boolean isFull() {
        return fortuneList.size() - rightmostIndex <= 1;
    }

    private void swapWithLastValue(int id) {
        String tempFortune = fortuneList.get(rightmostIndex);
        fortuneList.set(id, tempFortune);
    }

    private boolean isIdValid(int id) {
        if (rightmostIndex < 0) {
            System.out.println("Sorry, no fortune yet. You can delete nothing.");
            return false;
        }
        if (id > rightmostIndex || id < 0) {
            System.out.println("Sorry, ID doesn't exists.");
            return false;
        } else {
            return true;
        }
    }

    public String getFortuneById(int id) {
        if (isIdValid(id)){
            return fortuneList.get(id);
        }else{
            return "Can't get fortune with that id.";
        }
    }

    public String get() {
        if (rightmostIndex < 0) {
            return "Sorry, no fortune yet. Please come back later.";
        }
        int id = rand.nextInt(rightmostIndex + 1);
        return getFortuneById(id);
    }


    public boolean delete(int id) {
        if (!isIdValid(id)) {
            return false;
        }
        if (id != rightmostIndex) {
            swapWithLastValue(id);
        }
        rightmostIndex--;
        return true;
    }

    public int add(String fortune_words) {
        if (isFull()) {
            fortuneList.add(fortune_words);
        } else {
            fortuneList.set(rightmostIndex + 1, fortune_words);
        }
        rightmostIndex++;
        return rightmostIndex;
    }


    @Override
    public String toString() {
        StringBuffer text = new StringBuffer();
        for (String vlaue : fortuneList) {
            text.append(vlaue + ", ");
        }
        return text.toString();
    }

    public String getVisFortuneList() {
        StringBuffer visFortuneListStr = new StringBuffer();
        for (int i = 0; i <= rightmostIndex; i++) {
            visFortuneListStr.append(fortuneList.get(i) + ",");
        }
        return visFortuneListStr.toString();
    }

    public String frequencyTest(int totalGetTimes) {
        if (rightmostIndex >= 0) {
            HashMap<String, Integer> hashMap = new HashMap<>();
            for (int i = 0; i < totalGetTimes; i++) {
                String key = get();
                if (hashMap.containsKey(key)) {
                    hashMap.put(key, hashMap.get(key) + 1);
                } else {
                    hashMap.put(key, 1);
                }
            }
            StringBuffer text = new StringBuffer();

            for (Map.Entry elem : hashMap.entrySet()) {
                text.append("Key-> " + elem.getKey() + ", Value-> " + elem.getValue() + "\n");
            }
            text.append("\nReal fortuneList   : " + this.toString() + "\n");
            text.append("\nVisible FortuneList: " + getVisFortuneList() + "\n");
            return text.toString();
        } else {
            return "Sorry, No Fortune. You need to add more fortunes to test.";
        }
    }
}
