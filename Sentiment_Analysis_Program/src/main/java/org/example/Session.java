package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

class Session {

    private static String emailAddress, name, userType;
    private static User newUser;
    private static HashMap<String, User> HRAccounts = new HashMap<String, User>();
    public static HashMap<String, User> TMAccounts = new HashMap<String, User>();
    public static void runSession() {
        //Reset Program
        resetProgram();

        //Start Program
        //display welcome message
        welcomeMessage();

        // validate users log in credentials
       if (logInUser()){
           if (userType.equals("0")) {
               HRSession.runSession();
           } else TMSession.runSession();
       } else printErrorMessage();

    }

    private static void resetProgram() {
        //Instantiate Variables
        emailAddress = "";
        name = "";
        userType = "";

        //Populate HaashMaps
        populateHashMap("TM_access_file.txt", TMAccounts);
        populateHashMap("HR_access_file.txt", HRAccounts);
    }

    //Display Welcome message to the user
    private static void welcomeMessage() {
        System.out.println("--------------------------------------------");
        System.out.println("               WHITE PANDA");
        System.out.println("--------------------------------------------");
        System.out.println();
    }

    //checks user's log in credentials provided and returns true if they are valid
    private static boolean logInUser() {
        //collect user info
        String password = collectUserDetails();

        //create new user object
        newUser = new User(emailAddress, "Unknown");
       // newUser.User(emailAddress, "Unknown");

        //validate log in info
        if (userType.equals("0")) {
            return validateUserDetails(HRAccounts, password);
        } else return validateUserDetails(TMAccounts, password);

    }

    private static void printErrorMessage() {
        System.out.println("You have entered an invalid username or password Please try again.");
    }

    //gets user info to be used for validation processes at later stage
    public static String collectUserDetails() {
        Scanner input = new Scanner(System.in);
        do {
            //display prompt to get the type of user
            System.out.println("Would you like to Sign in as an (0) Administrator or as a (1) Team member");
            System.out.println("Enter either (0), (1) or (e) to exit the program: ");
            //validate input
            userType = input.next();
        } while (!validateUserType(userType));


        //collect user data
        System.out.println("Please enter your details to log in to our system:");
        System.out.println("Email Address:");
        emailAddress = input.next().toLowerCase();
        System.out.println("Password:");
        String givenPassword = input.next();

        return givenPassword;
    }

    //Validates users info by checking if details entered by user is similar to any of those stored in the hashmaps
    private static boolean validateUserDetails(HashMap<String, User> userList, String pass) {
            for (String i: userList.keySet()) {
             //   System.out.println(emailAddress + " " + userList.get(i).getEmail());
           //     System.out.println(pass + " " + i);
                if (newUser.validateEmail(userList.get(i).getEmail())) {
                    if (newUser.validatePassword(i, pass)) {
                        name = userList.get(i).getUsername();
                        emailAddress = userList.get(i).getEmail();
                      return true;
                    }
                }
            }
        return false;
    }

    //Instantiates hashmaps with required user data from textfiles files
    public static void populateHashMap(String fileName, HashMap<String, User> userList) {
        try {
            File accessFile = new File(fileName);
            //Check if file exists
            if (accessFile.exists()) {
                Scanner currLine = new Scanner(accessFile);
                while (currLine.hasNextLine()) {
                    //read each line of the textfile into a temporary string
                    String tempString = currLine.nextLine();

                    //extract the username section of the string
                    String emAddress = tempString.substring(0,tempString.indexOf(','));

                    //extract the password section of the string
                    String uName = tempString.substring(tempString.indexOf(',')+2, tempString.indexOf(';'));

                    String psWord = tempString.substring(tempString.indexOf(';')+2);

                    //populate the hashMap using the extracted username and password
                    User tmUser = new User(emAddress, uName);
                 //   tmUser.User(emAddress, uName);
                    userList.put(psWord,tmUser);
                }

                //Close the file
                currLine.close();
            } else {
                //print out error message
                System.out.println("The file does not exist.");
                createAccessFile(fileName);
            }
        } catch (FileNotFoundException e) {
            //print out error message
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void createAccessFile(String filename) {
        File accessList = new File(filename);
        try {
            if (accessList.createNewFile()) {
                System.out.println(filename + " has been created!");
            }
        } catch (IOException e) {
            System.out.println("Could not create file. An error occurred.");
            e.printStackTrace();
        }
    }
   //     welcomeMessage();

        //prompt them to enter their information
        //collect their info and save
        //user logs in successfully
        //program begins
        //determine the type of user
        //Begin User program

        //TM Program;
        // - display menu page
        // - prompt user to make choice

        //User chooses to create new text to analyze
        //text is analyzed
        //analysis report is displayed with bars indicating Positive negative and neutral percentages.

    public static void displayProfile() {
        System.out.println("--------------------------------------------");
        System.out.println("                  Profile");
        System.out.println("--------------------------------------------");
        System.out.println("Name: " + name);
        System.out.println("Email Address: "+ emailAddress);
    }

    //INPUT VALIDATIONS
   //validation to ensure a valid input has been entered by the user
    private static boolean validateUserType(String ans) {
        if (ans.equals("1") || ans.equals("0")) {
            return true;
        } else if (ans.equals("e")) {
            System.exit(0);
            return true;
        } else {
            System.out.println("You have entered an invalid value. Please try again.");
        }
        return false;
    }

}
