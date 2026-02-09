package com.revworkforce.controller;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Scanner;

public class LoginMain {
	private static final Logger logger = LogManager.getLogger(LoginMain.class);


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        EmployeeDAO dao = new EmployeeDAO();

        logger.info("=== RevWorkForce Login ===");

        logger.info("Employee ID: ");
        String loginId = sc.nextLine();   // AD01 / MGR01 / EMP01

        logger.info("Password: ");
        String password = sc.nextLine();

        Employee user = dao.login(loginId, password);   // ✅ FIXED

        if (user != null) {
        	logger.info("Login Successful");
        	logger.info("Role: " + user.getRole());

            MainMenu.show(user);

        } else {
        	logger.info("Invalid Credentials");
        }
    }
}
