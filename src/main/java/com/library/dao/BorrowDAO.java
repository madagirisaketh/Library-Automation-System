package com.library.dao;

import com.library.model.Borrow;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class BorrowDAO {
	private final JdbcTemplate jdbc;

	public BorrowDAO(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	private static final String SELECT = "select r.BORROW_ID, r.STUDENT_ID, r.BOOK_ID, r.STATUS, "
			+ "to_char(r.BORROW_DATE,'DD Mon YYYY') as BORROW_DATE, "
			+ "to_char(r.DUE_DATE,'DD Mon YYYY') as DUE_DATE, "
			+ "to_char(r.RETURN_DATE,'DD Mon YYYY') as RETURN_DATE, "
			+ "s.CODE as STUDENT_CODE, s.FIRST_NAME || ' ' || NVL(s.LAST_NAME,'') as STUDENT_NAME, "
			+ "b.TITLE as BOOK_TITLE, b.ISBN, " + "case when r.STATUS='BORROWED' and r.DUE_DATE < trunc(sysdate) "
			+ "     then trunc(sysdate) - trunc(r.DUE_DATE) else 0 end as DAYS_OVERDUE "
			+ "from BORROW r join STUDENTS s on s.STUDENT_ID = r.STUDENT_ID "
			+ "join BOOKS b on b.BOOK_ID = r.BOOK_ID ";

	private final RowMapper<Borrow> mapper = (r, n) -> {
		Borrow x = new Borrow();
		x.setBorrowId(r.getInt("BORROW_ID"));
		x.setStudentId(r.getInt("STUDENT_ID"));
		x.setBookId(r.getInt("BOOK_ID"));
		x.setStatus(r.getString("STATUS"));
		x.setBorrowDate(r.getString("BORROW_DATE"));
		x.setDueDate(r.getString("DUE_DATE"));
		x.setReturnDate(r.getString("RETURN_DATE"));
		x.setStudentCode(r.getString("STUDENT_CODE"));
		x.setStudentName(r.getString("STUDENT_NAME"));
		x.setBookTitle(r.getString("BOOK_TITLE"));
		x.setIsbn(r.getString("ISBN"));
		x.setDaysOverdue(r.getInt("DAYS_OVERDUE"));
		return x;
	};

	public List<Borrow> all() {
		return jdbc.query(
				SELECT + "order by case when r.STATUS='BORROWED' then 0 else 1 end, r.DUE_DATE, r.BORROW_ID desc",
				mapper);
	}

	public List<Borrow> byStudent(int studentId) {
		return jdbc.query(
				SELECT + "where r.STUDENT_ID = ? order by case when r.STATUS='BORROWED' then 0 else 1 end, r.DUE_DATE",
				mapper, studentId);
	}

	public int activeCountForStudent(int studentId) {
		Integer n = jdbc.queryForObject("select count(*) from BORROW where STUDENT_ID=? and STATUS='BORROWED'",
				Integer.class, studentId);
		return n == null ? 0 : n;
	}

	public boolean hasActive(int studentId, int bookId) {
		Integer n = jdbc.queryForObject(
				"select count(*) from BORROW where STUDENT_ID=? and BOOK_ID=? and STATUS='BORROWED'", Integer.class,
				studentId, bookId);
		return n != null && n > 0;
	}

	public void insert(int studentId, int bookId, Date dueDate) {
		jdbc.update("insert into BORROW(BORROW_ID,STUDENT_ID,BOOK_ID,BORROW_DATE,DUE_DATE,STATUS) "
				+ "values(BORROW_SEQ.nextval,?,?,trunc(sysdate),?,'BORROWED')", studentId, bookId, dueDate);
	}

	public List<Integer> activeBookId(int borrowId) {
		return jdbc.queryForList("select BOOK_ID from BORROW where BORROW_ID=? and STATUS='BORROWED'", Integer.class,
				borrowId);
	}

	public int markReturned(int borrowId) {
		return jdbc.update(
				"update BORROW set STATUS='RETURNED', RETURN_DATE=trunc(sysdate) where BORROW_ID=? and STATUS='BORROWED'",
				borrowId);
	}
}