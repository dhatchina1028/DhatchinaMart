package com.dhatchina.dhatchinamart.controller;

import com.dhatchina.dhatchinamart.dto.RegisterRequest;
import com.dhatchina.dhatchinamart.exception.AppException;
import com.dhatchina.dhatchinamart.model.User;
import com.dhatchina.dhatchinamart.service.AuthService;
import com.dhatchina.dhatchinamart.util.SessionUtil;
import com.dhatchina.dhatchinamart.util.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/register", "/logout"})
public class AuthServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        if (SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        if ("/login".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/login".equals(path)) {
            handleLogin(request, response);
        } else {
            handleRegister(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            User user = ServiceRegistry.getAuthService().login(email, password);
            HttpSession session = request.getSession(true);
            request.changeSessionId();
            session.setAttribute(SessionUtil.SESSION_USER, user);
            log.info("Session established for user {}", user.getEmail());
            response.sendRedirect(request.getContextPath() + "/");
        } catch (AppException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Login failed unexpectedly", e);
            request.setAttribute("error", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(request.getParameter("name"));
        registerRequest.setEmail(request.getParameter("email"));
        registerRequest.setPassword(request.getParameter("password"));
        registerRequest.setConfirmPassword(request.getParameter("confirmPassword"));
        registerRequest.setRole(request.getParameter("role"));
        try {
            AuthService authService = ServiceRegistry.getAuthService();
            authService.register(registerRequest);
            response.sendRedirect(request.getContextPath() + "/login?registered=1");
        } catch (AppException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("name", registerRequest.getName());
            request.setAttribute("email", registerRequest.getEmail());
            request.setAttribute("role", registerRequest.getRole());
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Registration failed unexpectedly", e);
            request.setAttribute("error", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }
    }
}
