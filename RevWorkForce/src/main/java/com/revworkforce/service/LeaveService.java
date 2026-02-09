package com.revworkforce.service;

import com.revworkforce.dao.LeaveDAO;
import com.revworkforce.model.LeaveRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.LocalDate;
import java.util.List;

public class LeaveService {
	private static final Logger logger = LogManager.getLogger(LeaveService.class);

    private LeaveDAO leaveDAO;
    private NotificationService notificationService;

    public LeaveService() {
        this.leaveDAO = new LeaveDAO();
        this.notificationService = new NotificationService();
    }

    // Employee applies leave
    public boolean applyLeave(LeaveRequest leave) {

        if (leave.getStartDate() == null || leave.getEndDate() == null) {
        	logger.info("Dates cannot be null");
            return false;
        }

        if (leave.getStartDate().isAfter(leave.getEndDate())) {
        	logger.info("Start date cannot be after end date");
            return false;
        }

        if (leave.getStartDate().isBefore(LocalDate.now())) {
        	logger.info("Cannot apply leave for past dates");
            return false;
        }

        leave.setAppliedDate(LocalDate.now());
        return leaveDAO.applyLeave(leave);
    }

    //  Manager views pending leaves
    public List<LeaveRequest> getPendingLeaves() {
        return leaveDAO.getPendingLeaves();
    }

    //  Manager approves leave
    public boolean approveLeave(int leaveId) {
    	


        LeaveRequest leave = leaveDAO.getLeaveById(leaveId);
        if (leave == null) return false;

        boolean updated = leaveDAO.updateLeaveStatus(leaveId, "APPROVED");

        if (updated) {
        	int empId = leaveDAO.getEmployeeIdByLeaveId(leaveId);

        	notificationService.notifyEmployee(empId, "Your leave has been approved");

        }
        return updated;
    }

    public boolean rejectLeave(int leaveId) {
    	int empId = leaveDAO.getEmployeeIdByLeaveId(leaveId);

        LeaveRequest leave = leaveDAO.getLeaveById(leaveId);
        if (leave == null) return false;

        boolean updated = leaveDAO.updateLeaveStatus(leaveId, "REJECTED");

        if (updated) {
        	notificationService.notifyEmployee(empId, "Your leave has been rejected");
        }
        return updated;
    }

}
