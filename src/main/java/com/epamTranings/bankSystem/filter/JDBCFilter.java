package com.epamTranings.bankSystem.filter;

import com.epamTranings.bankSystem.utils.AppUtils;
import com.epamTranings.bankSystem.utils.dbConnectionUtils.ConnectionUtils;
import com.epamTranings.bankSystem.controller.MainServlet;
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
        // (For example, the path to the controller, JSP, ..)
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
                // Set auto commit to false.
                conn.setAutoCommit(false);

                // Store Connection object in attribute of request.
                AppUtils.storeConnection(servletRequest, conn);

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
     * Check the target of the request is a controller?
     * @param request
     * @return
     */
    private boolean needJDBC(HttpServletRequest request){

        String servletPath = request.getServletPath();

        String pathInfo = request.getPathInfo();

        String urlPattern = servletPath;

        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
        }

        // Key: servletName.
        // Value: ServletRegistration
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();

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
