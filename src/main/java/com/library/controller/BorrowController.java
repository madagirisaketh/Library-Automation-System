package com.library.controller;

import com.library.model.User;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class BorrowController {
    private final StudentService studentService;
    private final BookService bookService;
    private final BorrowService borrowService;

    public BorrowController(StudentService studentService, BookService bookService, BorrowService borrowService) {
        this.studentService = studentService;
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    @GetMapping("/borrow")
    public String borrow(@RequestParam(required = false) Integer bookId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            model.addAttribute("students", studentService.active());
            model.addAttribute("books", bookService.available());
            model.addAttribute("today", LocalDate.now().toString());
            model.addAttribute("defaultDue", LocalDate.now().plusDays(14).toString());
            model.addAttribute("selectedBookId", bookId == null ? 0 : bookId);
            model.addAttribute("borrowings", borrowService.all());
        } else {
            model.addAttribute("borrowings", borrowService.byStudent(user.getStudentId()));
        }
        return "borrow";
    }

    @PostMapping("/borrow/issue")
    public String issue(@RequestParam int studentId, @RequestParam int bookId,
                        @RequestParam String dueDate, RedirectAttributes ra) {
        String error = borrowService.issue(studentId, bookId, dueDate);
        if (error == null) ra.addFlashAttribute("flashOk", "Book issued successfully.");
        else ra.addFlashAttribute("flashErr", error);
        return "redirect:/borrow";
    }

    @PostMapping("/borrow/return/{id}")
    public String giveBack(@PathVariable int id, RedirectAttributes ra) {
        if (borrowService.giveBack(id)) ra.addFlashAttribute("flashOk", "Book marked as returned.");
        else ra.addFlashAttribute("flashErr", "This book was already returned.");
        return "redirect:/borrow";
    }
}