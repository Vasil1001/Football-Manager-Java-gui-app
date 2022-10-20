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

//SportsClub is abstract
//contains basic name loc for clubs
    
abstract public class SportsClub {
//define
    private String name;
    private String location;

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
}
