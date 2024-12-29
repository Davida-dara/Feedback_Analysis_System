package White_Panda.SentimentAnalysis;

import javax.annotation.processing.Filer;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class LogIn extends Common {
    private static final int HR = 1;
    private static final int TM = 2;
    private String username;
    private String password;
    private String accountUser;
    private String filename;
    public static HashMap<String, String> userAccounts = new HashMap<String, String>();

    public void LogIn() {
        System.out.println("Please enter your details to log in to our system:");

        //instantiate accessfile based on the user is.
        filename = getAccessFileName();
        createAccessFile(filename);

        Scanner input = new Scanner(System.in);
        //Prompt user to enter their username/email address
        System.out.println("Username: ");
        this.username = input.nextLine().trim().toLowerCase();
        //Prompt user to enter their password
        System.out.println("Password: ");
        this.password = input.next();
        populateHashMap(filename, userAccounts);
    }

    public boolean Validate() {
        for (String i : userAccounts.keySet()) {
            if ((username.compareTo(i.toLowerCase())==0)) { //Using compare to and not equals here because the output was always false
                if (password.equals(userAccounts.get(i))) { //ASK: why equal works in this line but not in previous line
                   System.out.println("You have successfully logged in.");
                   return true;
                } else {
                    System.out.println("Your password is incorrect");
                    return false;
                }
            }
        }
        System.out.println("You have entered an invalid username.");
        return false;
    }

    public void writetoFile(String filename) {
        try {
            FileWriter accessFile = new FileWriter(filename + ".txt");
            accessFile.write( username + ", "+ password);
            accessFile.close();
        } catch (IOException e) {
            System.out.println("An error occured could not write to the accessfile");
            e.printStackTrace();
        }

    }

    public String getAccessFileName() {
        //display prompt to get the type of user
        System.out.println("Would you like to Sign in as an (1) Administrator or as a (2) Team member");
        System.out.println("Enter either (1) or (2): ");
        Scanner input = new Scanner(System.in);
        int answer = input.nextInt();

        //Team member = TM = 2; Administrator = HR = 1
        if ( answer == HR) {
           //the HR_access_file.txt file contains the list of HR accounts with access to the program
            accountUser = "Administrator";
         return "HR_access_file.txt";  //uses the HR_access_file.txt file to populate the hashMap

        } else if (answer == TM) {
            //the TR_access_file.txt file contains the list of HR accounts with access to the program
            accountUser = "Team member";
            return "TM_access_file.txt"; //uses the TM_access_file.txt file to populate the hashMap

        } else System.out.println("You have entered an invalid input"); ///more validation is needed in this section!!!
        return "TM_access_file.txt";
    }

    public String getUser() {
        return accountUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<String, String> getHashMap() {
      return userAccounts;
    }
}


