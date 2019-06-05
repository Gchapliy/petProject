package com.epamTranings.bankSystem.filter;

import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.AppUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebFilter(filterName = "cookieFilter", urlPatterns = {"/*"})
public class CookieFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        UserAccount userInSession = AppUtils.getLoginedUser(session);
        //Check is user in session
        if (userInSession != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // Connection was created in JDBCFilter.
        Connection conn = AppUtils.getStoredConnection(servletRequest);

        // Flag check cookie
        String checked = (String) session.getAttribute("COOKIE_CHECKED");
        if (checked == null && conn != null) {
            String userEmail = AppUtils.getUserEmailInCookie(req);
            UserAccount user = UserDAO.findUserByEmail(conn, userEmail);
            AppUtils.storeLoginedUser(session, user);
            // Mark checked Cookies.
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
