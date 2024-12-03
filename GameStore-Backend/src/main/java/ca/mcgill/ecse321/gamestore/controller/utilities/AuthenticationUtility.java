package ca.mcgill.ecse321.gamestore.controller.utilities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthenticationUtility {

    // HTTP Status Codes
    public static int UNAUTHORIZED = 401;
    public static int FORBIDDEN = 403;

    // Role enum
    public enum Role {
        CUSTOMER, STAFF, MANAGER
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("accountId") != null;
    }

    public static int getAccountId(HttpServletRequest request) throws Exception {
        if (!isLoggedIn(request)) {
            throw new Exception("Not logged in");
        }
        return Integer.parseInt(request.getSession(false).getAttribute("accountId").toString());
    }

    public static boolean isStaff(HttpServletRequest request) throws Exception {
        if (!isLoggedIn(request)) {
            throw new Exception("Not logged in");
        }
        return request.getSession(false).getAttribute("role") == Role.STAFF ||
                request.getSession(false).getAttribute("role") == Role.MANAGER;
    }

    public static boolean isManager(HttpServletRequest request) throws Exception {
        if (!isLoggedIn(request)) {
            throw new Exception("Not logged in");
        }
        return request.getSession(false).getAttribute("role") == Role.MANAGER;
    }

}
