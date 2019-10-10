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
        /*
         * check if size of the visible part of fortuneList is equal to the actual size of the fortuneList
         *
         * By saying 'visible part', meaning the part of fortune that hasn't been deleted and thus
         * can be returned by GET request.
         *
         * The 'invisible part' are the rightmost part of fortune that has been move to the position of deleted fortune
         * and cannot returned by GET request, but they may still exists in fortuneList until rewrite by newly added fortune
         * They are just invisible by users.
         */
        return fortuneList.size() - rightmostIndex <= 1;
    }

    private void rewriteWithLastValue(int id) {
        /*
         * Rewrite the fortune with id {id} with the rightmost value of fortuneList.
         * */
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
        if (isIdValid(id)) {
            return fortuneList.get(id);
        } else {
            return "Can't get fortune with that id.";
        }
    }

    public String get() {
        /*
         * Randomly get a fortune form visible part of fortuneList.
         * */
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
            rewriteWithLastValue(id);
        }
        rightmostIndex--;
        return true;
    }

    public int add(String fortune_words) {
        /*
         * Add a new fortune into the visible part of fortuneList.
         */
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
        /*
         * Return a String represents the whole fortuneList, including visible and invisible parts
         * */
        StringBuffer text = new StringBuffer();
        for (String vlaue : fortuneList) {
            text.append(vlaue + ", ");
        }
        return text.toString();
    }

    public String getVisFortuneList() {
        /*
         * Return a String represents the visible part of fortuneList
         * */
        StringBuffer visFortuneListStr = new StringBuffer();
        for (int i = 0; i <= rightmostIndex; i++) {
            visFortuneListStr.append(fortuneList.get(i) + ",");
        }
        return visFortuneListStr.toString();
    }

    public String frequencyTest(int totalGetTimes) {
        /*
         * Simulate calling get() function {totalGetTimes} times.
         * Return a String contains:
         * 1. frequency of each fortune occurs;
         * 2. all visible fortunes in fortune container object;
         * 3. all fortunes in fortune container object.
         * */
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
