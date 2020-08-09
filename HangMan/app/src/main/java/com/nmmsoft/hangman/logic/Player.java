package com.nmmsoft.hangman.logic;
///////////////////////////
//  Designed and Creared //
//     Naser Elziadna    //
//         Thanks♥       //
///////////////////////////

public class Player {
    private String name;
    private int score;

    public Player() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
