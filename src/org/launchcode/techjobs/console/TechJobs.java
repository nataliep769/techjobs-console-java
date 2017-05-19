package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static org.launchcode.techjobs.console.JobData.loadData;

/**
 * Created by LaunchCode
 */

public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill"); //This adds a row for core competency/skill
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices); //the menuHeader is equal to "view jobs by")

            if (actionChoice.equals("list")) { //if the user chooses list, give them the column choices devoted to list //

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) { //if the user chooses to list all of the job data//
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search" //If the user chooses search //

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm)); //place method call for findbyValue here //
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) { //we're creating a method called getUserSelection//

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    //To Do//
    //1) Create an array list of hash maps. Each hash map is a job with location, description, etc.
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) { //someJobs is the name of our array list of hashmaps that we have given//
        // We don't want to iterate over each column as it is given (employer, location, type of job) because if a new column is added for each job, we'll have to update this code as well//
        //How are "search" and "list" any different?
        //A hashmap can contain any number of key-value pairs, but each key can only have one value. Think of a dictionary in python.//
        if(!someJobs.isEmpty()){
        for (HashMap<String, String> entry : someJobs) { //the colon means "in". We can name "entry" whatever we want
            System.out.println("****");
            for (String key : entry.keySet()) {
                String value = entry.get(key);
                System.out.println(key + ": " + value);
                }
            }
        }
        else {
            System.out.println("Sorry, we can't find that term");
        }
    }
}
