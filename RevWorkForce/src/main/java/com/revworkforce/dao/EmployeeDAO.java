package com.revworkforce.dao;

import com.revworkforce.model.Employee;
import com.revworkforce.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
	private static final Logger logger = LogManager.getLogger( EmployeeDAO.class);


    // LOGIN METHOD (THIS WAS MISSING)
	public Employee login(String loginId, String password) {

	    String sql = "SELECT * FROM employee WHERE login_id = ? AND password = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, loginId);
	        ps.setString(2, password);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            Employee emp = new Employee();
	            emp.setEmployeeId(rs.getInt("employee_id"));
	            emp.setLoginId(rs.getString("login_id"));
	            emp.setName(rs.getString("name"));
	            emp.setEmail(rs.getString("email"));
	            emp.setDepartment(rs.getString("department"));
	            emp.setRole(rs.getString("role").trim());

	            // FIXED PART
	            int managerId = rs.getInt("manager_id");
	            if (rs.wasNull()) {
	                emp.setManagerId(null);
	            } else {
	                emp.setManagerId(managerId);
	            }

	            return emp;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	// AUTO GENERATE LOGIN ID
	public String generateLoginId(String role) {

	    String prefix = "";

	    switch (role) {
	        case "ADMIN":
	            prefix = "AD";
	            break;
	        case "MANAGER":
	            prefix = "MGR";
	            break;
	        case "EMPLOYEE":
	            prefix = "EMP";
	            break;
	        default:
	            throw new RuntimeException("Invalid Role");
	    }

	    String sql = "SELECT login_id FROM employee " +
	                 "WHERE login_id LIKE ? " +
	                 "ORDER BY login_id DESC LIMIT 1";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, prefix + "%");

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String lastId = rs.getString("login_id"); 
	            int num = Integer.parseInt(lastId.substring(prefix.length()));
	            num++;
	            return prefix + String.format("%02d", num);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // FIRST ENTRY
	    return prefix + "01";
	}


    // ADD EMPLOYEE
    public boolean addEmployee(Employee emp) {

    	String sql = "INSERT INTO employee (login_id, name, email, phone, department, role, join_date, salary, password) "
    	           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

        	ps.setString(1, emp.getLoginId());
        	ps.setString(2, emp.getName());
        	ps.setString(3, emp.getEmail());
        	ps.setString(4, emp.getPhone());
        	ps.setString(5, emp.getDepartment());
        	ps.setString(6, emp.getRole());
        	ps.setDate(7, Date.valueOf(emp.getJoiningDate()));
        	ps.setDouble(8, emp.getSalary());
        	ps.setString(9, emp.getPassword());


            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateLeaveBalance(int empId, int cl, int sl, int pl) {

    	String sql =
    		    "INSERT INTO employee_leave_balance (employee_id, cl, sl, pl) " +
    		    "VALUES (?, ?, ?, ?) " +
    		    "ON DUPLICATE KEY UPDATE " +
    		    "cl = VALUES(cl), sl = VALUES(sl), pl = VALUES(pl)";


        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setInt(2, cl);
            ps.setInt(3, sl);
            ps.setInt(4, pl);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployeeProfile(int employeeId,
            String email,
            String phone,
            String password) {

String sql = "UPDATE employee SET email=?, phone=?, password=? WHERE employee_id=?";

try (Connection conn = DBConnection.getConnection();
PreparedStatement ps = conn.prepareStatement(sql)) {

ps.setString(1, email);
ps.setString(2, phone);
ps.setString(3, password);
ps.setInt(4, employeeId);

return ps.executeUpdate() > 0;

} catch (SQLException e) {
e.printStackTrace();
}

return false;
}






    // 📋 VIEW ALL EMPLOYEES
    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmployeeId(rs.getInt("employee_id"));  // ✅ FIXED
                emp.setName(rs.getString("name"));
                emp.setEmail(rs.getString("email"));
                emp.setDepartment(rs.getString("department"));
                emp.setRole(rs.getString("role"));
                employees.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
    public int[] getLeaveBalance(int empId) {
        int[] balance = new int[3];

        String sql = "SELECT cl, sl, pl FROM employee_leave_balance WHERE employee_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                balance[0] = rs.getInt("cl");
                balance[1] = rs.getInt("sl");
                balance[2] = rs.getInt("pl");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return balance;
    }


}

