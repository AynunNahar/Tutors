package com.tutors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Princess on 9/17/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Information {


    @JsonProperty("name")
    String name;

    @JsonProperty("educationQualification")
    String educationQualification;

    @JsonProperty("teachingExperience")
    String teachingExperience;

    @JsonProperty("subjectCanTeach")
    String subjectCanTeach;

    @JsonProperty("classesCanTeach")
    String classesCanTeach;

    @JsonProperty("availableTime")
    String availableTime;

    @JsonProperty("maxHourDay")
    String maxHourDay;


    @JsonIgnoreProperties("mobile")
    String mobile;
    @JsonIgnoreProperties("email")
    String email;

    @JsonIgnoreProperties("uKey")
    String uKey;

    @JsonIgnoreProperties("occupation")
    String occupation;

    @JsonIgnoreProperties("address")
    String address;

    @JsonIgnoreProperties("availability")
    String availability;

    @JsonProperty("image")
    String image;


    public Information() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEducationQualification() {
        return educationQualification;
    }

    public void setEducationQualification(String educationQualification) {
        this.educationQualification = educationQualification;
    }

    public String getTeachingExperience() {
        return teachingExperience;
    }

    public void setTeachingExperience(String teachingExperience) {
        this.teachingExperience = teachingExperience;
    }

    public String getSubjectCanTeach() {
        return subjectCanTeach;
    }

    public void setSubjectCanTeach(String subjectCanTeach) {
        this.subjectCanTeach = subjectCanTeach;
    }

    public String getClassesCanTeach() {
        return classesCanTeach;
    }

    public void setClassesCanTeach(String classesCanTeach) {
        this.classesCanTeach = classesCanTeach;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getMaxHourDay() {
        return maxHourDay;
    }

    public void setMaxHourDay(String maxHourDay) {
        this.maxHourDay = maxHourDay;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuKey() {
        return uKey;
    }

    public void setuKey(String uKey) {
        this.uKey = uKey;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
