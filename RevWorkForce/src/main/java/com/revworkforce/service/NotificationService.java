package com.revworkforce.service;

import java.util.List;
import com.revworkforce.dao.NotificationDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotificationService {
	private static final Logger logger = LogManager.getLogger(NotificationService.class);

    private NotificationDAO dao = new NotificationDAO();

    public void notifyEmployee(int employeeId, String message) {
        dao.addNotification(employeeId, message);
    }

    public void viewNotifications(int employeeId) {

        List<String> notes = dao.getUnreadNotifications(employeeId);

        if (notes.isEmpty()) {
        	logger.info("No new notifications.");
        } else {
        	logger.info("\n--- Notifications ---");
            for (String n : notes) {
            	logger.info("• " + n);
            }
            dao.markAsRead(employeeId);
        }
    }
}
