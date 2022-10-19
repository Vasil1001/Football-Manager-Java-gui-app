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

//SportsClub is abstract//have to keep track of teams' w/d/l of the season to come up with their points
    
abstract public class SportsClub {
    //for sportsclub add names/locations 
    
    //keep track of club name/location
    //keep track of stats for every club
    //and then extend to football club with more specific info
    
    //define above notes as variables
    private String name;
    private String location;
    private String stats;
    
    //set getters and getters
    //so i can setName when adding new club
    //and getName when eg. printing team added
    
    public String getName(){
        return name; //eg NewFC.getName() will give me NewFC name
    }
    public void setName(String nm){
        name = nm; //eg NewFC.setName() will assign a name to NewFC
    }
    
    public String getLocation(){
        return location;
    }
    public void setLocation(String loc){
        location = loc;
    }
    
    public String getStats(){
        return stats;
    }
    public void setStats(String st){
        stats = st;
    }
}
