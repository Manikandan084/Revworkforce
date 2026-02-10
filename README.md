# 🏢 RevWorkforce – Console-Based HR Management System

RevWorkforce is a **console-based Human Resource Management (HRM) system** developed using **Core Java, JDBC, MySQL**, and **JUnit 5**.  
It supports **Employee, Manager, and Admin roles**, enabling streamlined employee management, leave tracking, performance reviews, and administrative reporting.

---

## 🚀 Features

### 👤 Employee
- Login / Logout
- View & edit profile
- Change password
- View leave balance
- Apply for leave
- View Notifications
- Submit performance review
- Add goals for performance review
- View goal status & manager feedback
- View Announcement
  

### 👨‍💼 Manager
- View profile
- View Pending Leave Requests
- Approve / reject leave requests
- Review Submitted Performance Reviews
- Review employee performance
- View and track employee goals
- Update Goal Status
- View Team Members
- View Announcement

### 🛠️ Admin
- Add new employees
- View all employees
- Update employees
- Activate / deactivate employees
- Manage leave policies (quota configuration)
- Manage Holiday Calendar
- Post Announcement
- Generate reports:
  - Total employees
  - Active vs inactive employees
  - Employees by department
  - Leave request summary

---

## 🧱 Project Architecture
RevWorkforce
 - src/
    - com.revworkforce.main → Application entry point
    - com.revworkforce.ui → CLI menus
    - com.revworkforce.service → Business logic
    - com.revworkforce.dao → JDBC data access
    - com.revworkforce.model → Entity classes
    - com.revworkforce.util → DB & logging utilities
 - test/
    - com.revworkforce.test → JUnit 5 test cases
 - logs/
    - revworkforce.log → Log4j2 logs
 - README.md


---

## 🗄️ Database Design (MySQL)

### employees
 - employee_id (PK)
 - first_name
 - last_name
 - email
 - phone
 - address
 - dob
 - department_id
 - designation
 - joining_date
 - role
 - manager_id
 - password
 - is_active
 - created_at
 - updated_at


### leave_types
 - leave_type_id (PK)
 - leave_name
 - description
 - max_per_year
 - is_active


### leave_requests
 - leave_request_id (PK)
 - employee_id (FK)
 - leave_type_id (FK)
 - start_date
 - end_date
 - reason
 - status
 - applied_on
 - reviewed_by
 - review_comments
 - reviewed_on


### leave_balance
 - balance_id
 - employee_id
 - leave_type_id
 - total_leaves
 - used_leaves
 - remaining_leaves


### performance_reviews
 - review_id (PK)
 - employee_id
 - review_year
 - self_assessment
 - achievements
 - areas_of_improvement
 - self_rating
 - manager_rating
 - manager_feedback
 - status
 - reviewed_by
 - created_at
 - reviewed_at


### goals
 - goal_id (PK)
 - review_id (FK)
 - goal_description
 - deadline
 - priority
 - success_metrics
 - status

### announcements
 - announcement_id
 - title
 - message
 - created_by
 - created_at

### departments
 - department_id
 - department_name
 - description
 - is_active
 - created_at
 - updated_at

### goal_progress
 - progress_id
 - goal_id
 - progress_percentage
 - comments
 - updated_on

### holidays
 - holiday_id
 - holiday_name
 - holiday_date
 - is_optional


### notifications
 - notification_id
 - employee_id
 - title
 - message
 - type
 - is_read
 - created_at
---

## 🧪 Testing (JUnit 5)

 - JUnit 5 (Jupiter) is used to test **service-layer logic** with real database integration.

### ✔ Test Coverage
- Authentication (login success & failure)
- Fetch all employees
- Update employee active status
- Update leave policy quota

---

## 📝 Logging

- Log4j2 is used for application logging
- Logs include:
  - Application startup
  - Login success / failure
  - Leave approval / rejection
  - Performance review submission
  - Leave policy updates

Log file:
 - logs/revworkforce.log


---

## 🛠️ Tech Stack

- Java (Core Java, JDBC)
- MySQL
- JUnit 5 (Jupiter)
- Log4j2
- Eclipse IDE

---

## ▶️ How to Run

1. Clone the repository
2. Import project into Eclipse
3. Configure MySQL DB in `DBConnection.java`
4. Run `RevWorkforceApp.java`
5. Login using valid credentials

---

## 🎯 Learning Outcomes

- Layered architecture (UI → Service → DAO → DB)
- JDBC with prepared statements
- Role-based access control
- SQL joins & aggregation
- Logging with Log4j2
- JUnit 5 integration testing
- Real-world backend debugging

---

## 📌 Future Enhancements

- Web-based UI (Spring Boot + React)
- Password hashing
- Email notifications
- REST API support

---

## 👨‍🎓 Author

**Manikandan M**
