package com.evolutionary.problems.strings;

public class WordNode {
    String word ;
    int numOfSteps ;

    WordNode (String word, int steps) {
        this.word = word ;
        this.numOfSteps = steps ;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public void setNumOfSteps(int numOfSteps) {
        this.numOfSteps = numOfSteps;
    }
}
