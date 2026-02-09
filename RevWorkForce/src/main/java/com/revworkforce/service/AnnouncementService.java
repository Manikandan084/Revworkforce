package com.revworkforce.service;

import com.revworkforce.dao.AnnouncementDAO;
import com.revworkforce.model.Announcement;

import java.util.List;

public class AnnouncementService {

    private AnnouncementDAO dao = new AnnouncementDAO();

    public boolean postAnnouncement(String msg){
        return dao.addAnnouncement(msg);
    }

    public List<Announcement> viewAnnouncements(){
        return dao.getAllAnnouncements();
    }
}
