package com.library.controller;

import com.library.model.User;
import com.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/login"})
    public String home() {
        return "home";
    }

    @GetMapping("/admin/login")
    public String adminLogin(Model model) {
        model.addAttribute("role", "ADMIN");
        model.addAttribute("title", "Admin Login");
        return "login";
    }

    @GetMapping("/student/login")
    public String studentLogin(Model model) {
        model.addAttribute("role", "STUDENT");
        model.addAttribute("title", "Student Login");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String role,
                        HttpSession session,
                        Model model) {

        String requiredRole = role.toUpperCase();
        User user = userService.login(username, password, requiredRole);

        if (user == null) {
            model.addAttribute("role", requiredRole);
            model.addAttribute("title", requiredRole.equals("ADMIN") ? "Admin Login" : "Student Login");
            model.addAttribute("error", "Invalid username, password or role.");
            return "login";
        }

        session.setAttribute("user", user);
        session.setAttribute("role", user.getRole());

        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
