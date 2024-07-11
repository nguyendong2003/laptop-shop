package vn.nguyendong.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.nguyendong.laptopshop.service.UserService;

@Controller
public class UserController {
    // dependency injection (DI)-> Không nên dùng @Autowired vì ko tốt cho testing
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = userService.handleHello();
        model.addAttribute("m10", test);
        model.addAttribute("neymar", "Hello from UserController!");
        return "hello";
    }
}
