package com.revworkforce.dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revworkforce.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
	private static final Logger logger = LogManager.getLogger(NotificationDAO.class);


    // Add notification
    public void addNotification(int employeeId, String message) {

        String sql = "INSERT INTO notifications (employee_id, message, is_read) VALUES (?, ?, false)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setString(2, message);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get unread notifications
    public List<String> getUnreadNotifications(int employeeId) {

        List<String> list = new ArrayList<>();

        String sql = "SELECT message FROM notifications WHERE employee_id = ? AND is_read = false";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("message"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Mark as read
    public void markAsRead(int employeeId) {

        String sql = "UPDATE notifications SET is_read = true WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
