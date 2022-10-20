/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw1_premierleague;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vasilis
 */

public class PremierLeagueManager implements LeagueManager {
//CREATE MENU IN HERE ^^^^^
    //define arrayLists
    private ArrayList<FootballClub> league; //define arraylist of FootballClub objects called league
    private ArrayList<PlayMatch> games; //new arraylist so games can be stored
    
//add 20 in 1st main class to create a league of length 20
    public PremierLeagueManager(int leagueLength) {
        //initialize arraylist league /and define at top as arrayList<>
        league = new ArrayList<>();
        games = new ArrayList<>();
        runOptionsMenu();
    }

    public void runOptionsMenu() {

        

//Used similar to my last year train passenger menu
        boolean exit = false;
        //NOTE make exit == true to exit loop
            System.out.println("------------------------------------");
            System.out.println("Welcome to the Premier League!");
            System.out.println("Please select an option from below: ");
            
            try { //load lastly saved teams
                loadFile();
            } catch (Exception ex) {
                System.out.println("No teams to load!" + ex);
                }//load teams from file previously saved
            
        while (!exit) {
            System.out.println("A: To create a new football club and add it to the premier league.");
            System.out.println("R: To relegate an existing football club from the premier league.");
            System.out.println("D: To display the various statistics for a selected football club.");
            System.out.println("T: To display the Premier League Table.");
            System.out.println("P: To add a played match with its score and its date.");
            System.out.println("G: To run a user interface with buttons and tables.");
            System.out.println("C: To clear all teams from the league."); //used for quicker testing left in - useful in app
            System.out.println("S: To save all the current teams in league to a file for future load.");
            System.out.println("L: To remove current league and load previously saved teams and scores from last saved file.");
            System.out.println("1: To remove current league and load 20 Premier League teams.");
            System.out.println("Q: To exit program.");

            Scanner menuInput = new Scanner(System.in);
            String choice;
            choice = menuInput.next().toUpperCase(); //make user choice to capitals so he can enter both a or A

            switch (choice) { //take String choice from user and call methods
                case "A":
                    addNewFC();
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
                    addNewFCMatch();
                    break;

                case "G":
                    gui();
                    break;

                case "S": {
                    try {
                        storeThisFile();
                    } catch (Exception ex) {
                        System.out.println("No teams to store!" + ex);
                    }
                }
                break;

                case "L": {
                    try {
                        //loadFile();
                        loadLastFile();
                    } catch (Exception ex) {
                        System.out.println("No teams to load!" + ex);
                    }
                }
                break;
                case "1": {
                    try {
                        //Load 20 Premier league teams and delete all existing data
                        loadFile();
                    } catch (Exception ex) {
                        System.out.println("No teams to load!" + ex);
                    }
                }
                break; 
                case "C":
                    try{
                       clearLeague(); 
                    } catch (Exception ex) {
                        System.out.println("League is empty");
                    }
                    
                    break;
                    
                case "Q":
                    System.out.println("Thanks for checking out the premier league bye!");
                    exit = true;
                    break;

                default: //if there is wrong input
                    System.out.println("Invalid Choice");
                    break;

            }
        }
    }

    private void addNewFC() {

        //before creating a Team check if the league is full
        if (league.size() > 19) { //premier league has max 20 clubs
            System.out.println("\nLeague is full, 20 teams are in already\n");
            return;
        }

        Scanner userInput = new Scanner(System.in);
        FootballClub FC = new FootballClub(); //*Create object FC

        System.out.println("Enter football club name");           //user enters name first
        String addFCName = userInput.nextLine(); //assing user input to String
        FC.setName(addFCName); //use setName from SportsClub to assign FC name

        System.out.println("Enter football club location");
        String addFCLocation = userInput.nextLine();
        FC.setLocation(addFCLocation);

        league.add(FC);
        System.out.println("Your Football Club: " + FC.getName() + " has been added to the league!");
    }

    private void relegateFC() {
        if (league.size() == 0) {
            System.out.println("\nLeague has no football clubs to relegate\n");
            return;
        }

        Scanner userInput = new Scanner(System.in);

        System.out.println("Enter football club name to relegate");
        String deleteTeam = userInput.nextLine();

        for (FootballClub FC : league) { //FC.getName == deleteTeam not working?? fixed
            if (FC.getName().equals(deleteTeam)) { //changed from bulb to ***.equals*** and works
                league.remove(FC);
                System.out.println("Football club " + deleteTeam
                        + " has been relegated from the Premier League");
                return;
            }
        }
        System.out.println("\nEntered FC does not match any football club from league\n");
    }

    private void displayTable() {
        if (league.isEmpty()) { //league.size()==0 also **league.isEmpty() useful
            System.out.println("\nLeague is empty");
        }
        displaySortedTable();
        System.out.println("___________________________________________________________\n");
    }

    //Example .java file in JAVA Q&A + 02/12/20 Lecture : Call CompareTeams USING Collections.sort(arrayList,class)**
    /*  To call comparator call the library sort method and then for loop + print
        Collections.sort(list); try Collections.sort(league,CompareTeams);*/
    private void displaySortedTable() {
        Collections.sort(league, new CompareTeams()); //THIS CALLS THE COMPARE METHOD

        int i = 0;
        for (FootballClub FC : league) {
            System.out.println((i + 1) + " " + FC.getName()
                    + " MP: " + FC.getCurrent_points()
                    + " Points: " + FC.getCurrent_points()
                    + " Goal Difference "
                    //goal difference means 'goals scored MINUS received'
                    + (FC.getGoals_scored() - FC.getGoals_received()
                    + " W: " + FC.getWins() + " D: " + FC.getDraws() + " L: " + FC.getLosses()));
            i++; //increment positions number
        }
    }

    private void displayStats() {
        System.out.println("Enter FC name: ");
        Scanner input = new Scanner(System.in);
        String typeFCname = input.nextLine();

        if (league.isEmpty()) {
            System.out.println("League is empty");
        }

        for (FootballClub FC : league) {
            if (FC.getName().equals(typeFCname)) { //here again getName == typeFCname doesnt work. fixed changed to ***.equals
                System.out.println(FC.getName() + " Location: " + FC.getLocation() + "\n Wins: " 
                        + FC.getWins() + " MP: " + FC.getCurrent_points()
                        + "  |  Draws: " + FC.getDraws()
                        + "  |  Losses: " + FC.getLosses()
                        + "\n Points: " + FC.getCurrent_points()
                        + " |  Scored Goals: " + FC.getGoals_scored()
                        + " |  Received Goals: " + FC.getGoals_received() + "\n");
                return;
            }
        }
        //else
        System.out.println("\nEntered FC does not match any teams from league\n");

    }

    private void addNewFCMatch() {
//**I need 2 different FC1,FC2 because FC will compare only 1 team on score
//<--- initialize variable FC1 when calling FC1.getName();
        //System.out.print("Enter Match date using --> dd/mm/yyyy: ");
        Scanner userInput = new Scanner(System.in);
        
        PlayMatch game = new PlayMatch(); //Make new game obj to add the 2 teams in

        //String gamedate = userInput.nextLine();
        // game.setDate(gameDate);

        //SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        //gameDate = sdf.format(new Date()); 
        //game.setDate(gameDate);
        //System.out.println(gameDate); //Prints TODAYS DATE??
        
        FootballClub FC1 = new FootballClub();
        System.out.print("Enter first FC name: ");
        String FirstFC = userInput.nextLine();
        //if the club typed exists, make it FC1
       
        
        for (FootballClub FC : league) {
             try{
                 if (FC.getName().equals(FirstFC)) {
                    FC1 = FC;   
                } 
             }catch(Exception e){
                    System.out.println("Club not found");
                }

            
        
        }
        
        if (FC1 == null) {//else
            System.out.println("FC not found");
            return;
        }
    try{
        int FC1_goals = 0;
        System.out.print("Enter first FC scored goals: ");
        Scanner goals1 = new Scanner(System.in);
        FC1_goals = goals1.nextInt();
    
        game.setFC1(FC1);
        game.setScore_FC1(FC1_goals);
        

//------------------- Now initialize second FC same stuff

        FootballClub FC2 = new FootballClub();
        System.out.print("Enter Second FC name: ");
        String SecondFC = userInput.nextLine();
        //if the club typed exists, make it FC1
        for (FootballClub FC : league) {
            if (FC.getName().equals(SecondFC)) {
                FC2 = FC; //to assign to FC1 it has to be **= null;**
            }            //System.out.println(FC2.getName());

        }
        if (FC2 == null) {
            System.out.println("FC not found");
            return;
        }

//-----------------------------------------//

        int FC2_goals = 0;
        System.out.print("Enter second FC scored goals: ");
        Scanner goals2 = new Scanner(System.in);
        FC2_goals = goals2.nextInt();
        
        //set FC1/FC2 Goals,matches played
        FC1.setGoals_scored(FC1.getGoals_scored() + FC1_goals);
        FC1.setGoals_received(FC1.getGoals_received() + FC2_goals);
        FC2.setGoals_scored(FC2.getGoals_scored() + FC2_goals);
        FC2.setGoals_received(FC2.getGoals_received() + FC1_goals);
        FC1.setMatch_played(FC1.getMatch_played() + 1);
        FC2.setMatch_played(FC2.getMatch_played() + 1);

        if (game.getScore_FC1() > game.getScore_FC2()) {
            FC1.setWins(FC1.getWins() + 1);
            FC1.setCurrent_points(FC1.getCurrent_points() + 3);
            FC2.setLosses(FC2.getLosses() + 1);

        } else if (game.getScore_FC1() < game.getScore_FC2()) {
            FC2.setWins(FC2.getWins() + 1);
            FC2.setCurrent_points(FC2.getCurrent_points() + 3);
            FC1.setLosses(FC1.getLosses() + 1);

        } else if (game.getScore_FC1() == game.getScore_FC2()) {
            FC1.setDraws(FC1.getDraws() + 1);
            FC1.setCurrent_points(FC1.getCurrent_points() + 1);
            FC2.setDraws(FC2.getDraws() + 1);
            FC2.setCurrent_points(FC2.getCurrent_points() + 1);
        }

        game.setFC2(FC2);
        game.setScore_FC2(FC2_goals);
        
        games.add(game);
        
    }catch(Exception e){
            System.out.println("Error try again");
        }
    }
    
    private void clearLeague(){
        for(FootballClub FC: league){ //removes all FC from league(for quicker testing)
            league.clear();
        }
    }
 //Used similar to my last year train passenger menu  
    private void storeThisFile() throws IOException {

        try (FileWriter saveFile = new FileWriter("teams2.dat")) { //using filewriter create new file teams.dat
            for (FootballClub FC : league) {  //for each object FC    
                //write in created storeData
                saveFile.write(FC.getName() + (" ") + FC.getLocation() + (" ") + FC.getWins() + (" ") + FC.getDraws() + (" ") + FC.getLosses() + (" ") + FC.getCurrent_points() + (" ") + FC.getGoals_scored() + (" ") + FC.getGoals_received() + (" ") + FC.getMatch_played() + (" "));
            }
        } catch (Exception e) {
            System.out.println("No teams found to store " + e);
        }

        System.out.println("Teams have been stored to file!");
        System.out.println("_______________________________");
    }
    
//Used similar to my last year train passenger menu
    private void loadFile(){
        if(league.size() > 0){ //
            try{
                clearLeague();
                }
            catch(Exception load){
                    System.out.println("League Cleared: Loading new teams from saved file...");     
                }
        }
        
        if (league.size() > 20) {
  
            System.out.println("League is full, cannot add anymore teams.");
            return;
        }
        
        File readFromFile = new File("teams.dat"); // read the saved file
        try (Scanner load = new Scanner(readFromFile)){ //using scanner
            while (load.hasNext()){
                FootballClub FC = new FootballClub(); //create new FC to assign from text file

                String name = load.next(); //read first word - attatch to name
                FC.setName(name);

                String location = load.next();
                FC.setLocation(location);
                     
                league.add(FC);     
            }
            System.out.println("Your football clubs have been added!");
            System.out.println("____________________________________");
            load.close();
                    
        } catch (FileNotFoundException e) { //if there is no file
            System.out.println("No file found " + e);
            }
    }
 
//Used similar to my last year train passenger menu
    private void loadLastFile(){
        if(league.size() > 0){ //
            try{
                clearLeague();
                }
            catch(Exception load){
                    System.out.println("League Cleared: Loading new teams from saved file...");     
                }
        }
        
        if (league.size() > 20) {
  
            System.out.println("League is full, cannot add anymore teams.");
            return;
        }
        
        File readFromFile = new File("teams2.dat"); // read the saved file
        try (Scanner load = new Scanner(readFromFile)){ //using scanner
            while (load.hasNext()){
                FootballClub FC = new FootballClub(); //create new FC to assign from text file

                String name = load.next(); //read first word - attatch to name
                FC.setName(name);

                String location = load.next();
                FC.setLocation(location);
                
                int wins = load.nextInt();
                FC.setWins(wins);
                
                int Draws = load.nextInt();
                FC.setDraws(Draws);
                
                int Losses = load.nextInt();
                FC.setLosses(Losses);
                
                int points = load.nextInt();
                FC.setCurrent_points(points);
                
                int gs = load.nextInt();
                FC.setGoals_scored(gs);
                
                int gr = load.nextInt();
                FC.setGoals_received(gr);
                
                int mp = load.nextInt();
                FC.setMatch_played(mp);
   
                league.add(FC);     
            }
            System.out.println("Your football clubs have been added!");
            System.out.println("____________________________________");
            load.close();
                    
        } catch (Exception e) { //if there is no file
            System.out.println("No file found " + e);
            }
    }
    
    public void gui() {
        //define my windowlistener
        class MyWindowListener extends WindowAdapter {

            public void windowClosing(WindowEvent e) {
                System.out.println("Closing window!");
                System.exit(0);
            }
        }
        
        System.out.println("Launching gui...\n");
        JFrame frame = new JFrame("Premier League Table");
        frame.addWindowListener(new MyWindowListener()); //closes window/program

//https://docs.oracle.com/javase/tutorial/uiswing/components/table.html

        String[] columnNames = {"Position", "Team", "MP", "Points", "W", "D", "L", "GS", "GR", "GD"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames); //use table model for objects
        JTable table = new JTable(model);
//https://www.roseindia.net/java/example/java/swing/InsertRows.shtml

        while (model.getRowCount() > 0) //if there are rows added
        {
            model.removeRow(0); //delete them otherwise it doubles the FC, so this updates it
        }

        int r = 0;
        for (FootballClub FC : league) { //re add the FC
            //0 is first position - .getRowCount() is last (SO TEAMS ARE SHOWN FROM TOP TO BOTTOM 1-20)
            model.insertRow(table.getRowCount(), new Object[]{(r + 1), FC.getName(), FC.getMatch_played(), FC.getCurrent_points(),
                FC.getWins(), FC.getDraws(),
                FC.getLosses(), FC.getGoals_scored(),
                FC.getGoals_received(),
                FC.getGoals_scored() - FC.getGoals_received()});
            r++;
        }
        
        String[] columnNames2 = {"Team 1", "Goals", "Goals", "Team 2"};
        Object[][] data2 = {};
        //JTable table = new JTable(data,columnNames);
        DefaultTableModel model2 = new DefaultTableModel(data2, columnNames2); //use table model for objects
        JTable table2 = new JTable(model2);

        int i = 0;
//from tutorial 7 
        //x    y    w    h
        frame.setBounds(250, 250, 775, 600);
        frame.getContentPane().setLayout(null);

        JLabel label = new JLabel("Welcome to the");

        label.setFont(new Font("Century Gothic", Font.BOLD, 13));
        label.setBounds(20, 10, 400, 20);

        JLabel labelb = new JLabel("Premier League table");

        labelb.setFont(new Font("Century Gothic", Font.BOLD, 13));
        labelb.setBounds(10, 28, 400, 20);

        JLabel labelc = new JLabel("___________________");

        labelc.setFont(new Font("Century Gothic", Font.BOLD, 13));
        labelc.setBounds(14, 42, 400, 20);

        //
        JButton button1 = new JButton("Sort Points");
        button1.setFont(new Font("Century Gothic", Font.BOLD, 12));
        //x    y    w   h
        button1.setBounds(20, 75, 110, 24);
        // 
        JButton button2 = new JButton("Sort Goals");
        button2.setFont(new Font("Century Gothic", Font.BOLD, 12));
        button2.setBounds(20, 105, 110, 24);
        //
        JButton button3 = new JButton("Sort Wins");
        button3.setFont(new Font("Century Gothic", Font.BOLD, 12));
        button3.setBounds(20, 135, 110, 24);
        
        JButton button4 = new JButton("Clear Table");
        button4.setFont(new Font("Century Gothic", Font.BOLD, 12));
        button4.setBounds(20, 165, 110, 24);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(150, 10, 595, 280);//table dimensions
        table.setFillsViewportHeight(true);

        JLabel label2 = new JLabel("Search for game using: dd/mm/yy");
        label2.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label2.setBounds(525, 450, 250, 20);

        JTextField textField = new JTextField();
        textField.setBounds(525, 470, 220, 20);

        JButton button5 = new JButton("Search");
        button5.setFont(new Font("Century Gothic", Font.BOLD, 12));
        button5.setBounds(639, 493, 105, 20);

        JButton button6 = new JButton("Play Random Match");
        button6.setFont(new Font("Century Gothic", Font.BOLD, 14));
        button6.setBounds(10, 452, 200, 24);
        
        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setBounds(10, 300, 735, 150);//table dimensions
        table2.setFillsViewportHeight(true);
        
        frame.setVisible(true); //use last otherwise everything is hidden unless i resize*fixed
        
        class SortPoints implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                System.out.println("You pressed the button " + e.getActionCommand());
                Collections.sort(league, new SortGuiPoints()); //call sorting for league
//FIRST DELETE TABLE, THEN ADD AGAIN (FOR LOOP) TO UPDATE**
                
                while (model.getRowCount() > 0) //if there are rows on the table added
                {
                    model.removeRow(0); //delete them otherwise it doubles the FC, so this updates it
                }

                int i = 0;
                for (FootballClub FC : league) { //re add the FC
                    model.insertRow(table.getRowCount(), new Object[]{(i + 1), FC.getName(), FC.getMatch_played(), FC.getCurrent_points(),
                        FC.getWins(), FC.getDraws(),
                        FC.getLosses(), FC.getGoals_scored(),
                        FC.getGoals_received(),
                        FC.getGoals_scored() - FC.getGoals_received()});
                    i++; //increment position
                }
            }
        }

        class SortGoals implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                System.out.println("You pressed the button " + e.getActionCommand());
                Collections.sort(league, new SortGuiGoals());
                

                while (model.getRowCount() > 0) //if there are rows added
                {
                    model.removeRow(0); //delete them otherwise it doubles the FC, so this updates it
                }

                int i = 0;
                for (FootballClub FC : league) { //re add the FC
                    model.insertRow(table.getRowCount(), new Object[]{(i + 1), FC.getName(), FC.getMatch_played(), FC.getCurrent_points(),
                        FC.getWins(), FC.getDraws(),
                        FC.getLosses(), FC.getGoals_scored(),
                        FC.getGoals_received(),
                        FC.getGoals_scored() - FC.getGoals_received()});
                    i++;
                }
            }
        }

        class SortWins implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                System.out.println("You pressed the button " + e.getActionCommand());
                Collections.sort(league, new SortGuiWins());

                while (model.getRowCount() > 0) //if there are rows added
                {
                    model.removeRow(0); //delete them otherwise it doubles the FC, so this updates it
                }

                int i = 0;
                for (FootballClub FC : league) { //re add the FC
                    model.insertRow(table.getRowCount(), new Object[]{(i + 1), FC.getName(), FC.getMatch_played(), FC.getCurrent_points(),
                        FC.getWins(), FC.getDraws(),
                        FC.getLosses(), FC.getGoals_scored(),
                        FC.getGoals_received(),
                        FC.getGoals_scored() - FC.getGoals_received()});
                    i++;
                }
            }
        }
        
        class ClearTable implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                Collections.sort(league, new SortGuiPoints()); //call sorting
                  
                //REMOVE CURRENT TABLE NUMBERS
                    while (model.getRowCount() > 0) //if there are rows added
                {
                    model.removeRow(0); //delete them otherwise it doubles the FC, so this updates it
                }
                    
                for(FootballClub FC: league)
                {
                    //CLEAR ALL TO 0
                    FC.setWins(0);              
                    FC.setDraws(0);                
                    FC.setLosses(0);              
                    FC.setCurrent_points(0);               
                    FC.setGoals_scored(0);              
                    FC.setGoals_received(0);   
                    FC.setMatch_played(0); 
                    
                 
                    //ADD ALL TEAMS AGAIN
                    int i = 0;
                    model.insertRow(table.getRowCount(), new Object[]{(i + 1), FC.getName(), FC.getMatch_played(), FC.getCurrent_points(),
                        FC.getWins(), FC.getDraws(),
                        FC.getLosses(), FC.getGoals_scored(),
                        FC.getGoals_received(),
                        FC.getGoals_scored() - FC.getGoals_received()});
                    i++;
                }
                
            }
        }
 //from class ActionListener1 implements ActionListener
        class PlayRandomTeams implements ActionListener {

            public void actionPerformed(ActionEvent h) {

                PlayMatch game = new PlayMatch();
                Random rand = new Random();

                try { //when button is pressed it crashes so try/catch to prevent that
                    int RandT1 = rand.nextInt(league.size());

//https://www.tutorialspoint.com/java/util/arraylist_get.htm
                    FootballClub RFC1 = league.get(RandT1);  //select a random team from league .get!! 

                    int RandT2 = rand.nextInt(league.size());
                    
                    //if a team plays itself, then reset
                    if (RandT2 == RandT1) {//was getting same team in 1 game twice, so dont run it
                        //System.out.println("SAME TEAMS" + RandT1 + RandT2);
                        return;
                    }
                    
                    FootballClub RFC2 = league.get(RandT2); 

                    int RandT1Goals = rand.nextInt(7);
                    int RandT2Goals = rand.nextInt(7);

                    game.setFC1(RFC1);
                    game.setScore_FC1(RandT1Goals); //match goals
                    game.setFC2(RFC2);
                    game.setScore_FC2(RandT2Goals);

                    RFC1.setGoals_scored(RFC1.getGoals_scored() + RandT1Goals); //total goals
                    RFC2.setGoals_scored(RFC2.getGoals_scored() + RandT2Goals);
                    RFC1.setGoals_received(RFC1.getGoals_received() + RandT2Goals);
                    RFC2.setGoals_received(RFC2.getGoals_received() + RandT1Goals);
                    RFC1.setMatch_played(RFC1.getMatch_played() + 1);
                    RFC2.setMatch_played(RFC2.getMatch_played() + 1);

                    if (game.getScore_FC1() > game.getScore_FC2()) {
                        RFC1.setWins(RFC1.getWins() + 1);
                        RFC1.setCurrent_points(RFC1.getCurrent_points() + 3);
                        RFC2.setLosses(RFC2.getLosses() + 1);
                    } 
                    
                    else if (game.getScore_FC1() < game.getScore_FC2()) {
                        RFC2.setWins(RFC2.getWins() + 1);
                        RFC2.setCurrent_points(RFC2.getCurrent_points() + 3);
                        RFC1.setLosses(RFC1.getLosses() + 1);
                    } 
                    
                    else if (game.getScore_FC1() == game.getScore_FC1()) {
                        RFC1.setDraws(RFC1.getDraws() + 1);
                        RFC1.setCurrent_points(RFC1.getCurrent_points() + 1);
                        RFC2.setDraws(RFC2.getDraws() + 1);
                        RFC2.setCurrent_points(RFC2.getCurrent_points() + 1);
                    }
                    //insert in table 2   
                    model2.insertRow(table2.getRowCount(), new Object[]{RFC1.getName(), RandT1Goals, RandT2Goals, RFC2.getName()}); //UPDATE

                } catch (Exception a) {
                    System.out.println("No matches to play.");
                }
            }
        }
        //add all the above to the frame
        frame.add(label); //Welcome to
        frame.add(labelb);//Premier league text
        frame.add(labelc);

        frame.add(button1);//sort points
        frame.add(button2);//sort goals
        frame.add(button3);//sort wins
        frame.add(button4);//clear table
        
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.add(label2);
        frame.add(textField);
        frame.add(button5);//search NOT IMPLEMENTED

        frame.add(button6);//Play random match
        frame.add(scrollPane2, BorderLayout.CENTER);

        button1.addActionListener(new SortPoints());//BUTTON 1 SORTS BY POINTS
        button2.addActionListener(new SortGoals());//BUTTON 2 SORTS BY GOALS
        button3.addActionListener(new SortWins());//BUTTON 3 SORTS BY WINS
        button4.addActionListener(new ClearTable());//BUTTON 4 CLEARS TABLE
        button4.addActionListener(new SortPoints());//BUTTON 4 SORTS BY POINTS TO UPDATE TABLE   
        button6.addActionListener(new PlayRandomTeams());//BUTTON 6 PLAYS 2 RANDOM TEAMS
        button6.addActionListener(new SortPoints());//BUTTON 6 SORTS BY POINTS TO UPDATE TABLE
    }

    public class SortGuiPoints implements Comparator<FootballClub> {

        public int compare(FootballClub FC1, FootballClub FC2) //compareTo doesn't work!! use compare
        {
            if (FC1.getCurrent_points() > FC2.getCurrent_points()) {
                return -1;
            } else if (FC1.getCurrent_points() < FC2.getCurrent_points()) {
                return 1;
            } //if both FootballClubs have same points return 0;
            else {//but first dont return 0, **!compare the goal difference of FC1 and FC2**

                //goal difference is "goals scored - goals received"
                int FC1gd = FC1.getGoals_scored() - FC1.getGoals_received();//goal difference for FC1
                int FC2gd = FC2.getGoals_scored() - FC2.getGoals_received();

                //repeat as above but use goaldif instead, not points
                if (FC1gd > FC2gd) {
                    return -1;
                } else {
                    if (FC1gd < FC2gd) {
                        return 1;
                    }
                }

            }
            return 0;
        }
    }

//----------------------------------------------------------------//
    public class SortGuiGoals implements Comparator<FootballClub> {

        public int compare(FootballClub FC1, FootballClub FC2) //compareTo doesn't work!! use compare
        {
            if (FC1.getGoals_scored() > FC2.getGoals_scored()) {
                return -1;
            } else if (FC1.getGoals_scored() < FC2.getGoals_scored()) {
                return 1;
            } //if both FootballClubs have same points return 0;
            else {//but first dont return 0, **!compare the goal difference of FC1 and FC2**

                //goal difference is "goals scored - goals received"
                int FC1gd = FC1.getGoals_scored() - FC1.getGoals_received();//goal difference for FC1
                int FC2gd = FC2.getGoals_scored() - FC2.getGoals_received();

                //repeat as above but use goaldif instead, not points
                if (FC1gd > FC2gd) {
                    return -1;
                } else {
                    if (FC1gd < FC2gd) {
                        return 1;
                    }
                }

            }
            return 0;
        }
    }

//----------------------------------------------------------------//
    public class SortGuiWins implements Comparator<FootballClub> {

        public int compare(FootballClub FC1, FootballClub FC2) //compareTo doesn't work!! use compare
        {
            if (FC1.getWins() > FC2.getWins()) {
                return -1;
            } else if (FC1.getWins() < FC2.getWins()) {
                return 1;
            } //if both FootballClubs have same points return 0;
            else {//but first dont return 0, **!compare the goal difference of FC1 and FC2**

                //goal difference is "goals scored - goals received"
                int FC1gd = FC1.getGoals_scored() - FC1.getGoals_received();//goal difference for FC1
                int FC2gd = FC2.getGoals_scored() - FC2.getGoals_received();

                //repeat as above but use goaldif instead, not points
                if (FC1gd > FC2gd) {
                    return -1;
                } else {
                    if (FC1gd < FC2gd) {
                        return 1;
                    }
                }

            }
            return 0;
        }
    }
}
