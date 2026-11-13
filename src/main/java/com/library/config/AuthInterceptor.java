package com.library.config;

import com.library.model.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String[] ADMIN_ONLY = {
        "/admin/dashboard", "/categories", "/students", "/users",
        "/books/new", "/books/edit", "/books/save", "/books/delete",
        "/borrow/issue", "/borrow/return"
    };

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        HttpSession session = req.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        String ctx = req.getContextPath();

        if (user == null) {
            res.sendRedirect(ctx + "/");
            return false;
        }

        boolean admin = "ADMIN".equalsIgnoreCase(user.getRole());
        if (!admin) {
            String path = req.getServletPath();
            for (String prefix : ADMIN_ONLY) {
                if (path.startsWith(prefix)) {
                    res.sendRedirect(ctx + "/dashboard");
                    return false;
                }
            }
        }
        return true;
    }
}