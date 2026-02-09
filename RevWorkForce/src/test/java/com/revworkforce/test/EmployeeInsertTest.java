package com.revworkforce.test;

import static org.junit.jupiter.api.Assertions.*;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeeInsertTest {

	
	void testInsertEmployee() {

	    Employee emp = new Employee();
	    emp.setName("TestUser");
	    emp.setEmail("testuser@gmail.com");
	    emp.setPhone("9999999999");
	    emp.setDepartment("IT");
	    emp.setDesignation("Developer");
	    emp.setJoiningDate(LocalDate.now());
	    emp.setSalary(40000);

	    // 🔴 MISSING FIELDS (IMPORTANT)
	    emp.setRole("EMPLOYEE");
	    emp.setPassword("test123");

	    EmployeeDAO dao = new EmployeeDAO();
	    boolean result = dao.addEmployee(emp);

	    assertTrue(result, "Employee insert failed");
	}

}
