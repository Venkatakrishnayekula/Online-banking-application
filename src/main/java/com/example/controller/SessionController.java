package com.example.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.model.User;
import com.example.service.UserService;

@Controller
public class SessionController {

    @Autowired
    private UserService userService;

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session) {
        try {
            User user = userService.login(email, password);
            session.setAttribute("user", user); // ✅ Save user in session

            return user.getRole().equals("ADMIN") ? "redirect:/admin-dashboard" : "redirect:/dashboard";
        } catch (RuntimeException e) {
            return "redirect:/login?error";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
