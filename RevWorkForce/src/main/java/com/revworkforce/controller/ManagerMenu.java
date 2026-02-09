package com.revworkforce.controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revworkforce.model.Employee;
import com.revworkforce.model.LeaveRequest;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.service.LeaveService;
import com.revworkforce.service.PerformanceReviewService;
import com.revworkforce.service.GoalService;
import com.revworkforce.model.Announcement;
import com.revworkforce.service.AnnouncementService;



import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
	private static final Logger logger = LogManager.getLogger(ManagerMenu.class);


    private static Scanner sc = new Scanner(System.in);

    private static LeaveService leaveService = new LeaveService();
    private static PerformanceReviewService reviewService = new PerformanceReviewService();
    private static AnnouncementService announcementService = new AnnouncementService();


    // Accept logged-in manager
    public static void show(Employee manager) {

        while (true) {
        	logger.info("\n=== MANAGER MENU ===");
        	logger.info("Welcome Manager: " + manager.getName());

        	logger.info("1. View Pending Leaves");
        	logger.info("2. Approve / Reject Leave");
        	logger.info("3. View Performance Reviews");
        	logger.info("4. Give Performance Feedback");
        	logger.info("5. Review Employee Goals");
        	logger.info("6. View Announcements");

        	logger.info("7. Logout");

        	logger.info("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    viewPendingLeaves();
                    break;

                case 2:
                    processLeave();
                    break;

                case 3:
                    viewPerformanceReviews();
                    break;

                case 4:
                    givePerformanceFeedback();
                    break;
                    
                case 5:
                    reviewGoals();
                    break;
                case 6:

                    logger.info("\n Company Announcements:");

                    List<Announcement> announcements = announcementService.viewAnnouncements();

                    if(announcements == null || announcements.isEmpty()){
                        logger.info("No announcements available.");
                    }else{
                        for(Announcement a : announcements){
                            logger.info(a.getMessage() + " | " + a.getCreatedAt());
                        }
                    }
                    break;


                case 7:
                	logger.info("Logging out...");
                    return;

                default:
                	logger.info("Invalid choice");
            }
        }
    }

    // LEAVE FEATURES

    // View all pending leaves
    private static void viewPendingLeaves() {

        List<LeaveRequest> leaves = leaveService.getPendingLeaves();

        if (leaves.isEmpty()) {
        	logger.info("No pending leave requests");
            return;
        }

        logger.info("\n--- Pending Leave Requests ---");

        for (LeaveRequest leave : leaves) {
        	logger.info(
                    "Leave ID: " + leave.getLeaveId() +
                    " | Emp ID: " + leave.getEmployeeId() +
                    " | Type: " + leave.getLeaveType() +
                    " | From: " + leave.getStartDate() +
                    " | To: " + leave.getEndDate() +
                    " | Reason: " + leave.getReason() +
                    " | Status: " + leave.getStatus()
            );
        }
    }

    // ✅ Approve or Reject leave
    private static void processLeave() {

    	logger.info("Enter Leave ID: ");
        int leaveId = sc.nextInt();
        sc.nextLine();

        logger.info("Approve or Reject (A/R): ");
        String decision = sc.nextLine().toUpperCase();

        boolean result;

        if (decision.equals("A")) {
            result = leaveService.approveLeave(leaveId);
            logger.info(result ? "Leave Approved" : "Failed to approve leave");

        } else if (decision.equals("R")) {
            result = leaveService.rejectLeave(leaveId);
            logger.info(result ? "Leave Rejected" : "Failed to reject leave");

        } else {
        	logger.info("Invalid choice (Use A or R)");
        }
    }
    
    private static void reviewGoals() {

        GoalService goalService = new GoalService();
        Scanner sc = new Scanner(System.in);

        logger.info("\n--- All Employee Goals ---");
        goalService.viewAllGoals().forEach(System.out::println);

        logger.info("\nEnter Goal ID: ");
        int goalId = sc.nextInt();
        sc.nextLine();

        logger.info("Set Priority (High / Medium / Low): ");
        String priority = sc.nextLine();

        if (goalService.setPriority(goalId, priority)) {
        	logger.info("Priority updated");
        } else {
        	logger.info("pdate failed");
        }
    }


    // PERFORMANCE REVIEW FEATURES 

    // View submitted performance reviews
    private static void viewPerformanceReviews() {

        List<PerformanceReview> reviews = reviewService.getPendingReviews();

        if (reviews.isEmpty()) {
        	logger.info("✅ No performance reviews submitted");
            return;
        }

        System.out.println("\n--- Performance Reviews ---");

        for (PerformanceReview pr : reviews) {
        	logger.info(
                    "Review ID: " + pr.getReviewId() +
                    " | Emp ID: " + pr.getEmployeeId() +
                    " | Self Rating: " + pr.getSelfRating() +
                    " | Comments: " + pr.getSelfComments() +
                    " | Date: " + pr.getReviewDate() +
                    " | Status: " + pr.getStatus()
            );
        }
    }

    // Manager gives feedback & rating
    private static void givePerformanceFeedback() {

    	logger.info("Enter Review ID: ");
        int reviewId = sc.nextInt();
        sc.nextLine();

        logger.info("Enter Manager Rating (1-5): ");
        int rating = sc.nextInt();
        sc.nextLine();

        logger.info("Enter Manager Feedback: ");
        String feedback = sc.nextLine();

        boolean result = reviewService.addManagerFeedback(reviewId, rating, feedback);

        logger.info(result
                ? "Feedback submitted successfully"
                : "Failed to submit feedback");
    }
    private static void viewPendingPerformanceReviews() {

        List<PerformanceReview> reviews = reviewService.getPendingReviews();

        if (reviews.isEmpty()) {
        	logger.info("No pending performance reviews.");
            return;
        }

        logger.info("\n=== PENDING PERFORMANCE REVIEWS ===");

        for (PerformanceReview pr : reviews) {
        	logger.info("------------------------------");
        	logger.info("Review ID   : " + pr.getReviewId());
        	logger.info("Employee ID : " + pr.getEmployeeId());
        	logger.info("Self Rating : " + pr.getSelfRating());
        	logger.info("Comments    : " + pr.getSelfComments());
        	logger.info("Status      : " + pr.getStatus());
        }
    }

}
