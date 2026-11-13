package com.library.dao;

import com.library.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAO {
    private final JdbcTemplate j;

    public StudentDAO(JdbcTemplate j) {
        this.j = j;
    }

    private static final String SELECT =
        "select STUDENT_ID,CODE,FIRST_NAME,LAST_NAME,EMAIL,CONTACT,DEPARTMENT,COURSE,STATUS from STUDENTS ";

    private final RowMapper<Student> mapper = (r, n) -> {
        Student s = new Student();
        s.setStudentId(r.getInt("STUDENT_ID"));
        s.setCode(r.getString("CODE"));
        s.setFirstName(r.getString("FIRST_NAME"));
        s.setLastName(r.getString("LAST_NAME"));
        s.setEmail(r.getString("EMAIL"));
        s.setContact(r.getString("CONTACT"));
        s.setDepartment(r.getString("DEPARTMENT"));
        s.setCourse(r.getString("COURSE"));
        s.setStatus(r.getString("STATUS"));
        return s;
    };

    public List<Student> all() {
        return j.query(SELECT + "order by STUDENT_ID", mapper);
    }

    public List<Student> active() {
        return j.query(SELECT + "where STATUS in ('ACTIVE','1') order by FIRST_NAME", mapper);
    }

    public Student find(int id) {
        List<Student> list = j.query(SELECT + "where STUDENT_ID=?", mapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public void insert(Student s) {
        j.update("insert into STUDENTS(STUDENT_ID,CODE,FIRST_NAME,LAST_NAME,EMAIL,CONTACT,DEPARTMENT,COURSE,STATUS) " +
                 "values(STUDENTS_SEQ.nextval,?,?,?,?,?,?,?,?)",
                 s.getCode(), s.getFirstName().trim(), s.getLastName(), s.getEmail(),
                 s.getContact(), s.getDepartment(), s.getCourse(), s.getStatus());
    }

    public void update(Student s) {
        j.update("update STUDENTS set CODE=?, FIRST_NAME=?, LAST_NAME=?, EMAIL=?, CONTACT=?, DEPARTMENT=?, COURSE=?, STATUS=? where STUDENT_ID=?",
                 s.getCode(), s.getFirstName().trim(), s.getLastName(), s.getEmail(), s.getContact(),
                 s.getDepartment(), s.getCourse(), s.getStatus(), s.getStudentId());
    }

    public void delete(int id) {
        j.update("delete from STUDENTS where STUDENT_ID=?", id);
    }
}