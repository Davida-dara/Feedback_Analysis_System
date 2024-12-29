package White_Panda.SentimentAnalysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.io.File;

public class Common {
    public void createAccessFile(String filename) {
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

    public void populateHashMap(String filename, HashMap<String, String> userList) {
        try {
            File accessFile = new File(filename);
            //Check if file exists
            if (accessFile.exists()) {
                Scanner currLine = new Scanner(accessFile);
                while (currLine.hasNextLine()) {
                    //read each line of the textfile into a temporary string
                    String tempString = currLine.nextLine();

                    //extract the username section of the string
                    String usernameInput = tempString.substring(0,tempString.indexOf(','));

                    //extract the password section of the string
                    String passwordInput = tempString.substring(tempString.indexOf(',')+2); ;

                    //populate the hashMap using the extracted username and password
                    userList.put(usernameInput, passwordInput);
                }

                //Close the file
                currLine.close();
            } else {
                //print out error message
                System.out.println("The file does not exist.");
            }
        } catch (FileNotFoundException e) {
            //print out error message
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

