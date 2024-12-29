package org.example;

import org.apache.lucene.document.Field;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TMSession {
    static String data, Analysis;
    static ArrayList<String> analysisReportNames = new ArrayList<String>(); // This Arraylist Stores the name of analysis reports as a string
    static final String reportFileList = "recentReportFiles.txt";

    private static void displayMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("               Team Member");
        System.out.println("--------------------------------------------");
        System.out.println("(1) Analyse Feedback");
        System.out.println("(2) View Analysed Reports");
        System.out.println("(3) View Profile");
        System.out.println("(4) Log out or Sign in with a different account");
        System.out.println("(5) Quit");
        System.out.println("Please select the action you would like to perform from the options listed below");
    }

    public static void runSession() {
        boolean endloop = false;
        do {
            //display Menu
            displayMenu();

            //collect user choice from the menu
            int menuChoice = collectIntegerInput();

            switch (menuChoice) {
                case 1:
                    if (analyseFeedback()) {
                        initializeSaveFeedbackProcedure();
                    }
                    break;
                case 2:
                    viewAnalysedReports();
                    break;
                case 3:
                    //    viewProfile();
                    Session.displayProfile();
                    break;
                case 4: //log out
                    endloop = true;
                    Session.runSession();
                    break;
                case 5: {
                    System.exit(0);
                    break;
                }
                default:
                    displayErrorMessage();
                    break;
            }
        } while (!endloop);
    }

    private static int collectIntegerInput() {
        try {
            Scanner input = new Scanner(System.in);
            int answer = input.nextInt();
            return answer;
        } catch (
                InputMismatchException e) { //checks if user has entered an invalid input an outputs appropriate response
            return 100;
        }
    }

    //Analysing feedback and generating reports
    private static boolean analyseFeedback() {
        boolean validated = false;
        int ans;
        do {
            //display choices
            displayTextInputOptions();
            ans = collectIntegerInput();
            if (ans == 0) return false;
            validated = getData(ans);
        } while (!validated);

        //Create report analysis object
        AnalysisReport report = new AnalysisReport(data);
        //display analysis report
        report.generateAnalysisReport();
        Analysis = report.getAnalysisReport();
        return true;
    }

    private static void displayTextInputOptions() {
        //display choices
        System.out.println("Would you like to:");
        System.out.println("1) Type in the feedback");
        System.out.println("2) Upload the feedback from a file");
        System.out.println("Select option (Enter [0] to return to the menu): ");
    }

    private static boolean getData(int response) {
        //capture the date that the user would like to analyse
        if (response == 1) {
            System.out.println("Please enter the feedback you want to analyse: ");
            data = collectStringInput();
            System.out.println(data);
            return true;
        } else if (response == 2) {
            System.out.println("Please enter the name of the file you would like to read from");
            String feedbackFileName = collectStringInput();

            File fbFile = new File(feedbackFileName);
            //check if file entered exists
            if (fbFile.exists()) {
                //   System.out.println("File Exists");
                return readFeedbackFromFile(fbFile);
            } else System.out.println("The File name you have entered does not exist."); return false;

        } else {
            displayErrorMessage();
            return false;
        }

    }

    //Reads the content of the file given and stores it as the data to be analysed
    private static boolean readFeedbackFromFile(File feedbackData) {
        data = "";
        try {
            Scanner currLine = new Scanner(feedbackData);
            while (currLine.hasNextLine()) {
                //read data from text file into the data variable
                data = data + currLine.nextLine();
            }
            currLine.close(); //close the file
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong. Could not read from the file.");
            return false;
        }
    }


//Save Generated Reports
private static void initializeSaveFeedbackProcedure() {
    boolean validate = false;
    String ans;
    //Ask if user wants to save report
    do {
        //prompt user to save their analysis report
        System.out.println("Would you like to save the Analysis report (y/n):");
        ans = collectStringInput().toLowerCase();
        //check if user entered values other than y or n
        if (ans.equals("y") || ans.equals("n")) {
            validate = true;
        } else {
            System.out.println("You have entered an invalid input");
            validate = false;
        }
    } while (!validate);

    if (ans.equals("y")) {
        saveFeedback();
    } else System.out.println("Analysis report will not be saved.");
}

    private static String collectStringInput() {
        try {
            Scanner input = new Scanner(System.in);
            return input.nextLine().trim();
        } catch (InputMismatchException e) {
            System.out.println("You have entered an invalid input please select an option from the list provided above.");
            return "invalid";
        }
    }

    private static void saveFeedback() {
        System.out.println("Name the analysis report (Please include no spaces in the name):");
        String newReportName = collectStringInput();
        if (!(newReportName.contains(".txt"))) {
            newReportName = newReportName + ".txt";
        }
        //Save to file
        if (saveReportToFile(newReportName)) {
            System.out.println("File " + newReportName + " was saved successfully!");
        } else System.out.println("An error occurred. Could not save file. Please try again later");
        //Add new report to list of recent reports
        readRecentFilesFromFile();
        analysisReportNames.add(newReportName);
        //overwrite reportnamesfile
        if (overwriteRecentFilesFile()) {
            System.out.println("Recent reports list has been updated!");
        } else
            System.out.println("An error occurred. Could not update the recent reports list. Please try again later");
    }

    //updates the contents of the file containing the list of recently created analysis report files
    private static boolean overwriteRecentFilesFile() {
        try {
            FileWriter accessFile = new FileWriter(reportFileList);
            try {
                for (int m = 0; m < analysisReportNames.size(); m++) {
                    accessFile.write(analysisReportNames.get(m)+"\n");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("There waas an error processing your request. Please try again.");
                return false;
            }
            accessFile.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean saveReportToFile(String fileName) {
        try {
            FileWriter accessFile = new FileWriter(fileName);
            accessFile.write(Analysis);
            accessFile.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred. Could not save file. Please try again later");
            return false;
        }
    }

    //reads the list of recently opened files from the textfile into the array analysisReportNames
    private static boolean readRecentFilesFromFile() {
        analysisReportNames.clear();
        File recentReports = new File(reportFileList);
        //   if (recentReports.exists()) {
        try {
            Scanner currLine = new Scanner(recentReports);
            while (currLine.hasNextLine()) {
                //read data from text file into the data variable
                analysisReportNames.add(currLine.nextLine());
            }
            currLine.close(); //close the file
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong. Could not read from the file.");
            return false;
        }
        //      } else System.out.println("File does not Exists"); return false;
    }


    ///View analysed reports
    private static void viewAnalysedReports() {
        boolean validInput = false;
        int reportNumber = 0;
        if (readRecentFilesFromFile()) {
            do {
                //Display list of recently analysed documents
                displayRecentdocuments();

                //prompt user to enter the Analysis report they would like to view
                System.out.println("Please enter the number of the report you would like to view (enter [0] to return to the menu");

                // get file selection from user
                reportNumber = collectIntegerInput();

                if (reportNumber == 0) return;


                //validate Input checks if the user has entered a valid integer input

                validInput = validateSelection(reportNumber);
            } while (reportNumber == 100 || !validInput);

            //open up that document
            File previousReport = new File(analysisReportNames.get(reportNumber - 1));
            //display the contents of the File selected
            displayFeedbackReport(previousReport);

        } else
            System.out.println("Something went wrong could not perform the action requested. Please try again later.");
        //prompt user to select a document to view by entering the number //consider saving the list of documents in an array.
    }

    private static void displayRecentdocuments() {
        for (int j = 0; j < analysisReportNames.size(); j++) {
            System.out.println(j + 1 + ". " + analysisReportNames.get(j));
        }
    }

    //Check if the Analysis report file selected exists.
    private static boolean validateSelection(int check) {
        // checks if user has entered a valid selection for the analysis report they would like to view.
        if ((check <= analysisReportNames.size()) && (check >= 1)) {
            return true;
        } else displayErrorMessage(); return false;
    }

    private static void displayFeedbackReport(File dispayFile) {
        //   File recentReports = new File("recentReportFiles.txt");
        try {
            System.out.println();
            System.out.println("--------------------------------------------");
            System.out.println("               " + dispayFile);
            System.out.println("--------------------------------------------");
            Scanner currLine = new Scanner(dispayFile);
            while (currLine.hasNextLine()) {
                //read data from text file into the data variable
                System.out.println(currLine.nextLine());
            }
            currLine.close(); //close the file
            System.out.println();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Something went wrong. Could not read from the file.");
        }
    }

    private static void displayErrorMessage() {
        System.out.println("You have entered an invalid input please select an option from the list provided above.");
    }


}
