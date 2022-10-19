/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw1_premierleague;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Vasilis
 */
//use extend for abstract**
//LeagueManager is interface so use implements here
public class PremierLeagueManager implements LeagueManager {

    //number of football clubs in the premier league
    //create arrayList of Football Clubs objects
    private int clubsCount;
    private ArrayList<FootballClub> league; //set arraylist of FootballClub objects called league

    public PremierLeagueManager(int size) {
        //set the size of premierleagueclass to 20 teams
        //define at top as int
        size = clubsCount;
        //create an arraylist called league 
        //and define at top as arrayList<>
        league = new ArrayList<>();
        runOptionsMenu();
    }

    public void runOptionsMenu() {
        //menuInput to get users typed choice
        Scanner menuInput = new Scanner(System.in);
        String choice;
        
        // exit true or false
        boolean exit = false;
        //while exit is not true display all the options
        while (!exit) { //while true 
//            System.out.println("---");
//            System.out.println("Welcome to the Premier League!");
//            System.out.println("Please select an option from below: ");
//            System.out.println("------------------------------------");
            System.out.println("A: To create a new football club and Add it in the premier league.");
            System.out.println("R: To Relegate an existing football club from the premier league.");
            System.out.println("D: To Display the various statistics for a selected football club.");
            System.out.println("T: To display the Premier League Table.");
            System.out.println("P: Add a played match with its score and its date.");
            System.out.println("S: To Save to a file of all the information entered by the user.");
            System.out.println("L: To load saved file of all the information entered by the user.");
            System.out.println("Q: To exit program.");
            
            //take users choice and make all capitals
            choice = menuInput.next().toUpperCase();
            //add cases for eachc corresponding letter entered
            switch (choice) { //switch and take String choice from user
                case "A":
                    addFC();
                    break;

                case "R":
                    relegateFC();
                    break;

                case "D":
                    displayStats();
                    break;

                case "T":
                    displayTable();
                    break;

                case "P":

                    break;

                case "S":
                   // storeFile();
                    break;

                case "L":
                   // loadFile();
                    break;

                case "Q":
                    System.out.println("Thanks for checking out the premier league bye!");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid Choice");
                    break;

            }
        }
    }

    private void addFC() {
        
        //before creating a FC check if the league is full
        if (league.size() == clubsCount) { //premier league has 20 clubs
            System.out.println("League is full, 20 teams are in already.");
        }

        //Create new object called FC
        FootballClub FC = new FootballClub();

        //ask user to enter name of the club
        Scanner input = new Scanner(System.in);

        //Input and set name of club
        System.out.println("Enter club name: ");
        String addFCname = input.nextLine(); //assign name from input to FCname
        FC.setName(addFCname); //use setter from Sports_Club to set the name to the assigned from scanner/input

        //check NAME ONLY if the object created already exists
        //e.g. if ManUtd is in i cannot add another
        if (league.contains(FC)) {
            System.out.println("This club already exists");
            return;
        }
        //Input and set location of club
        System.out.println("Enter Location: ");
        String addLoc = input.nextLine(); //assign name from input to FCname
        FC.setLocation(addLoc);

        //to check if object is added print the name and loc
        System.out.println("Your new football club is: \n" + addFCname
                + "\nLocation: " + addLoc + " \nYour football club has been added to the league!");

        league.add(FC);

    }

    private void relegateFC() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter name of football club to relegate: ");
        String relegateFCname = input.nextLine();

        for (FootballClub FC : league) {
            if (FC.getName() == relegateFCname) {
                league.remove(FC);
                System.out.println("Football club " + relegateFCname
                        + " has been relegated from the Premier League");
                return;

            }
        }
        System.out.println("Club not found"); //else
//        if (league.contains(relegateFCname)){
//            league.remove(relegateFCname);
//        }

    }

    private void displayTable() {

        if (league.size() == 0) { //if the league is empty, print that
            System.out.println("League is empty");
        }

        for (FootballClub FC : league) {
            System.out.println(FC.getName() + " Points: " + FC.getCurrent_points()
                    + " Goal Difference " + (FC.getGoals_scored() - FC.getGoals_received()));

        }

        //System.out.println("No clubs yet"); not working as else
    }

    private void displayStats() {
        System.out.println("Enter FC name: ");
        Scanner input = new Scanner(System.in);
        String FCname = input.nextLine();

        for (FootballClub FC : league) {
            if (FC.getName().equals(FCname)) {
                System.out.println(FC.getName() + "\n Wins: " + FC.getWins()
                        + "\n Draws: " + FC.getDraws()
                        + "\n Losses: " + FC.getLosses()
                        + "\n Points: " + FC.getCurrent_points()
                        + "\n Scored Goals: " + FC.getGoals_scored()
                        + "\n Received Goals: " + FC.getGoals_received()
                        + "\n Matches Played: " + FC.getMatches_played());

            }//else

            //System.out.println("Club not found");
        }        //else

    }

    private void storeFile() throws IOException {

        try (FileWriter storeData = new FileWriter("teams.dat")) { //using filewriter create new file report.dat
            for (FootballClub FC : league) {         //and for each team store in file
                storeData.write(FC.getName() + (" ") + FC.getLocation() + "\n"); //+ FC.getWins() + (" ") + FC.getDraws() + (" ") + FC.getLosses() + (" ") + FC.getCurrent_points() + (" ") + FC.getGoals_scored() + (" ") + FC.getGoals_received() + (" ") + FC.getMatches_played() + (" ")

            }
        } catch (IOException e) {
            System.out.println("Error IOException is: " + e);
        }
        System.out.println("Teams have been stored to file!");
        System.out.println("-----------------------------------");
    }

    private void loadFile() throws FileNotFoundException, IOException {

        try { //from scanner we have new BufferedReader and FileReader that reads from file report.dat
            Scanner loadFromFile = new Scanner(new BufferedReader(new FileReader("teams.dat")));

            int i = 0;
            //while there are names in the file, read them into passenger objects with iterrator hasNext
            //for (i = 0; i < waitingRoom.length; i++){} or hasNext
            while (loadFromFile.hasNext()) { //hasNext iterator to read the whole file from scanner while there is a next word

                FootballClub FC = new FootballClub();

                String name = loadFromFile.next(); //read name from first word and move to next
                String loc = loadFromFile.next(); //read name from first word and move to next
                FC.setName(name);
                FC.setLocation(loc);

                //Create new "wpass" objects using fname and sname above
                //Passenger wpass = new Passenger(name, name);
                //waitingRoom[i] = wpass;//add the Passenger to the waiting Room
                // i++;
                //ask user to enter name of the club
                //Input and set name of club
                //check NAME ONLY if the object created already exists
                //e.g. if ManUtd is in i cannot add another
                if (league.contains(FC)) {
                    System.out.println("This club already exists");
                    return;
                }

                league.add(FC);
                //to check if object is added print the name and loc
                System.out.println("Your new football club is: \n" + name
                        + "\nLocation: " + loc + " \nYour football clubs has been added to the league!");

            }
            //System.out.println("Passengers have been added to waiting queue!");
            System.out.println("--------------------------------------------");
            loadFromFile.close();
        } catch (IOException e) {
            System.out.println("Error IOException is: " + e);
        }
    }
}
