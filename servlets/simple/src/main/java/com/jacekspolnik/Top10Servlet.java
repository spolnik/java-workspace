package com.jacekspolnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@WebServlet(name = "Top10Servlet", urlPatterns = "/top10")
public class Top10Servlet extends HttpServlet {

    private static final long serialVersionUID = 21393431232L;

    private List<String> londonAttractions;
    private List<String> parisAttractions;

    @Override
    public void init() throws ServletException {
        londonAttractions = new ArrayList<>(10);

        londonAttractions.add("Buckingham Palace");
        londonAttractions.add("London Eye");
        londonAttractions.add("British Museum");
        londonAttractions.add("National Gallery");
        londonAttractions.add("Big Ben");
        londonAttractions.add("Tower of London");
        londonAttractions.add("Natural History Museum");
        londonAttractions.add("Canary Wharf");
        londonAttractions.add("2012 Olympic Park");
        londonAttractions.add("St Paul's Cathedral");

        parisAttractions = new ArrayList<>(10);

        parisAttractions.add("Eiffel Tower");
        parisAttractions.add("Notre Dame");
        parisAttractions.add("The Louvre");
        parisAttractions.add("Champs Elysees");
        parisAttractions.add("Arc de Triomphe");
        parisAttractions.add("Sainte Chapelle Church");
        parisAttractions.add("Les Invalides");
        parisAttractions.add("Musee d'Orsay");
        parisAttractions.add("Montmarte");
        parisAttractions.add("Sacre Couer Basilica");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String city = req.getParameter("city");
        if ("london".equals(city) || "paris".equals(city)) {
            showAttractions(req, resp, city);
        } else {
            showMainPage(resp);
        }
    }

    private void showAttractions(HttpServletRequest req, HttpServletResponse resp, String city) throws IOException {
        int page = getPageNumber(req);
        List<String> attractions = getAttractions(city);

        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();
        writeAttractionsPage(city, page, attractions, writer);
    }

    private void writeAttractionsPage(String city, int page, List<String> attractions, PrintWriter writer) {
        writer.println("<html><head>" +
                "<title>Top 10 Tourist Attractions</title>" +
                "</head><body>");
        writer.println("<a href='top10'>Select City</a> ");
        writer.println("<hr/>Page " + page + "<hr/>");

        attractionsOnPage(page, attractions).forEach(
                attraction -> writer.println(attraction + "<br />")
        );

        writer.print("<hr style='color:blue'/>" +
                "<a href='?city=" + city +
                "&page=1'>Page 1</a>");
        writer.println("&nbsp; <a href='?city=" + city +
                "&page=2'>Page 2</a>");
        writer.println("</body></html>");
    }

    private Stream<String> attractionsOnPage(int page, List<String> attractions) {
        return attractions.stream()
                .skip((page - 1) * 5)
                .limit(5);
    }

    private List<String> getAttractions(String city) {
        return "london".equals(city)
                ? londonAttractions
                : "paris".equals(city)
                    ? parisAttractions
                    : null;

    }

    private int getPageNumber(HttpServletRequest req) {
        int pageNr = 1;

        String pageParameter = req.getParameter("page");

        if (pageParameter != null) {
            try {
                pageNr = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                // do nothing and retain default value for page
            }
            if (pageNr > 2) {
                pageNr = 1;
            }
        }

        return pageNr;
    }

    private void showMainPage(HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();
        writer.print("<html><head>" +
            "<title>Top 10 Tourist Attractions</title>" +
            "</head><body>" +
            "Please select a city:" +
            "<br /><a href='?city=london'>London</a>" +
            "<br /><a href='?city=paris'>Paris</a>" +
            "</body></html>");
    }
}
