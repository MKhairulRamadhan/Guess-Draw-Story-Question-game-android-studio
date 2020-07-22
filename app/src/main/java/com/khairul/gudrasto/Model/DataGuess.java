package com.khairul.gudrasto.Model;

import com.khairul.gudrasto.R;

import java.util.ArrayList;

public class DataGuess {

    private String sound;
    private int option1;
    private int option2;
    private int option3;
    private int option4;
    private int NrAnswer;

    public static ArrayList<GuessModels> getDataGuess(){

        ArrayList<GuessModels> guessList = new ArrayList<>();

        GuessModels m1 = new GuessModels("cat", R.drawable.cat, R.drawable.bear, R.drawable.bee, R.drawable.beetle, 1);
        guessList.add(m1);
        GuessModels m2 = new GuessModels("cow", R.drawable.bat, R.drawable.beetle, R.drawable.camel, R.drawable.cow, 4);
        guessList.add(m2);
        GuessModels m3 = new GuessModels("bee", R.drawable.bulbul, R.drawable.bee, R.drawable.cockatiel, R.drawable.butterfly, 2);
        guessList.add(m3);
        GuessModels m4 = new GuessModels("bulbul", R.drawable.bulbul, R.drawable.bear, R.drawable.camel, R.drawable.cat, 1);
        guessList.add(m4);

        return guessList;

    }

}
