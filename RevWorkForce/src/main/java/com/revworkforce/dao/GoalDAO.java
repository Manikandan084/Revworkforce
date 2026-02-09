package com.revworkforce.dao;

import com.revworkforce.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO {
	private static final Logger logger = LogManager.getLogger(GoalDAO.class);


    // EMPLOYEE ADDS GOAL
    public boolean addGoal(int empId, String desc, LocalDate deadline) {

        String sql =
            "INSERT INTO employee_goals (employee_id, goal_description, deadline) " +
            "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, desc);
            ps.setDate(3, Date.valueOf(deadline));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // VIEW ALL GOALS (ADMIN & MANAGER)
    public List<String> getAllGoals() {

        List<String> goals = new ArrayList<>();

        String sql =
            "SELECT g.goal_id, e.name, g.goal_description, g.deadline, g.priority " +
            "FROM employee_goals g " +
            "JOIN employee e ON g.employee_id = e.employee_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String row =
                    "Goal ID: " + rs.getInt("goal_id") +
                    " | Employee: " + rs.getString("name") +
                    " | Goal: " + rs.getString("goal_description") +
                    " | Deadline: " + rs.getDate("deadline") +
                    " | Priority: " + rs.getString("priority");

                goals.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return goals;
    }

    // MANAGER SETS PRIORITY
    public boolean updatePriority(int goalId, String priority) {

        String sql =
            "UPDATE employee_goals " +
            "SET priority = ?, status = 'REVIEWED' " +
            "WHERE goal_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, priority);
            ps.setInt(2, goalId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // EMPLOYEE VIEWS OWN GOALS + PRIORITY
    public List<String> getGoalsByEmployee(int empId) {

        List<String> goals = new ArrayList<>();

        String sql =
            "SELECT goal_description, deadline, priority, status " +
            "FROM employee_goals " +
            "WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String row =
                    "Goal: " + rs.getString("goal_description") +
                    " | Deadline: " + rs.getDate("deadline") +
                    " | Priority: " + rs.getString("priority") +
                    " | Status: " + rs.getString("status");

                goals.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return goals;
    }
}
