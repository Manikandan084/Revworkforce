package com.revworkforce.model;

import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Goal {
	private static final Logger logger = LogManager.getLogger(Goal.class);

    private int goalId;
    private int employeeId;
    private String description;
    private LocalDate deadline;
    private String priority;
    private String status;

    // Getters & Setters
}
