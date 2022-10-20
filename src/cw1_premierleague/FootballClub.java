/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw1_premierleague;

/**
 *
 * @author Vasilis
 */


public class FootballClub extends SportsClub {
    //have to keep track of teams' w/d/l of the season to come up with their points
    //keep track of current points
    //goals scored and received
    //how many matches are played so far
    
    //define above notes as variables
    int wins; int draws; int losses;
    int goals_scored; int goals_received;
    int current_points;
    int played_match;
    
    public int getWins(){
        return wins;
    }
    public void setWins(int w){
        wins = w;
    }
    
    public int getDraws(){
        return draws;
    }
    public void setDraws(int d){
        draws = d;
    }
    
    public int getLosses(){
        return losses;
    }
    public void setLosses(int l){
        losses = l;
    }
    
    public int getGoals_scored(){//TOTAL SCORED GOALS
        return goals_scored;
    }
    public void setGoals_scored(int gs){
        goals_scored = gs;
    }
    
    
    public int getGoals_received(){
        return goals_received;
    }
    public void setGoals_received(int gr){
        goals_received = gr;
    }
    
    public int getCurrent_points(){
        return current_points;
    }
    public void setCurrent_points(int cp){
        current_points = cp;
    } 
    public int getMatch_played(){
        return played_match;
    }
    public void setMatch_played(int mp){
        played_match = mp;
    }
}
    