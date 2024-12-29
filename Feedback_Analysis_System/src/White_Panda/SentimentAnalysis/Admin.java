package White_Panda.SentimentAnalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


public class Admin extends Common {
    String fileName = "TM_access_file.txt";
    String givenUsername, givenPassword;
    public static HashMap<String, String> TMAccounts = new HashMap<String, String>();


    public void collectInfo() { // collects the username and password that an admin would like to make changes to
        System.out.println("Please enter the registration details of the user you would like to add or remove.");
        Scanner input = new Scanner(System.in);

        System.out.println("Username: ");
        givenUsername = input.nextLine();

        System.out.println("Password: ");
        givenPassword = input.next();

        populateHashMap(fileName,TMAccounts);
    }

    public void addNewUser() { //function that writes username and password to file the team members accecc list
        writeToFile(givenUsername,givenPassword);
        /*TMAccess.put(givenUsername,givenPassword);*/
        //update hashmap //Add new user to hash map
        populateHashMap(fileName, TMAccounts);
    }

    public void removeUser() {
        //delete user details from hash map
         String name = TMAccounts.remove(givenUsername);
      //  System.out.println(TMAccounts);
        //rewrite overwrite text file with hash map contents
        overwriteFile();
  //      displayhashMap();
    }

    private void writeToFile(String u, String p) {
        try {
            FileWriter accessFile = new FileWriter(fileName,true);
            accessFile.write( u + ", "+ p+"\n");
            accessFile.close();
            System.out.println("Registration complete!");
        } catch (IOException e) {
            System.out.println("Registration unsuccessful. Could not register new account details to the file");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void displayhashMap() {
        System.out.println("Username\t\tPassword");
      for (String i: TMAccounts.keySet()) {
          System.out.println(i +"\t"+TMAccounts.get(i));
      }
    }

    private void overwriteFile() {
        try {
            FileWriter accessFile = new FileWriter(fileName);
            for (String i : TMAccounts.keySet()) {
                accessFile.write( "\n"+i + ", "+ TMAccounts.get(i));
            }
            accessFile.close();
            System.out.println("Account Deleted");
        } catch (IOException e) {
            System.out.println("\"Registration unsuccessful. Could not delete account details. Please try again later.");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
