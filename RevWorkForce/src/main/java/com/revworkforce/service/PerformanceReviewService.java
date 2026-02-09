package com.revworkforce.service;

import com.revworkforce.dao.PerformanceReviewDAO;
import com.revworkforce.model.PerformanceReview;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PerformanceReviewService {
	private static final Logger logger = LogManager.getLogger(PerformanceReviewService.class);

    private PerformanceReviewDAO dao = new PerformanceReviewDAO();

    // Manager: View pending reviews
    public List<PerformanceReview> getPendingReviews() {
        return dao.getPendingReviews();
    }

    //  Manager: Add feedback
    public boolean addManagerFeedback(int reviewId, int rating, String feedback) {
        return dao.addManagerFeedback(reviewId, rating, feedback);
    }

    //  Employee: Submit review
    public boolean submitReview(PerformanceReview review) {
        return dao.submitReview(review);
    }

    // ✅ Employee: View own reviews
    public List<PerformanceReview> getMyReviews(int employeeId) {
        return dao.getReviewsByEmployeeId(employeeId);
    }
}
