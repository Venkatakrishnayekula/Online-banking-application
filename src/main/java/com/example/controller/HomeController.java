package com.example.controller;

import com.example.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "online-banking-frontend/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "online-banking-frontend/login";
    }

    @GetMapping("/dashboard")
    public String userDashboard() {
        return "online-banking-frontend/user-dashboard";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "online-banking-frontend/register";
    }

    @GetMapping("/deposit")
    public String depositPage() {
        return "online-banking-frontend/deposit";
    }

    @GetMapping("/withdraw")
    public String withdrawPage() {
        return "online-banking-frontend/withdraw";
    }

    @GetMapping("/transfer")
    public String transferPage() {
        return "online-banking-frontend/transfer";
    }

    @GetMapping("/transaction-history")
    public String showTransactionHistoryPage() {
        return "online-banking-frontend/transaction-history";
    }

    @GetMapping("/profile")
    public String profileUpdatePage() {
        return "online-banking-frontend/profile-update";
    }

    @GetMapping("/admin-approval")
    public String adminApprovalPage() {
        return "online-banking-frontend/admin-approval";
    }

    @GetMapping("/admin-profile-requests")
    public String adminProfileRequests() {
        return "online-banking-frontend/admin-profile-requests";
    }

    @GetMapping("/admin-login")
    public String showAdminLoginPage() {
        return "online-banking-frontend/admin-login";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboardPage() {
        return "online-banking-frontend/admin-dashboard";
    }

    @PostMapping("/doAdminLogin")
    public String adminLogin(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("admin", "true"); // ✅ store dummy admin session
            return "redirect:/admin-dashboard";
        } else {
            return "redirect:/admin-login?error=true";
        }
    }


    @ModelAttribute("loggedUser")
    public User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }
    @GetMapping("/admin-view-users")
    public String viewAllUsersPage() {
        return "online-banking-frontend/admin-view-users";
    }
    @GetMapping("/admin-transactions")
    public String viewAllTransactionsPage() {
        return "online-banking-frontend/admin-transactions";
    }


}
