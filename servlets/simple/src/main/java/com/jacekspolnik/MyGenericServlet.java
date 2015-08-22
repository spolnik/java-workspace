package com.jacekspolnik;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MyGenericServlet",
    urlPatterns = { "/mygeneric" },
    initParams = {
            @WebInitParam(name = "admin", value = "Jacek Spolnik"),
            @WebInitParam(name = "email", value = "jacek@jacekspolnik.com")
    })
public class MyGenericServlet extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException {

        ServletConfig servletConfig = getServletConfig();

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
}
