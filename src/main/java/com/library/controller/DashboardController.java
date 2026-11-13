package com.library.controller;

import com.library.dao.DashboardDAO;
import com.library.model.Borrow;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.BorrowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    private final DashboardDAO dashboardDAO;
    private final BookService bookService;
    private final BorrowService borrowService;

    public DashboardController(DashboardDAO dashboardDAO, BookService bookService, BorrowService borrowService) {
        this.dashboardDAO = dashboardDAO;
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    private static <T> List<T> limit(List<T> list, int max) {
        return list.size() > max ? list.subList(0, max) : list;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";
        if ("ADMIN".equalsIgnoreCase(user.getRole())) return "redirect:/admin/dashboard";
        if ("STUDENT".equalsIgnoreCase(user.getRole())) return "redirect:/student/dashboard";
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) return "redirect:/";

        List<Borrow> active = new ArrayList<>();
        for (Borrow b : borrowService.all()) {
            if ("BORROWED".equals(b.getStatus())) active.add(b);
        }
        model.addAttribute("stats", dashboardDAO.adminStats());
        model.addAttribute("recentBooks", limit(bookService.all(), 6));
        model.addAttribute("activeLoans", limit(active, 6));
        return "admin-dashboard";
    }

    @GetMapping("/student/dashboard")
    public String studentDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"STUDENT".equalsIgnoreCase(user.getRole())) return "redirect:/";

        List<Borrow> mine = new ArrayList<>();
        for (Borrow b : borrowService.byStudent(user.getStudentId())) {
            if ("BORROWED".equals(b.getStatus())) mine.add(b);
        }
        model.addAttribute("stats", dashboardDAO.studentStats(user.getStudentId()));
        model.addAttribute("myLoans", mine);
        model.addAttribute("arrivals", limit(bookService.available(), 8));
        return "student-dashboard";
    }
}