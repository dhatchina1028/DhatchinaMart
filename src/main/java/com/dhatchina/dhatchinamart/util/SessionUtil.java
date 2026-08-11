package com.dhatchina.dhatchinamart.util;

import com.dhatchina.dhatchinamart.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class SessionUtil {

    public static final String SESSION_USER = "user";

    private SessionUtil() {
    }

    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (User) session.getAttribute(SESSION_USER);
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getUser(request) != null;
    }
}
