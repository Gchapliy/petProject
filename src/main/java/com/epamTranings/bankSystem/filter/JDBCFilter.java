package com.epamTranings.bankSystem.filter;

import com.epamTranings.bankSystem.dbConnection.ConnectionUtils;
import com.epamTranings.bankSystem.servlet.MainServlet;
import com.epamTranings.bankSystem.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

@WebFilter(filterName = "jdbcFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter{
    final static Logger logger = LogManager.getLogger(MainServlet.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // Only open connections for the special requests.
        // (For example, the path to the servlet, JSP, ..)
        //
        // Avoid open connection for commons request.
        // (For example: image, css, javascript,... )
        //
        if (this.needJDBC(req)) {

            logger.info("Open Connection for: " + req.getServletPath());

            Connection conn = null;
            try {
                // Create a Connection.
                conn = ConnectionUtils.getConnection();
                // Set outo commit to false.
                conn.setAutoCommit(false);

                // Store Connection object in attribute of request.
                UserUtils.storeConnection(servletRequest, conn);

                // Allow request to go forward
                // (Go to the next filter or target)
                filterChain.doFilter(servletRequest, servletResponse);

                // Invoke the commit() method to complete the transaction with the DB.
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                ConnectionUtils.rollbackQuietly(conn);
                throw new ServletException();
            } finally {
                ConnectionUtils.closeQuietly(conn);
            }
        }
        // With commons requests (images, css, html, ..)
        // No need to open the connection.
        else {
            // Allow request to go forward
            // (Go to the next filter or target)
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * Check the target of the request is a servlet?
     * @param request
     * @return
     */
    private boolean needJDBC(HttpServletRequest request){
        // Servlet Url-pattern: /spath/*
        //
        // => /spath
        String servletPath = request.getServletPath();
        // => /abc/mnp
        String pathInfo = request.getPathInfo();

        String urlPattern = servletPath;

        if (pathInfo != null) {
            // => /spath/*
            urlPattern = servletPath + "/*";
        }

        // Key: servletName.
        // Value: ServletRegistration
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();

        // Collection of all servlet in your Webapp.
        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
}
