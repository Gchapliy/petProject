package com.myProject.bankSystem.filter;

import com.myProject.bankSystem.dao.UserDAO;
import com.myProject.bankSystem.bean.userAccount.UserAccount;
import com.myProject.bankSystem.utils.AppUtils;

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

    /**
     * In case, the user logined and  remembered information in previous access (for example the day before).
     * And now the user return, this Filter will check the Cookie information stored by the browser and automatic Login
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
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
