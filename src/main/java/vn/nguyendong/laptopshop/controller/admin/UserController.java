package vn.nguyendong.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.nguyendong.laptopshop.domain.User;
import vn.nguyendong.laptopshop.service.UploadService;
import vn.nguyendong.laptopshop.service.UserService;

@Controller
public class UserController {
    // dependency injection (DI)-> Không nên dùng @Autowired vì ko tốt cho testing
    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/{userId}")
    public String getUserDetailPage(Model model, @PathVariable long userId) {
        User user = this.userService.getUserById(userId);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUserPage(Model model,
            @Valid @ModelAttribute("newUser") User user,
            BindingResult bindingResult, // phải đặt dòng này ngay sau @Valid
            @RequestParam("avatarUploadFile") MultipartFile file) {

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>>>>>>>>>>>>>>" + error.getField() + " - " +
                    error.getDefaultMessage());
        }

        if (bindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String avatarName = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        user.setAvatar(avatarName);
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));

        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }

    @PostMapping("admin/user/update")
    public String postUpdateUser(Model model,
            @Valid @ModelAttribute("newUser") User user,
            BindingResult bindingResult,
            @RequestParam("avatarUploadFile") MultipartFile file) {

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>>>>>>>>>>>>>>" + error.getField() + " - " +
                    error.getDefaultMessage());
        }

        if (bindingResult.hasErrors()) {
            User currentUser = this.userService.getUserById(user.getId());
            model.addAttribute("originImage", currentUser.getAvatar());
            return "admin/user/update";
        }

        User currentUser = this.userService.getUserById(user.getId());
        if (currentUser != null) {
            if (!file.isEmpty()) {
                String avatarName = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(avatarName);
            }
            currentUser.setPhone(user.getPhone());
            currentUser.setFullName(user.getFullName());
            currentUser.setAddress(user.getAddress());
            currentUser.setRole(this.userService.getRoleByName(user.getRole().getName()));
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User user) {
        this.userService.handleDeleteUser(user.getId());
        return "redirect:/admin/user";
    }
}
