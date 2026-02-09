package com.revworkforce.test;

import static org.junit.jupiter.api.Assertions.*;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;

import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeeFetchTest {

    @Test
    void testGetAllEmployees() {

        EmployeeDAO dao = new EmployeeDAO();
        List<Employee> list = dao.getAllEmployees();

        // check list is not null
        assertNotNull(list);

        // check list has records
        assertTrue(list.size() > 0);

        // optional print (not required)
        for (Employee e : list) {
            System.out.println(
                    e.getEmployeeId() + " | " +
                    e.getName() + " | " +
                    e.getEmail() + " | " +
                    e.getDepartment()
            );
        }
    }
}
