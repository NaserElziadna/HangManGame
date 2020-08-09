package com.nmmsoft.hangman.logic;
///////////////////////////
//  Designed and Creared //
//     Naser Elziadna    //
//         Thanks♥       //
///////////////////////////

import java.util.HashMap;

public class WordsToGuess {
    private String current_Words;
    private String[] words_Category;

    public WordsToGuess() {
    }

    public WordsToGuess(String current_Words, String[] words_Category) {
        this.current_Words = current_Words;
        this.words_Category = words_Category;
    }

    public String getCurrent_Words() {
        return current_Words;
    }

    public void setCurrent_Words(String current_Words) {
        this.current_Words = current_Words;
    }

    public String[] getWords_Category() {
        return words_Category;
    }

    public void setWords_Category(String[] words_Category) {
        this.words_Category = words_Category;
    }
}
