package com.library.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class DashboardDAO {
    private final JdbcTemplate jdbc;

    public DashboardDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private int count(String sql, Object... args) {
        Integer n = jdbc.queryForObject(sql, Integer.class, args);
        return n == null ? 0 : n;
    }

    public Map<String, Integer> adminStats() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("titles", count("select count(*) from BOOKS"));
        m.put("categories", count("select count(*) from CATEGORY"));
        m.put("students", count("select count(*) from STUDENTS where STATUS in ('ACTIVE','1')"));
        m.put("issued", count("select count(*) from BORROW where STATUS='BORROWED'"));
        m.put("overdue", count("select count(*) from BORROW where STATUS='BORROWED' and DUE_DATE < trunc(sysdate)"));
        m.put("returned", count("select count(*) from BORROW where STATUS='RETURNED'"));
        return m;
    }

    public Map<String, Integer> studentStats(int studentId) {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("issued", count("select count(*) from BORROW where STUDENT_ID=? and STATUS='BORROWED'", studentId));
        m.put("overdue", count("select count(*) from BORROW where STUDENT_ID=? and STATUS='BORROWED' and DUE_DATE < trunc(sysdate)", studentId));
        m.put("available", count("select count(*) from BOOKS where STATUS='ACTIVE' and AVAILABLE_COPIES > 0"));
        return m;
    }
}