package com.revworkforce.dao;

import com.revworkforce.model.PerformanceReview;
import com.revworkforce.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PerformanceReviewDAO {
	private static final Logger logger = LogManager.getLogger(PerformanceReviewDAO.class);


    //  EMPLOYEE: SUBMIT REVIEW 
    public boolean submitReview(PerformanceReview review) {

        String sql = "INSERT INTO performance_reviews " +
                     "(employee_id, self_rating, self_comments, status) " +
                     "VALUES (?, ?, ?, 'SUBMITTED')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, review.getEmployeeId());
            ps.setInt(2, review.getSelfRating());
            ps.setString(3, review.getSelfComments());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //  EMPLOYEE: VIEW MY REVIEWS 
    public List<PerformanceReview> getReviewsByEmployeeId(int employeeId) {

        List<PerformanceReview> list = new ArrayList<>();

        String sql = "SELECT * FROM performance_reviews WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                PerformanceReview pr = new PerformanceReview();
                pr.setReviewId(rs.getInt("review_id"));
                pr.setEmployeeId(rs.getInt("employee_id"));
                pr.setSelfRating(rs.getInt("self_rating"));
                pr.setSelfComments(rs.getString("self_comments"));
                pr.setManagerRating((Integer) rs.getObject("manager_rating"));
                pr.setManagerFeedback(rs.getString("manager_feedback"));
                pr.setStatus(rs.getString("status"));

                list.add(pr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //  MANAGER: VIEW PENDING REVIEWS 
    public List<PerformanceReview> getPendingReviews() {

        List<PerformanceReview> list = new ArrayList<>();

        String sql = "SELECT * FROM performance_reviews WHERE status = 'SUBMITTED'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                PerformanceReview pr = new PerformanceReview();
                pr.setReviewId(rs.getInt("review_id"));
                pr.setEmployeeId(rs.getInt("employee_id"));
                pr.setSelfRating(rs.getInt("self_rating"));
                pr.setSelfComments(rs.getString("self_comments"));
                pr.setStatus(rs.getString("status"));

                list.add(pr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //  MANAGER: GIVE FEEDBACK 
    public boolean addManagerFeedback(int reviewId, int rating, String feedback) {

        String sql = "UPDATE performance_reviews " +
                     "SET manager_rating = ?, manager_feedback = ?, status = 'REVIEWED' " +
                     "WHERE review_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, rating);
            ps.setString(2, feedback);
            ps.setInt(3, reviewId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
