package com.revworkforce.service;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EmployeeService {

    private static final Logger logger =
            LogManager.getLogger(EmployeeService.class);

    private EmployeeDAO dao = new EmployeeDAO();

    // Generate Login ID
    public String generateLoginId(String role) {
        return dao.generateLoginId(role);
    }

    // Add employee
    public boolean addEmployee(Employee emp) {
        boolean result = dao.addEmployee(emp);
        if(result)
            logger.info("Employee added successfully");
        else
            logger.error("Employee add failed");
        return result;
    }

    // View all employees
    public List<Employee> getAllEmployees() {
        return dao.getAllEmployees();
    }

    // Edit employee profile
    public boolean editProfile(int employeeId,
                               String email,
                               String phone,
                               String password) {

        boolean result =
                dao.updateEmployeeProfile(employeeId, email, phone, password);

        if(result)
            logger.info("Profile updated successfully");
        else
            logger.error("Profile update failed");

        return result;
    }

    // Admin update leave balance
    public boolean updateLeaveBalance(int empId, int cl, int sl, int pl) {
        return dao.updateLeaveBalance(empId, cl, sl, pl);
    }

    // Employee view leave balance
    public int[] getLeaveBalance(int empId) {
        return dao.getLeaveBalance(empId);
    }
}
