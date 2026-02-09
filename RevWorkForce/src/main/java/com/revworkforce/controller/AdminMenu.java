package com.revworkforce.controller;

import com.revworkforce.model.Employee;
import com.revworkforce.service.GoalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.revworkforce.service.AnnouncementService;

import com.revworkforce.service.EmployeeService;

import java.time.LocalDate;
import java.util.Scanner;

public class AdminMenu {
	private static final Logger logger = LogManager.getLogger(AdminMenu.class);

    private static EmployeeService service = new EmployeeService();
    private static AnnouncementService announcementService = new AnnouncementService();

    private static Scanner sc = new Scanner(System.in);

    public static void show() {

        while (true) {
        	logger.info("\n=== ADMIN MENU ===");
        	logger.info("1. Add Employee");
        	logger.info("2. View All Employees");
        	logger.info("3. Update Employee Leave Balance");
        	logger.info("4. View Employee Goals");
        	logger.info("5. Post Announcement");

        	logger.info("6. Logout");
        	logger.info("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateLeaveBalance();
                    break;
                case 4:
                    viewGoals();
                    break;
                    
                case 5:
                    postAnnouncement();
                    break;

                case 6:
                	logger.info("Logging out...");
                    return;
                default:
                	logger.info("Invalid choice");
            }
        }
    }

    // ADD EMPLOYEE
    private static void addEmployee() {
        
        Employee emp = new Employee();

        logger.info("Name: ");
        emp.setName(sc.nextLine());

        logger.info("Email: ");
        emp.setEmail(sc.nextLine());

        logger.info("Phone: ");
        emp.setPhone(sc.nextLine());

        logger.info("Department: ");
        emp.setDepartment(sc.nextLine());

        logger.info("Designation: ");
        emp.setDesignation(sc.nextLine());

        logger.info("Role (ADMIN / MANAGER / EMPLOYEE): ");
        String role = sc.nextLine().toUpperCase();
        emp.setRole(role);

        String loginId = service.generateLoginId(role);
        emp.setLoginId(loginId);

        logger.info("Generated Login ID: " + loginId);

        logger.info("Password: ");
        emp.setPassword(sc.nextLine());

        emp.setJoiningDate(LocalDate.now());

        logger.info("Salary: ");
        emp.setSalary(sc.nextDouble());
        sc.nextLine();

        emp.setManagerId(null);

        boolean result = service.addEmployee(emp);
        logger.info(result ? "Employee Added Successfully" : "Failed to Add Employee");
    }

    // VIEW EMPLOYEES
    private static void viewEmployees() {

    	logger.info("\n--- Employee List ---");

        for (Employee e : service.getAllEmployees()) {
        	logger.info(
                    e.getEmployeeId() + " | " +
                    e.getName() + " | " +
                    e.getRole()
            );
        }
    }
    private static void viewGoals() {

        GoalService goalService = new GoalService();

        logger.info("\n--- Employee Goals ---");
        goalService.viewAllGoals().forEach(System.out::println);
    }

    // UPDATE LEAVE BALANCE
    private static void updateLeaveBalance() {

    	logger.info("Enter Employee ID: ");
        int empId = sc.nextInt();

        logger.info("Enter CL balance: ");
        int cl = sc.nextInt();

        logger.info("Enter SL balance: ");
        int sl = sc.nextInt();

        logger.info("Enter PL balance: ");
        int pl = sc.nextInt();

        boolean updated = service.updateLeaveBalance(empId, cl, sl, pl);

        if (updated)
            System.out.println("Leave Balance Updated Successfully");
        else
            System.out.println("Failed to Update Leave Balance");
    }
    
    private static void postAnnouncement() {

        logger.info("Enter Announcement Message:");
        String msg = sc.nextLine();

        boolean result = announcementService.postAnnouncement(msg);

        if(result)
            logger.info("Announcement Posted Successfully");
        else
            logger.info("Failed to Post Announcement");
    }

}

