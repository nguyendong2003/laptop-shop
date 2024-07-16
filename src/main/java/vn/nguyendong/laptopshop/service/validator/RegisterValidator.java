package vn.nguyendong.laptopshop.service.validator;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.nguyendong.laptopshop.domain.dto.RegisterDTO;
import vn.nguyendong.laptopshop.service.UserService;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {

    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true;

        // Check if password fields match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Password and Confirm password must match") // khi lỗi xảy ra
                                                                                                     // thì hiện message
                    .addPropertyNode("confirmPassword") // trường thông tin báo lỗi là 'confirmPassword'
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Additional validations can be added here

        // Check if email already exists
        if (this.userService.checkEmailExist(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("Email already exists") // khi lỗi xảy ra thì hiện message
                    .addPropertyNode("email") // trường thông tin báo lỗi là 'email'
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
