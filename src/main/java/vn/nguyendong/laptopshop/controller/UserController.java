package vn.nguyendong.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.nguyendong.laptopshop.service.UserService;

@Controller
public class UserController {
    // dependency injection (DI)-> Không nên dùng @Autowired vì ko tốt cho testing
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage() {
        String test = userService.handleHello();
        // return "dong.html";
        return "hello";
    }
}

// @RestController
// public class UserController {

// // dependency injection (DI)-> Không nên dùng @Autowired vì ko tốt cho
// testing
// private UserService userService;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("/")
// public String getHomePage() {
// return this.userService.handleHello();
// }
// }
