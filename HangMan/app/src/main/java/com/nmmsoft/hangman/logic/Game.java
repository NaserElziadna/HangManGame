package com.nmmsoft.hangman.logic;
///////////////////////////
//  Designed and Creared //
//     Naser Elziadna    //
//         Thanks♥       //
///////////////////////////

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class Game extends AppCompatActivity {
    private  boolean isContinue=false;
    public static final int MAX_ERRORS = 6;
    private int nbErrors;
    private ArrayList< String > letters = new ArrayList < > ();
    private int currentLive=7;
    private String wordToFind;
    private char[] wordFound;

    public String[] getWORDS() {
        return WORDS;
    }

    public void setWORDS(String[] WORDS) {
        this.WORDS = WORDS;
    }

    public String[] WORDS =
            {
            "NASER", "AA"
            //            , "BOOLEAN", "BREAK", "BYTE",
//            "CASE", "CATCH", "CHAR", "CLASS", "CONST",
//            "CONTINUE", "DEFAULT", "DOUBLE", "DO", "ELSE",
//            "ENUM", "EXTENDS", "FALSE", "FINAL", "FINALLY",
//            "FLOAT", "FOR", "GOTO", "IF", "IMPLEMENTS",
//            "IMPORT", "INSTANCEOF", "INT", "INTERFACE",
//            "LONG", "NATIVE", "NEW", "NULL", "PACKAGE",
//            "PRIVATE", "PROTECTED", "PUBLIC", "RETURN",
//            "SHORT", "STATIC", "STRICTFP", "SUPER", "SWITCH",
//            "SYNCHRONIZED", "THIS", "THROW", "THROWS",
//            "TRANSIENT", "TRUE", "TRY", "VOID", "VOLATILE", "WHILE"
    };
    public static final Random RANDOM = new Random();

    public boolean isContinue() {
        return isContinue;
    }

    public void setIsContinue(boolean aContinue) {
        isContinue = aContinue;
    }

    public static int getMaxErrors() {
        return MAX_ERRORS;
    }

    public int getNbErrors() {
        return nbErrors;
    }

    public void setNbErrors(int nbErrors) {
        this.nbErrors = nbErrors;
    }
    public void add1toNbErrors() {
        this.nbErrors ++;
    }
    public String getWordToFind() {
        return wordToFind;
    }

    public void setWordToFind(String wordToFind) {
        this.wordToFind = wordToFind;
    }

    public char[] getWordFound() {
        return wordFound;
    }

    public void setWordFound(char[] wordFound) {
        this.wordFound = wordFound;
    }

    public int getCurrentLive() {
        return currentLive;
    }

    public ArrayList<String> getLetters() {
        return letters;
    }

    public void setLetters(ArrayList<String> letters) {
        this.letters = letters;
    }

    public void setCurrentLive(int currentLive) {
        this.currentLive = currentLive;
    }

    public void takeLive() {
        this.currentLive -=1;
    }


    // Method returning trus if word is found by user
    public boolean wordFounded() {
        return wordToFind.contentEquals(new String(wordFound));
    }

    public String nextWordToFind() {
        return WORDS[RANDOM.nextInt(WORDS.length)];
    }

    // Method returning the state of the word found by the user until by now
    public String wordFoundContent() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < getWordFound().length; i++) {
            builder.append(getWordFound()[i]);

            if (i < getWordFound().length - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }
}
