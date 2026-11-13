package com.library.service;

import com.library.dao.StudentDAO;
import com.library.dao.UserDAO;
import com.library.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentDAO studentDAO;
    private final UserDAO userDAO;

    public StudentService(StudentDAO studentDAO, UserDAO userDAO) {
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
    }

    public List<Student> all() { return studentDAO.all(); }
    public List<Student> active() { return studentDAO.active(); }
    public Student find(int id) { return studentDAO.find(id); }

    public void save(Student s) {
        if (s.getStudentId() == 0) studentDAO.insert(s);
        else studentDAO.update(s);
    }

    @Transactional
    public void delete(int id) {
        userDAO.deleteByStudent(id);
        studentDAO.delete(id);
    }
}