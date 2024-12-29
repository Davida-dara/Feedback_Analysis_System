package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HRSession {
    private static String enteredUsername, enteredEmail, enteredPassword;
    static String tmFile = "TM_access_file.txt";

    public static void runSession() {
        boolean loopMenu = true;
        do {
            try {
                //display Menu
                displayMenu();

                //collect user choice from the menu option
                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                switch (choice) {
                    case 1: { //Add new user log in details
                        //collect details of the new user from the Admin
                        collectInfo();
                        //write to file
                        writeToFile(enteredEmail, enteredUsername, enteredPassword);
                        //update to hashmap
                        Session.populateHashMap(tmFile, Session.TMAccounts);
                        break;
                    }
                    case 2: { //Remove old user log in details
                        //collect name of user to be deleted.
                        System.out.println("Please enter the name of the user you would like to remove: ");
                        Scanner input2 = new Scanner(System.in);
                        String deleteuser = input2.nextLine().toLowerCase();

                        //remove user details: Checks if the name entered belongs to a user, deletes user form hashmap.
                        removeUser(deleteuser); //ovewrites file with updated hashmap contents
                        break;
                    }
                    case 3: { //View Profile
                        Session.displayProfile();
                        loopMenu = true; //consider changing the name of the boolean variable
                        break;
                    }
                    case 4: { //Log out or Sign in with a different account
                        Session.runSession();
                        loopMenu = false;
                        break;
                    }
                    case 5: { //end program
                        System.exit(0);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("You have entered an invalid input please select an option from the list provided above.");
            }
        } while (loopMenu);
    }

    private static void displayMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("               Administrator");
        System.out.println("--------------------------------------------");
        System.out.println("(1) Add a new user log in details");
        System.out.println("(2) Remove user log in details");
        System.out.println("(3) View Profile");
        System.out.println("(4) Log out or Sign in with a different account");
        System.out.println("(5) Quit");
        System.out.println("Please select the action you would like to perform from the options listed above");
    }

    private static void collectInfo() {
        // collects the username and password that an admin would like to make changes to
        System.out.println("Please enter the registration details of the user you would like to add.");
        Scanner input = new Scanner(System.in);

        System.out.println("Email Address: ");
        enteredEmail = input.nextLine();

        System.out.println("Name: ");
        enteredUsername = input.nextLine();

        System.out.println("Password: ");
        enteredPassword = input.next();

    }

    private static void overwriteFile() {
        try {
            FileWriter accessFile = new FileWriter(tmFile);
            for (String i : Session.TMAccounts.keySet()) {
                accessFile.write( Session.TMAccounts.get(i).getUsername() + ", " + Session.TMAccounts.get(i).getEmail() + "; " + i + "\r\n");
            }
            accessFile.close();
            System.out.println("Account Deleted");
        } catch (IOException e) {
            System.out.println("\"Registration unsuccessful. Could not delete account details. Please try again later.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void writeToFile(String email, String u, String p) {
        try {
            FileWriter accessFile = new FileWriter(tmFile, true);
            accessFile.write(email +", " + u + "; " + p + "\r\n");
            accessFile.close();
            System.out.println("Registration complete!");
        } catch (IOException e) {
            System.out.println("Registration unsuccessful. Could not register new account details to the file");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void removeUser(String deletedName) {
        boolean found = false;
        //rewrite overwrite text file with hash map contents
        for (String i: Session.TMAccounts.keySet()) {
            if (deletedName.compareTo(Session.TMAccounts.get(i).getUsername().toLowerCase()) == 0) {
                Session.TMAccounts.remove(i); //removes the users details from the hashmap
                found = true; //signifies that the name entered was found
                break; //exit the for loop
            }
        }

        if (!found) {
            System.out.println("You have entered an unregistered username, Please enter the name of a currently registered user.");
        } else overwriteFile();
        //      displayhashMap();
    }
}
