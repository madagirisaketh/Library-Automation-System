package com.library.dao;

import com.library.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {
	private final JdbcTemplate jdbcTemplate;

	public UserDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public User login(String username, String password, String role) {
		String sql = "SELECT USER_ID, USERNAME, PASSWORD, ROLE, STUDENT_ID "
				+ "FROM USERS WHERE LOWER(USERNAME)=LOWER(?) AND PASSWORD=? AND ROLE=? AND STATUS='1'";
		List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
			User user = new User();
			user.setUserId(rs.getInt("USER_ID"));
			user.setUsername(rs.getString("USERNAME"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setRole(rs.getString("ROLE"));
			user.setStudentId(rs.getInt("STUDENT_ID"));
			return user;
		}, username, password, role);

		return users.isEmpty() ? null : users.get(0);
	}

	public List<User> all() {
		return jdbcTemplate.query(
				"SELECT u.USER_ID, u.USERNAME, u.ROLE, u.STATUS, "
						+ "s.FIRST_NAME || ' ' || NVL(s.LAST_NAME,'') AS STUDENT_NAME "
						+ "FROM USERS u LEFT JOIN STUDENTS s ON s.STUDENT_ID = u.STUDENT_ID ORDER BY u.USER_ID",
				(rs, rowNum) -> {
					User user = new User();
					user.setUserId(rs.getInt("USER_ID"));
					user.setUsername(rs.getString("USERNAME"));
					user.setRole(rs.getString("ROLE"));
					user.setStatus(rs.getString("STATUS"));
					user.setStudentName(rs.getString("STUDENT_NAME"));
					return user;
				});
	}

	public void createStudentLogin(String code) {
		jdbcTemplate.update("insert into USERS(USER_ID,USERNAME,PASSWORD,ROLE,STATUS,STUDENT_ID) "
				+ "values(USERS_SEQ.nextval, lower(?), 'student123', 'STUDENT', '1', "
				+ "(select STUDENT_ID from STUDENTS where CODE = ?))", code, code);
	}

	public void deleteByStudent(int studentId) {
		jdbcTemplate.update("delete from USERS where STUDENT_ID=?", studentId);
	}
}