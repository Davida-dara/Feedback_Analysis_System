package White_Panda.SentimentAnalysis;

import java.util.HashMap;
import java.util.Scanner;

public class Home {
    public static String userAccount, userPassword, accountHolder;
    //  public static HashMap<String, String> TMAccounts = new HashMap<String, String>();
    public static void main(String[] args) {
        onboarding();
        System.exit(0);
    }

    public static void onboarding(){
        //User has successfully signed in to the program
        System.out.println("\t" +"WHITE PANDA" +"\n" +
                "\t" + "Your reviews, Our Expertise, Your Success");

        //Get the position of the person trying to use the program i.e Admin or Team memeber
        LogIn currUser = new LogIn();

        currUser.LogIn();

        //Validate log in credentials of new user
        if (currUser.Validate()) {
            userAccount = currUser.getUser();
            accountHolder = currUser.getUsername();
            userPassword = currUser.getPassword();
            // tempUserAccounts = currUser.getHashMap();

            //    System.out.println("You may proceed");
            //Begin running program
            startProgram();
        }
    }

    public static void startProgram() {
        System.out.println("\t\t" + userAccount);
        System.out.println("Please select the action you would like to perform from the options listed below");

        if (userAccount.equals("Administrator")) {
            runAdminProgram();
        } else {
            runTeamMemberProgram();
        }
    }

    public static void runAdminProgram() {
        boolean keepGoing = true;
        while (keepGoing) {
            //display menu options
            displayHRMenu();
            //create Admin object
            Admin major = new Admin();
            //Collect user menu option
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();

            //switch statement thata performs different actions based on the user's input
            switch (option) {
                case 1: {
                    //Add new user to textfile document
                    major.collectInfo();
                    major.addNewUser();
                    //display hashmap contents
                    major.displayhashMap();
                    break;
                }
                case 2: {
                    major.collectInfo();
                    major.removeUser();
                    break;
                }
                case 3: {
                    displayProfile();
                    break;
                }
                case 4: {
                    //ask user if they would like to log out or sign in with a different account
                    System.out.println("Are you sure you want to log our or sign in with a different account? (y/n)");
                    String ans = input.next();
                    //input validation required here!!!!
                    if (ans.equals("y") || (ans.equals("yes"))) {
                        onboarding();
                    } else continue;
                    break;
                }
                case 5: {
                    //ask user if they would like to quit the program
                    System.out.println("Are you sure you want exit the program (y/n)");
                    String ans = input.next().toLowerCase();
                    //input validation required here!!!!
                    if (ans.equals("y") || (ans.equals("yes"))) {
                        keepGoing = false;
                  //      System.exit(0);
                    }
                }
            }
        }

    }

    public static void runTeamMemberProgram() {
        boolean runProgram = true;
        while (runProgram) {
            //display menu options
            displayTMMenu();
            //ru
        }
    }

    public static void displayTMMenu(){
        System.out.println("(1) Analyse Feedback");
        System.out.println("(2) View Analysed Reports");
        System.out.println("(3) View Profile");
        System.out.println("(4) Log out or Sign in with a different account");
        System.out.println("(5) Quit");
    }

    public static void displayHRMenu() {
        System.out.println("\t\t" + userAccount);
        System.out.println("(1) Add a new user log in details");
        System.out.println("(2) Remove user log in details");
        System.out.println("(3) View Profile");
        System.out.println("(4) Log out or Sign in with a different account");
        System.out.println("(5) Quit");
    }

    public static void displayProfile(){
        //Display profile info
        System.out.println("Username:" + accountHolder);
        System.out.println("Email Address:");
        System.out.println("User:" + userAccount );
    }
}