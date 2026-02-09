package com.revworkforce.service;

import com.revworkforce.dao.GoalDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class GoalService {
	private static final Logger logger = LogManager.getLogger(GoalService.class);

    private GoalDAO goalDAO = new GoalDAO();

    // Employee adds goal
    public boolean addGoal(int empId, String desc, LocalDate deadline) {
        return goalDAO.addGoal(empId, desc, deadline);
    }

    // Admin & Manager view all goals
    public List<String> viewAllGoals() {
        return goalDAO.getAllGoals();
    }

    // Manager sets priority
    public boolean setPriority(int goalId, String priority) {
        return goalDAO.updatePriority(goalId, priority);
    }

    // Employee views own goals
    public List<String> viewEmployeeGoals(int empId) {
        return goalDAO.getGoalsByEmployee(empId);
    }
}
