package com.revworkforce.controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revworkforce.model.Employee;
import com.revworkforce.model.LeaveRequest;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.service.LeaveService;
import com.revworkforce.service.PerformanceReviewService;
import com.revworkforce.service.EmployeeService;
import com.revworkforce.service.GoalService;
import com.revworkforce.service.NotificationService;
import com.revworkforce.service.AnnouncementService;
import com.revworkforce.model.Announcement;


import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {
	private static final Logger logger = LogManager.getLogger(EmployeeMenu.class);

    private static Scanner sc = new Scanner(System.in);
    private static LeaveService leaveService = new LeaveService();
    private static PerformanceReviewService reviewService = new PerformanceReviewService();
    private static NotificationService notificationService = new NotificationService();
    private static AnnouncementService announcementService = new AnnouncementService();
 
    private static EmployeeService employeeService = new EmployeeService();


    // ========================= MAIN MENU =========================
    public static void show(Employee user) {

    	logger.info("\n Checking notifications...");
        notificationService.viewNotifications(user.getEmployeeId());
        
        logger.info("\n Company Announcements:");
        AnnouncementService annService = new AnnouncementService();
        List<Announcement> list = annService.viewAnnouncements();

        if(list == null || list.isEmpty()){
            logger.info("No announcements available.");
        }else{
            for(Announcement a : list){
                logger.info(a.getMessage() + " | " + a.getCreatedAt());
            }
        }



        while (true) {
        	logger.info("\n=== EMPLOYEE MENU ===");
        	logger.info("Welcome: " + user.getName());
        	logger.info("1. View Profile");
        	logger.info("2. Apply Leave");
        	logger.info("3. Performance Review");
        	logger.info("4. View Leave Balance");
        	logger.info("5. Set Yearly Goal");
        	logger.info("6. View My Goals");
        	logger.info("7. Edit Profile");
        	logger.info("8.  View Announcements");
        	
        	logger.info("9. Logout");

        	logger.info("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewProfile(user);
                    break;

                case 2:
                    applyLeave(user);
                    break;

                case 3:
                    performanceMenu(user);
                    break;

                case 4:
                    viewLeaveBalance(user);
                    break;

                case 5:
                    setGoal(user);
                    break;

                case 6:
                    viewMyGoals(user);
                    break;
                    
                case 7:

                    System.out.print("Enter New Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter New Phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter New Password: ");
                    String password = sc.nextLine();

                    boolean updated = employeeService.editProfile(
                            user.getEmployeeId(),
                            email,
                            phone,
                            password
                    );

                    System.out.println(updated ? "Profile Updated Successfully" : "Update Failed");
                    break;
                case 8:

                    List<Announcement> announcements = announcementService.viewAnnouncements();

                    if(announcements.isEmpty()){
                        logger.info("No announcements available.");
                    }else{
                        for(Announcement a : announcements){
                            logger.info(a.getMessage() + " | " + a.getCreatedAt());
                        }
                    }
                    break;




                case 9:
                	logger.info("Logging out...");
                    return;

                default:
                	logger.info("Invalid choice");
            }
        }
    }

    // ========================= PROFILE =========================
    private static void viewProfile(Employee user) {
    	logger.info("\n--- PROFILE ---");
    	logger.info("ID   : " + user.getEmployeeId());
    	logger.info("Name : " + user.getName());
    	logger.info("Email: " + user.getEmail());
    	logger.info("Role : " + user.getRole());
    }

    // ========================= GOALS =========================
    private static void setGoal(Employee emp) {

        GoalService goalService = new GoalService();

        logger.info("Enter Goal Description: ");
        String desc = sc.nextLine();

        LocalDate deadline = readDate("Enter Deadline");

        if (goalService.addGoal(emp.getEmployeeId(), desc, deadline)) {
        	logger.info("Goal submitted to manager");
        } else {
        	logger.info("Failed to add goal");
        }
    }

    private static void viewMyGoals(Employee emp) {

        GoalService goalService = new GoalService();
        logger.info("\n--- My Goals ---");

        goalService.viewEmployeeGoals(emp.getEmployeeId())
                   .forEach(System.out::println);
    }

    // ========================= LEAVE =========================
    private static void applyLeave(Employee user) {

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployeeId(user.getEmployeeId());

        logger.info("\nLeave Type:");
        logger.info("1. Casual Leave (CL)");
        logger.info("2. Sick Leave (SL)");
        logger.info("3. Paid Leave (PL)");
        logger.info("Choose option: ");

        int typeChoice = sc.nextInt();
        sc.nextLine();

        switch (typeChoice) {
            case 1:
                leave.setLeaveType("CL");
                break;
            case 2:
                leave.setLeaveType("SL");
                break;
            case 3:
                leave.setLeaveType("PL");
                break;
            default:
            	logger.info("Invalid leave type");
                return;
        }

        leave.setStartDate(readDate("Start Date"));
        leave.setEndDate(readDate("End Date"));

        logger.info("Reason: ");
        leave.setReason(sc.nextLine());

        boolean result = leaveService.applyLeave(leave);
        logger.info(result ? "Leave Applied Successfully" : "Failed to Apply Leave");
    }

    private static void viewLeaveBalance(Employee employee) {

        EmployeeService service = new EmployeeService();
        int[] leave = service.getLeaveBalance(employee.getEmployeeId());

        logger.info("\n--- Leave Balance ---");
        logger.info("Casual Leave : " + leave[0]);
        logger.info("Sick Leave   : " + leave[1]);
        logger.info("Paid Leave   : " + leave[2]);
    }

    // ========================= PERFORMANCE =========================
    private static void performanceMenu(Employee user) {

        while (true) {
        	logger.info("\n=== PERFORMANCE REVIEW MENU ===");
        	logger.info("1. Submit Performance Review");
        	logger.info("2. View My Reviews");
        	logger.info("3. Back");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    submitPerformanceReview(user);
                    break;
                case 2:
                    viewMyReviews(user);
                    break;
                case 3:
                    return;
                default:
                	logger.info("Invalid choice");
            }
        }
    }

    private static void submitPerformanceReview(Employee user) {

        PerformanceReview review = new PerformanceReview();
        review.setEmployeeId(user.getEmployeeId());

        logger.info("Self Rating (1–5): ");
        review.setSelfRating(sc.nextInt());
        sc.nextLine();

        logger.info("Self Comments: ");
        review.setSelfComments(sc.nextLine());

        review.setStatus("SUBMITTED");
        review.setReviewDate(LocalDate.now());

        boolean result = reviewService.submitReview(review);
        System.out.println(result ? "Review Submitted Successfully" : "Submission Failed");
    }

    private static void viewMyReviews(Employee user) {

        List<PerformanceReview> reviews =
                reviewService.getMyReviews(user.getEmployeeId());

        if (reviews.isEmpty()) {
            System.out.println("No performance reviews found.");
            return;
        }

        for (PerformanceReview pr : reviews) {
        	logger.info("-----------------------------");
        	logger.info("Review ID: " + pr.getReviewId());
        	logger.info("Self Rating: " + pr.getSelfRating());
        	logger.info("Self Comments: " + pr.getSelfComments());
        	logger.info("Status: " + pr.getStatus());

            if ("REVIEWED".equalsIgnoreCase(pr.getStatus())) {
            	logger.info("Manager Rating: " + pr.getManagerRating());
            	logger.info("Manager Feedback: " + pr.getManagerFeedback());
            } else {
            	logger.info("Awaiting Manager Review");
            }
        }
    }

    // ========================= UTIL =========================
    private static LocalDate readDate(String label) {

        while (true) {
            try {
            	logger.info(label + " (YYYY-MM-DD): ");
                return LocalDate.parse(sc.nextLine());
            } catch (Exception e) {
            	logger.info("Invalid date format. Example: 2026-08-12");
            }
        }
    }
}
