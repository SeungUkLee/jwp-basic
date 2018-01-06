package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);

        log.debug("Login userId : {}, password: {}, user: {}", userId, password, user);
        if (user == null) {
            return "/user/login.jsp";
        }

        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            log.debug("Login match password excute! session : {}", session);
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        } else {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
    }
}
