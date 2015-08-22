package com.jacekspolnik;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MyServlet", urlPatterns = "/my",
    initParams = {
            @WebInitParam(name = "admin", value = "Jacek Spolnik"),
            @WebInitParam(name = "email", value = "jacek@jacekspolnik.com")
    })
public class MyServlet implements Servlet {

    private transient ServletConfig servletConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException {

        String servletName = servletConfig.getServletName();
        String admin = servletConfig.getInitParameter("admin");
        String email = servletConfig.getInitParameter("email");

        res.setContentType("text/html");

        PrintWriter writer = res.getWriter();
        writer.print("<html><head></head>"
            + "<body>Hello from " + servletName + "<br />"
            + "<p>Admin: " + admin + "</p>"
            + "<p>Email: " + email + "</p>"
            + "</body></html>");
    }

    @Override
    public String getServletInfo() {
        return "My Servlet";
    }

    @Override
    public void destroy() {

    }
}
