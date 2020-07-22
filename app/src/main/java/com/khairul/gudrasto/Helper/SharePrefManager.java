package com.khairul.gudrasto.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefManager {

    public static final String SP_APP = "Gudrasto";

    public static final String SP_ID = "id";
    public static final String SP_NAME = "name";
    public static final String SP_STORY_BEST = "Best_score_Story";
    public static final String SP_GUESS_BEST = "Best_score_Guess";
    public static final String SP_STORY_WEEK = "week_score_Story";
    public static final String SP_GUESS_WEEK = "week_score_Guess";

    public static final String SP_LOGIN_DONE = "already_login";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public SharePrefManager(Context context){
        sp = context.getSharedPreferences(SP_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPName(){
        return sp.getString(SP_NAME, "");
    }

    public int getSPId(){
        return sp.getInt(SP_ID, 0);
    }

    public String getSpStoryBest() {
        return sp.getString(SP_STORY_BEST, "0");
    }

    public String getSpGuessBest() {
        return sp.getString(SP_GUESS_BEST, "0");
    }

    public String getSpStoryWeek() {
        return sp.getString(SP_STORY_WEEK, "0");
    }

    public String getSpGuessWeek() {
        return sp.getString(SP_GUESS_WEEK, "0");
    }

    public Boolean getSPSudahLogin() {
        return sp.getBoolean(SP_LOGIN_DONE, false);
    }

}
