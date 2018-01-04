package next.web;


import next.model.User;

import javax.servlet.http.HttpSession;

/**
 * Created by SeungUk on 2018. 1. 4..
 */
public class UserSessionUtils {
    private static final String USER_SESSION_KEY = "user";

    private static User getUserFromSession(HttpSession session) {
        Object user = session.getAttribute(USER_SESSION_KEY);

        if (user == null) {
            return null;
        }

        return (User) user;
    }

    private static boolean isLogined(HttpSession session) {
        if (getUserFromSession(session) == null) {
            return false;
        }
        return true;
    }


    public static boolean isSameUser(HttpSession session, User user) {
        if (!isLogined(session)) {
            return false;
        }

        if (user == null) {
            return false;
        }

        return user.isSameUser(getUserFromSession(session));
    }
}
