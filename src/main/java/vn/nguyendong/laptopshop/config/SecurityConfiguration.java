package vn.nguyendong.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

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

        // Config SpringSessionRememberMe => khi tắt browser và mở lại thì vẫn còn login
        @Bean
        public SpringSessionRememberMeServices rememberMeServices() {
                SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
                // optionally customize
                rememberMeServices.setAlwaysRemember(true);
                return rememberMeServices;
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
                                                .requestMatchers("/", "/login", "/register", "/product/**", "/products",
                                                                "/client/**", "/css/**", "/js/**",
                                                                "/images/**")
                                                .permitAll()

                                                // Only admin can access to the following paths
                                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                                // The remaining requests must be authenticated
                                                .anyRequest().authenticated())

                                // Config Spring session management
                                .sessionManagement((sessionManagement) -> sessionManagement
                                                // Always create a session if it doesn't exist
                                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                                // Handle session expired
                                                .invalidSessionUrl("/logout?expired")
                                                /*
                                                 * Maximum number of sessions (1 tài khoản đăng nhập được tối đa trên
                                                 * bao nhiêu thiết bị
                                                 * => TH1: .maxSessionsPreventsLogin(true) => Nếu 1 tài khoản đã đc đăng
                                                 * nhập trên thiết bị này rồi thì thiết bị khác không đăng nhập được =>
                                                 * hết session mới login được)
                                                 * 
                                                 * => TH2: .maxSessionsPreventsLogin(false) => Nếu 1 tài khoản đã đc
                                                 * đăng nhập và sau đó đăng nhập trên thiết bị khác thì thiết bị cũ sẽ
                                                 * bị logout
                                                 */
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(false))

                                // Config logout
                                .logout(logout -> logout
                                                // delete 'JSESSIONID' cookie when logout
                                                .deleteCookies("JSESSIONID")
                                                // Notifies to server that the session has expired
                                                .invalidateHttpSession(true))

                                // Config Spring session remember me
                                .rememberMe((rememberMe) -> rememberMe
                                                .rememberMeServices(rememberMeServices()))

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
