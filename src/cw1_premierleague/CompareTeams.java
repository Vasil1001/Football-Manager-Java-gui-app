/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw1_premierleague;
import java.util.Comparator;
/**
 *
 * @author Vasilis
 */

//FC1 has more points than FC2 - return-1
//FC1 has less points than FC2 - return 1
//FC1 points == FC2 points     - return 0

//Lecture - Discussion and Q&A (Java) useful example given*
public class CompareTeams  implements Comparator<FootballClub>{
    public int compare(FootballClub FC1, FootballClub FC2) //compareTo doesn't work!! use compare
    {
        if(FC1.getCurrent_points()>FC2.getCurrent_points())
        {
            return -1;
        } 
        
        else if (FC1.getCurrent_points() < FC2.getCurrent_points()) {
            return 1;
        } 
    //if both FootballClubs have same points return 0;
        else{//but first dont return 0, **!compare the goal difference of FC1 and FC2**
            
            //goal difference is "goals scored - goals received"
            int FC1gd = FC1.getGoals_scored() - FC1.getGoals_received();//goal difference for FC1
            int FC2gd = FC2.getGoals_scored() - FC2.getGoals_received();
            
    //repeat as above but use goaldif instead, not points
            if(FC1gd > FC2gd)
            {
                return -1;
            }
            else
            {
                if(FC1gd < FC2gd){
                    return 1;
                }
            }
                    
        }        return 0;
    }                      
}

