package com.dhatchina.dhatchinamart.controller;

import com.dhatchina.dhatchinamart.dto.AdminStats;
import com.dhatchina.dhatchinamart.service.AdminService;
import com.dhatchina.dhatchinamart.util.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(AdminServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdminService adminService = ServiceRegistry.getAdminService();
            AdminStats stats = adminService.getDashboardStats();
            request.setAttribute("stats", stats);
            request.getRequestDispatcher("/WEB-INF/jsp/admin-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Failed to load admin dashboard", e);
            request.setAttribute("error", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
