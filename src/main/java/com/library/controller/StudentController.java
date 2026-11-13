package com.library.controller;

import com.library.model.Student;
import com.library.service.StudentService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", service.all());
        return "students";
    }

    @GetMapping("/new")
    public String addForm(Model model) {
        Student s = new Student();
        s.setStatus("ACTIVE");
        model.addAttribute("student", s);
        return "student-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        Student s = service.find(id);
        if (s == null) return "redirect:/students";
        model.addAttribute("student", s);
        return "student-form";
    }

    @PostMapping("/save")
    public String save(Student student, RedirectAttributes ra) {
        String code = student.getCode() == null ? "" : student.getCode().trim().toUpperCase();
        student.setCode(code);
        boolean isNew = student.getStudentId() == 0;
        try {
            service.save(student);
            ra.addFlashAttribute("flashOk", isNew
                ? "Student added. Login created: username " + code.toLowerCase() + " / password student123"
                : "Student updated.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("flashErr", "A student with this Student ID already exists.");
        }
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("flashOk", "Student deleted.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("flashErr", "This student has borrowing history and cannot be deleted. Edit the student and set the status to Inactive instead.");
        }
        return "redirect:/students";
    }
}