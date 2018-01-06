package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by SeungUk on 2018. 1. 5..
 */
public interface Controller {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
