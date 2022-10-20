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

public class PlayMatch { 
    
    private FootballClub FC1; private FootballClub FC2; 
    private int Score_FC1; private int Score_FC2;
    

    public FootballClub getFC1(){
        return FC1;
    }
    public void setFC1(FootballClub FC){//SET SCORE FOR FC1 DURING A GAME, NOT TOTAL
        FC = FC1;
    }
    
    public int getScore_FC1(){
        return Score_FC1;
    }
    public void setScore_FC1(int FC1){
        Score_FC1 = FC1;
    }
   
    public FootballClub getFC2(){
        return FC2;
    }
    public void setFC2(FootballClub FC){
        FC = FC2;
    }
    public int getScore_FC2(){
        return Score_FC2;
    }
    public void setScore_FC2(int FC2){
        Score_FC2 = FC2;
    }

}
