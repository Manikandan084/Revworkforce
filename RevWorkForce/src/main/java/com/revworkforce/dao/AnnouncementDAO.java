package com.revworkforce.dao;

import com.revworkforce.model.Announcement;
import com.revworkforce.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDAO {

    // Admin post announcement
    public boolean addAnnouncement(String message) {
        String sql = "INSERT INTO announcements(message) VALUES(?)";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, message);
            return ps.executeUpdate() > 0;

        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // Employee / Manager view announcements
    public List<Announcement> getAllAnnouncements() {

        List<Announcement> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM announcements ORDER BY created_at DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Announcement a = new Announcement();
                a.setAnnouncementId(rs.getInt("announcement_id"));
                a.setMessage(rs.getString("message"));
                a.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(a);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}

