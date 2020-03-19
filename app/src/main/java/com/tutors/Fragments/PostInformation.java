package com.tutors.Fragments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Princess on 10/17/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostInformation {
    @JsonIgnoreProperties("uKey")
    String uKey;

    @JsonProperty("username")
    String username;

    @JsonProperty("post")
    String post;

    @JsonProperty("time")
    String time;

    @JsonProperty("mobileNumber")
    String mobileNumber;

    @JsonProperty("usersKey")
    String usersKey;

    PostInformation(){

    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUsersKey() {
        return usersKey;
    }

    public void setUsersKey(String usersKey) {
        this.usersKey = usersKey;
    }




    public String getuKey() {
        return uKey;
    }

    public void setuKey(String uKey) {
        this.uKey = uKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
