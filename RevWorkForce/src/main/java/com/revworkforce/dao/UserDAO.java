package com.revworkforce.dao;

import com.revworkforce.model.Employee;
import com.revworkforce.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private static final Logger logger = LogManager.getLogger(UserDAO.class);

    public Employee login(int employeeId, String password) {

        String sql = "SELECT * FROM employee WHERE employee_id = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Employee emp = new Employee();
                emp.setEmployeeId(rs.getInt("employee_id"));
                emp.setName(rs.getString("name"));
                emp.setEmail(rs.getString("email"));
                emp.setRole(rs.getString("role"));
                emp.setManagerId(rs.getObject("manager_id", Integer.class));
                return emp;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
