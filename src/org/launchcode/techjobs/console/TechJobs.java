package org.launchcode.techjobs.console;

import java.util.*;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
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

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {

                    ArrayList<HashMap<String,String>>allresult = JobData.findAll();
                    for (Map<String,String>entry:allresult){
                        System.out.println("\n*****");
                        for (String key :entry.keySet()){
                            String value=entry.get(key);
                            System.out.println(key+':'+value);
                        }
                    }
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);
                    Collections.sort(results);
                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();


                if (searchField.equals("all")) {
                    ArrayList<HashMap<String, String>> listing = new ArrayList<>();
                    ArrayList<HashMap<String, String>> all_result = JobData.findAll();
                    String low_searchterm =searchTerm.toLowerCase();
                    for (HashMap<String, String> row : all_result) {
                        for (String key : row.keySet()) {
                            String value = row.get(key).toLowerCase();
                            if (value.contains(low_searchterm)) {
                                listing.add(row);
                            }
                        }
                    }
                    if (listing.isEmpty()) {
                        System.out.println("No results matching ");
                    } else {
                        for (Map<String, String> entry : listing) {
                            System.out.println("\n*****");
                            for (String key : entry.keySet()) {
                                String value = entry.get(key);
                                System.out.println(key + ':' + value);
                            }
                        }
                    }
                }else {
                    String low_searchterm =searchTerm.toLowerCase();
                    ArrayList<HashMap<String,String>>res_searchterm= JobData.findByColumnAndValue(searchField, low_searchterm);
                    ArrayList<HashMap<String, String>>field_search= new ArrayList<>();
                    for (HashMap<String,String>row:res_searchterm){
                        for (String key :row.keySet()){
                            String value=row.get(key);
                            field_search.add(row);
                        }
                    }

                    if (field_search.isEmpty()) {
                        System.out.println("No results matching ");
                    }else {
                        for (Map<String, String> entry : field_search) {
                            System.out.println("\n*****");
                            for (String key : entry.keySet()) {
                                String value = entry.get(key);
                                System.out.println(key + ':' + value);
                            }
                        }
                    }
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

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
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        System.out.println("printJobs is not implemented yet");
    }
}
