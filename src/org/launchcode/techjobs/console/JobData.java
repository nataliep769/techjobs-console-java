package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs; //Is this simply declaring allJobs is an array list of hash maps?

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     * <p>
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column Column that should be searched.
     * @param value  Value of the field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {
        //we assign the name of the column when we run the function //
        //^^first part defines what type we will be returning^^//
        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>(); //we are creating an empty array list to add hash maps to//

        for (HashMap<String, String> row : allJobs) { //for a particular entity in all entities -- for this in these. A HashMap is a name-value pair. An array list //

            String aValue = row.get(column);

            if (aValue.contains(value)) {
                jobs.add(row);
            }
        }
        return jobs;
    }

    /**
     * Read in data from a CSV file and store it in a list
     */
    public static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

    public static ArrayList<HashMap<String, String>> findByValue(String value) { //we're not searching for column, therefore we don't have to pass it as an argument //
        //if the user selects all //
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) { //A HashMap can contain any number of key-value pairs, for example
            // one row is: (name: Natalie, location: St. Louis, job: Student, employer: Self-employed) in ALL ROWS like this //
            //if the term appears once in a row, print that row but then go to searching the next row -- don't repeatedly print the same row //
            //for a particular entity in all entities -- for this in these.//
                for (String key : row.keySet()) { //key is an arbitrary name we assigned //
                    String aValue = row.get(key);
                    if (!jobs.contains(row)) {
                        if (aValue.toUpperCase().indexOf(value.toUpperCase()) != -1) { //not the same as contains//
                            jobs.add(row);
                        }
                    }
                }
        }
        return jobs;
    }
}
//look for if the row is already in our array list of hash maps