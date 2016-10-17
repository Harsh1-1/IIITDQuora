package com.example.harsh.iiitdquora;

/**
 * Created by Tushar on 17-10-2016.
 */

public class User {

    private String EmailId;
    private String Password;
    private String Username;
    private String Contact;
    private String AboutMe;


    public User(String emailId, String password, String username, String contact, String aboutMe) {
        EmailId = emailId;
        Password = password;
        Username = username;
        Contact = contact;
        AboutMe = aboutMe;
    }


    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

}
