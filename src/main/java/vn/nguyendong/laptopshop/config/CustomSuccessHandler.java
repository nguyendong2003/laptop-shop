package vn.nguyendong.laptopshop.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.nguyendong.laptopshop.domain.User;
import vn.nguyendong.laptopshop.service.UserService;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {

            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
        clearAuthenticationAttributes(request);
        setDataToSession(request, authentication);
    }

    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/");
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        /*
         * Nếu có session thì lấy session ra, nếu không có thì trả về null
         * 
         * 
         * Nếu không truyền false vào getSession thì mặc định là true, tức là nếu không
         * có session thì tạo mới 1 session
         */
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    protected void setDataToSession(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        // get email (username)
        String email = authentication.getName();
        // get user by email
        User user = userService.getUserByEmail(email);

        if (user != null) {
            session.setAttribute("fullName", user.getFullName());
            session.setAttribute("avatar", user.getAvatar());
            session.setAttribute("id", user.getId());
            session.setAttribute("email", user.getEmail());

            int sum = user.getCart() == null ? 0 : user.getCart().getSum();
            session.setAttribute("sum", sum);
        }

    }
}
