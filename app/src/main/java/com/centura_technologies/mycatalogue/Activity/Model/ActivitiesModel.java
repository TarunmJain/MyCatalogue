package com.centura_technologies.mycatalogue.Activity.Model;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 16-08-2016.
 */
public class ActivitiesModel {
    private String OwnedBy;
    private String Subject;
    private String DueDate;
    private String Contact;
    private String Status;
    private String Priority;
    private String Description;
    private String EventTitle;
    private String FromDate;
    private String ToDate;
    private String Participants;
    private ArrayList<String> Contacts;
    private String CallTitle;
    private String CallType;
    private String Result;
    private String ActivityType;

    public ActivitiesModel(){
        OwnedBy="";
        Subject="";
        DueDate="";
        Contact="";
        Status="";
        Priority="";
        Description="";
        EventTitle="";
        FromDate="";
        ToDate="";
        Participants="";
        Contacts=new ArrayList<String>();
        CallTitle="";
        CallType="";
        Result="";
        ActivityType="";
    }

    public String getOwnedBy() {
        return OwnedBy;
    }

    public void setOwnedBy(String ownedBy) {
        OwnedBy = ownedBy;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getParticipants() {
        return Participants;
    }

    public void setParticipants(String participants) {
        Participants = participants;
    }

    public ArrayList<String> getContacts() {
        return Contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        Contacts = contacts;
    }

    public String getCallTitle() {
        return CallTitle;
    }

    public void setCallTitle(String callTitle) {
        CallTitle = callTitle;
    }

    public String getCallType() {
        return CallType;
    }

    public void setCallType(String callType) {
        CallType = callType;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getActivityType() {
        return ActivityType;
    }

    public void setActivityType(String activityType) {
        ActivityType = activityType;
    }
}
