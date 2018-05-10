package com.example.fritzz.agricool;

/**
 * Created by Cristi on 4/18/2018.
 */

public class User {
    public String Username,Password,Email,DateOfBirth,SecurityQuestion,SecurityAnswer,regdate;
    public int user_id;
    public User(){}
    public User(int user_id,String username,String password,String email, String dateOfBirth,
                String securityQuestion,String securityAnswer,String regdate){
        this.Username = username;
        this.Password = password;
        this.Email = email;
        this.DateOfBirth = dateOfBirth;
        this.SecurityAnswer = securityAnswer;
        this.SecurityQuestion = securityQuestion;
        this.user_id = user_id;
        this.regdate = regdate;
    }
    public User(User user){
        this.Username = user.Username;
        this.Password = user.Password;
        this.Email = user.Email;
        this.SecurityQuestion = user.SecurityQuestion;
        this.SecurityAnswer = user.SecurityAnswer;
        this.DateOfBirth = user.DateOfBirth;
        this.user_id = user.user_id;
        this.regdate = user.regdate;
    }
}
