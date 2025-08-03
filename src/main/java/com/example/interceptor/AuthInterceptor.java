package com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // ✅ Allow admin session using hardcoded values
        String uri = request.getRequestURI();
        if (uri.startsWith("/admin") || uri.contains("admin-dashboard")) {
            String adminLoggedIn = (String) request.getSession().getAttribute("admin");
            if ("true".equals(adminLoggedIn)) {
                return true;
            } else {
                response.sendRedirect("/admin-login");
                return false;
            }
        }

        // ✅ Regular user check
        if (session != null && session.getAttribute("user") != null) {
            return true;
        }

        // Default: redirect to user login
        response.sendRedirect("/login");
        return false;
    }
}
