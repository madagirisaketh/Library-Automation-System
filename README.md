# Library Automation System

A web-based Library Management System developed using Java, Spring MVC, JSP, JDBC, and Oracle Database. The application simplifies day-to-day library operations by providing dedicated interfaces for administrators and students to manage books, student records, categories, and borrowing transactions.

## Project Overview

The Library Automation System is designed to digitize library operations and reduce manual record maintenance.

It provides role-based access for administrators and students. Administrators can manage library resources, maintain student information, issue books, and process returns. Students can access their dashboards and view their borrowing history.

The application follows a layered architecture to separate presentation, request handling, business logic, and database operations.

## Screenshots

| Login | Admin Dashboard |
|---|---|
| ![Login](docs/screenshots/login.png) | ![Dashboard](docs/screenshots/dashboard.png) |

| Books | Issue and Return |
|---|---|
| ![Books](docs/screenshots/books.png) | ![Borrow](docs/screenshots/borrow.png) |

## Key Features

### 1. Role-Based Login
- Separate login interfaces for administrators and students.
- Session-based user authentication.
- Role-based access restrictions for administrative operations.
- Dashboard redirection based on the logged-in user's role.

### 2. Book Management
- Add new books to the library.
- View available books and book details.
- Update existing book information.
- Delete books when permitted.
- Maintain total and available book copies.
- Organize books using categories.
- Search books by title, author or ISBN, and filter by category and availability.
- Book cover images load automatically from the ISBN.

### 3. Student Management
- Register and maintain student records.
- View student information.
- Update student details.
- Delete student records where permitted.
- A login is created automatically for every new student.

### 4. Category Management
- Create book categories.
- View categories with the number of books in each.
- Delete categories that have no books.

### 5. Book Issue and Return
- Issue books to registered students.
- Select available books and assign due dates.
- Automatically update available book quantities.
- Process book returns.
- Maintain borrowing transaction history.
- Prevent students from borrowing more than three active books.
- Prevent duplicate active borrowing of the same book by a student.
- Validate due dates and book availability.

### 6. Student Dashboard
- Access a dedicated student dashboard.
- View personal borrowing records.
- Track borrowed and returned books.
- Identify overdue books.

### 7. Admin Dashboard
- Access administrative library operations.
- View library management information.
- Navigate to book, student, category, and borrowing modules.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Backend programming |
| Spring MVC 5.3 | Web application framework |
| Spring JDBC | Database connectivity and queries |
| JSP | Dynamic web pages |
| JSTL | JSP page rendering |
| Oracle Database | Relational data storage |
| JDBC | Database communication |
| Maven | Dependency and build management |
| HTML5 | Page structure |
| CSS3 | Application styling |
| JavaScript | Client-side interactions |
| Apache Tomcat 9 | Application deployment |

## Application Architecture

The application follows a layered architecture.

```text
Presentation Layer
       |
       v
JSP / HTML / CSS / JavaScript
       |
       v
Controller Layer
       |
       v
Service Layer
       |
       v
DAO Layer
       |
       v
Oracle Database
```

### Package Structure

```text
src/main/java/com/library/
│
├── config/
│   ├── AppConfig.java
│   ├── AuthInterceptor.java
│   ├── WebAppInitializer.java
│   └── WebConfig.java
│
├── controller/
│   ├── BookController.java
│   ├── BorrowController.java
│   ├── CategoryController.java
│   ├── DashboardController.java
│   ├── LoginController.java
│   ├── StudentController.java
│   └── UserController.java
│
├── dao/
│   ├── BookDAO.java
│   ├── BorrowDAO.java
│   ├── CategoryDAO.java
│   ├── DashboardDAO.java
│   ├── StudentDAO.java
│   └── UserDAO.java
│
├── model/
│   ├── Book.java
│   ├── Borrow.java
│   ├── Category.java
│   ├── Student.java
│   └── User.java
│
└── service/
    ├── BookService.java
    ├── BorrowService.java
    ├── CategoryService.java
    ├── StudentService.java
    └── UserService.java
```

### Web Application Structure

```text
src/main/webapp/
│
├── WEB-INF/
│   └── views/
│       ├── admin-dashboard.jsp
│       ├── student-dashboard.jsp
│       ├── books.jsp
│       ├── book-form.jsp
│       ├── students.jsp
│       ├── student-form.jsp
│       ├── categories.jsp
│       ├── borrow.jsp
│       ├── login.jsp
│       ├── users.jsp
│       └── includes/
│
└── resources/
    ├── css/
    │   └── style.css
    └── js/
        └── app.js
```

## Prerequisites

Install the following software before running the application:

- Java Development Kit (JDK) 17
- Apache Maven
- Oracle Database (XE or a compatible Oracle installation)
- Apache Tomcat 9
- Eclipse IDE for Enterprise Java and Web Developers (recommended)

## Installation and Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/madagirisaketh/Library-Automation-System.git
```

### Step 2: Open the Project

Import the project into Eclipse as an existing Maven project.

Select:

File → Import → Maven → Existing Maven Projects

Choose the cloned project directory and click Finish.

### Step 3: Configure Oracle Database Connection

Update the database connection settings in:

`src/main/java/com/library/config/AppConfig.java`

Configure the following connection details according to your local environment:

- Oracle JDBC driver
- Database URL
- Database username
- Database password

**Security:** Replace the placeholder password in `AppConfig.java` with your own Oracle database password. Do not commit your actual password to a public repository.

### Step 4: Create the Database

Log in to Oracle as the application user and run `database/schema.sql`. It creates the tables, sequences and trigger, and loads sample books, students and borrowing records.

The database setup includes:

- Eight sample students with codes `STUDENT101` to `STUDENT108`.
- The `TRG_STUDENT_LOGIN` trigger for automatically creating student login accounts.
- An administrator login account.
- Sample book and borrowing records.

### Step 5: Build the Application

Open a terminal in the project directory and execute:

```bash
mvn clean package
```

This generates the deployable WAR file inside the `target/` directory.

### Step 6: Deploy on Apache Tomcat

1. Configure Apache Tomcat 9 in Eclipse.
2. Add the Library Automation System project to the Tomcat server.
3. Start the server.
4. Open the application in your browser.

```text
http://localhost:8086/LibraryAutomationSystemNew/
```

### Sample Logins

| Role | Username | Password |
|---|---|---|
| Admin | admin | admin123 |
| Student | student101 | student123 |

The sample student accounts are created automatically when the database initialization script inserts the student records.

## Business Rules

The application implements the following borrowing rules:

- A student can have a maximum of three active book borrowings.
- A student cannot borrow the same book again while an earlier borrowing is active.
- Books can only be issued when available copies exist.
- Due dates cannot be earlier than the current date.
- Returning a book updates the available copy count.
- Administrative operations are restricted to administrator accounts.

## Development Highlights

Through this project, I worked with:

- Developing a Java-based web application using Spring MVC.
- Implementing the MVC architecture.
- Separating business logic using service classes.
- Implementing database operations using Spring JDBC and JdbcTemplate.
- Integrating an Oracle relational database.
- Implementing session-based authentication and role-based access control.
- Managing book issue and return transactions.
- Using Maven for project dependencies and build management.
- Creating dynamic web pages using JSP and JSTL.

## Future Enhancements

Potential improvements for future versions include:

- Automated email notifications for due dates.
- Fine calculation for overdue books.
- Book reservation functionality.
- Detailed library analytics and reporting.
- Automated database backup and recovery.

## Author

**Madagiri Saketh**

GitHub: https://github.com/madagirisaketh

---

*Developed as a Java Full Stack Development project to demonstrate practical knowledge of Java, Spring MVC, database integration, and web application development.*