package org.example;

import java.util.HashMap;

public class User {
    private String userName, eMail, password;
    public User(String eAddress, String name) { //Collect User details//Prompt user to enter their details
        this.eMail = eAddress;
        this.userName = name;
    }

    public boolean validateEmail(String trueEmail) {
     //   System.out.println(trueEmail);
        return eMail.equals(trueEmail);
    }

    public boolean validatePassword(String truePassword, String givenPsWord) {
        //   System.out.println(truePassword);
        return givenPsWord.equals(truePassword);
    }

    public String getUsername(){return userName;}

    public String getEmail(){
        return eMail;
    }

    //Populate Arrays

}
