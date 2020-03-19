package com.tutors;

/**
 * Created by Princess  on 10/22/2017.
 */

public class Contact {

    String emailId;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Contact(){

    }

    public Contact(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
