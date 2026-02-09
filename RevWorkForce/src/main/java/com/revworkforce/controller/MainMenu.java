package com.revworkforce.controller;

import com.revworkforce.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenu {
	private static final Logger logger = LogManager.getLogger(MainMenu.class);

    public static void show(Employee user) {

        String role = user.getRole().trim().toUpperCase();

        switch (role) {

            case "ADMIN":
                AdminMenu.show();
                break;

            case "MANAGER":
                ManagerMenu.show(user);   // ✅ pass user
                break;

            case "EMPLOYEE":
                EmployeeMenu.show(user);  // ✅ pass user
                break;

            default:
            	logger.info("Unknown role: " + role);
        }
    }
}
