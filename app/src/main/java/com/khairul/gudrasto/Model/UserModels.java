package com.khairul.gudrasto.Model;

public class UserModels {

    private int id;
    private String name;
    private String email;
    private String password;
    private String Best_score_Story;
    private String Best_score_Guess;
    private String week_score_Story;
    private String week_score_Guess;

    public UserModels(){

    }

    public UserModels(int id, String email, String name, String password, String best_score_Story, String best_score_Guess, String week_score_Story, String week_score_Guess) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        Best_score_Story = best_score_Story;
        Best_score_Guess = best_score_Guess;
        this.week_score_Story = week_score_Story;
        this.week_score_Guess = week_score_Guess;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBest_score_Story() {
        return Best_score_Story;
    }

    public void setBest_score_Story(String best_score_Story) {
        Best_score_Story = best_score_Story;
    }

    public String getBest_score_Guess() {
        return Best_score_Guess;
    }

    public void setBest_score_Guess(String best_score_Guess) {
        Best_score_Guess = best_score_Guess;
    }

    public String getWeek_score_Story() {
        return week_score_Story;
    }

    public void setWeek_score_Story(String week_score_Story) {
        this.week_score_Story = week_score_Story;
    }

    public String getWeek_score_Guess() {
        return week_score_Guess;
    }

    public void setWeek_score_Guess(String week_score_Guess) {
        this.week_score_Guess = week_score_Guess;
    }

}
