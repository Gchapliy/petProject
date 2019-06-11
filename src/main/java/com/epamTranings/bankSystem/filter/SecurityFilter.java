package com.epamTranings.bankSystem.filter;

import com.epamTranings.bankSystem.entity.userAccount.Role;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.AppUtils;
import com.epamTranings.bankSystem.utils.SecurityUtils;
import com.epamTranings.bankSystem.utils.UserRoleRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String servletPath = request.getServletPath();

        UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession());

        if(servletPath.equals("/login")){
            filterChain.doFilter(request, response);
            return;
        }
        HttpServletRequest wrapRequest = request;

        if (loginedUser != null) {
            // User Email
            String userEmail = loginedUser.getUserAccountEmail();

            // Roles
            Role role = loginedUser.getUserAccountRole();

            // Wrap old request by a new Request with userName and Role information.
            wrapRequest = new UserRoleRequestWrapper(userEmail, role, request);
        }

        // Pages must be signed in.
        if (SecurityUtils.isSecurityPage(request)) {

            // If the user is not logged in,
            // Redirect to the login page.
            if (loginedUser == null) {

                String requestUri = request.getRequestURI();

                // Store the current page to redirect to after successful login.
                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);

                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }

            // Check if the user has a valid role?
            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {

                    request.getSession().invalidate();
                    AppUtils.deleteUserCookie(response);

                request.getRequestDispatcher("templates/accessDeniedView.jsp").forward(request, response);
                return;
            }
        }

        filterChain.doFilter(wrapRequest, response);
    }

    @Override
    public void destroy() {

    }
}
