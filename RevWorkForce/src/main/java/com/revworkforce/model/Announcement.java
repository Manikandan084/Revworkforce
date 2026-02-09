package com.revworkforce.model;

import java.sql.Timestamp;

public class Announcement {

    private int announcementId;
    private String message;
    private Timestamp createdAt;

    public Announcement(){}

    public Announcement(String message){
        this.message = message;
    }

    public int getAnnouncementId() { return announcementId; }
    public void setAnnouncementId(int id) { this.announcementId = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
