package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.logging.Logger;

@Path("/register-action")
public class RegisterActionController implements PageController {

    @Resource(name = "bean/UserService")
    private UserService userService;

    private Logger logger = Logger.getLogger(RegisterActionController.class.getName());

    @POST
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");

        // 邮箱转换
        String realEmail = URLDecoder.decode(email, "utf-8");

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(realEmail);
        user.setPhoneNumber(phoneNumber);
        logger.info("注册用户信息: " + user);

        // 打印一下数据库中所有用户列表
        Collection<User> userList = userService.getAll();
        if (userList != null && !userList.isEmpty()) {
            System.out.println("打印注册用户信息");
            userList.forEach(System.out::println);
        }

        boolean result = userService.register(user);
        if (result) {
            request.setAttribute("username", user.getName());
        }
        return "index.jsp";
    }

}
