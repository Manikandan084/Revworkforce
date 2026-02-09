package com.revworkforce.dao;

import com.revworkforce.model.LeaveRequest;
import com.revworkforce.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveDAO {
	private static final Logger logger = LogManager.getLogger(LeaveDAO.class);

    // Employee applies leave
    public boolean applyLeave(LeaveRequest leave) {

        String sql = "INSERT INTO leave_requests " +
                "(employee_id, leave_type, from_date, to_date, reason, status, applied_date) " +
                "VALUES (?, ?, ?, ?, ?, 'PENDING', ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leave.getEmployeeId());
            ps.setString(2, leave.getLeaveType());
            ps.setDate(3, java.sql.Date.valueOf(leave.getStartDate()));
            ps.setDate(4, java.sql.Date.valueOf(leave.getEndDate()));
            ps.setString(5, leave.getReason());
            ps.setDate(6, java.sql.Date.valueOf(leave.getAppliedDate()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
        	logger.info("Error applying leave");
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Manager views pending leaves
    public List<LeaveRequest> getPendingLeaves() {

        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM leave_requests WHERE status = 'PENDING'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LeaveRequest leave = new LeaveRequest();

                leave.setLeaveId(rs.getInt("leave_id"));
                leave.setEmployeeId(rs.getInt("employee_id"));
                leave.setLeaveType(rs.getString("leave_type"));
                leave.setStartDate(rs.getDate("from_date").toLocalDate());
                leave.setEndDate(rs.getDate("to_date").toLocalDate());
                leave.setReason(rs.getString("reason"));
                leave.setStatus(rs.getString("status"));
                leave.setAppliedDate(rs.getDate("applied_date").toLocalDate());

                list.add(leave);
            }

        } catch (SQLException e) {
        	logger.info("Error fetching pending leaves");
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Manager approves / rejects leave
    public boolean updateLeaveStatus(int leaveId, String status) {

        String sql = "UPDATE leave_requests SET status = ? WHERE leave_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, leaveId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
        	logger.info("Error updating leave status");
            e.printStackTrace();
        }
        return false;
    }

    // Fetch leave by ID (USED FOR NOTIFICATIONS)
    public LeaveRequest getLeaveById(int leaveId) {

        String sql = "SELECT * FROM leave_requests WHERE leave_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LeaveRequest leave = new LeaveRequest();

                leave.setLeaveId(rs.getInt("leave_id"));
                leave.setEmployeeId(rs.getInt("employee_id"));
                leave.setLeaveType(rs.getString("leave_type"));
                leave.setStartDate(rs.getDate("from_date").toLocalDate()); // ✅ FIXED
                leave.setEndDate(rs.getDate("to_date").toLocalDate());     // ✅ FIXED
                leave.setReason(rs.getString("reason"));
                leave.setStatus(rs.getString("status"));
                leave.setAppliedDate(
                        rs.getDate("applied_date") != null
                                ? rs.getDate("applied_date").toLocalDate()
                                : null
                );

                return leave;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getEmployeeIdByLeaveId(int leaveId) {

        String sql = "SELECT employee_id FROM leave_requests WHERE leave_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("employee_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

}
