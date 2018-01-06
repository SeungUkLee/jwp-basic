package next.controller;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController implements Controller{
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        log.debug("Profile req: {}", req);
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        log.debug("userId : {} user : {}", userId, user);

        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);

        return "/user/profile.jsp";
    }
}
