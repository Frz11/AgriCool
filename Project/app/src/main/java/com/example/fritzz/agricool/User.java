package com.example.fritzz.agricool;

/**
 * Created by Cristi on 4/18/2018.
 */

public class User {
    public String username,password,email,dateOfBirth,securityQuestion,securityAnswer;
    public User(){}
    public User(String username,String password,String email, String dateOfBirth,String securityQuestion,String securityAnswer){
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.securityAnswer = securityAnswer;
        this.securityQuestion = securityQuestion;
    }
    public User(User user){
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.securityQuestion = user.securityQuestion;
        this.securityAnswer = user.securityAnswer;
        this.dateOfBirth = user.dateOfBirth;
    }
}
