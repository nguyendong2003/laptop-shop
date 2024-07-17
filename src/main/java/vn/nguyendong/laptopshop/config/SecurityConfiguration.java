package vn.nguyendong.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.DispatcherType;
import vn.nguyendong.laptopshop.service.CustomerUserDetailsService;
import vn.nguyendong.laptopshop.service.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    // dùng BCryptPasswordEncoder để mã hóa mật khẩu người dùng
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Ghi đè lại UserDetailsService mặc định của Spring Security bằng class
    // CustomerUserDetailsService
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomerUserDetailsService(userService);
    }

    // fix infinity loop error
    @Bean
    public DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        // // Hiện 'User not found' khi không tìm thấy người dùng.
        // // Mặc định là true => hiện: 'Bad credentials'
        // authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    // Redirect after login (USER: /, ADMIN: /admin)
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }

    //
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // version 6: Lambda Syntax
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Permit FORWARD, INCLUDE when using Spring MVC (default denied)
                        .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                DispatcherType.INCLUDE)
                        .permitAll()

                        // Permit all requests to the following paths
                        .requestMatchers("/", "/login", "/product/**", "/client/**", "/css/**", "/js/**",
                                "/images/**")
                        .permitAll()

                        // Only admin can access to the following paths
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // The remaining requests must be authenticated
                        .anyRequest().authenticated())

                // Configure login page
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .successHandler(customSuccessHandler())
                        .permitAll())
                // User does not have permission to access the admin's page
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));
        return http.build();
    }
}
