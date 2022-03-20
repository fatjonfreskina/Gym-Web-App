package it.unipd.dei.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class IndexServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        // set the MIME media type of the response
        res.setContentType("text/html; charset=utf-8");

        // get a stream to write the response
        PrintWriter out = res.getWriter();

        // write the HTML page
        out.printf("<!DOCTYPE html>%n");

        out.printf("<html lang=\"en\">%n");
        out.printf("<head>%n");
        out.printf("<meta charset=\"utf-8\">%n");
        out.printf("<title>HelloWorld Servlet Response</title>%n");
        out.printf("</head>%n");

        out.printf("<body>%n");
        out.printf("<h1>HelloWorld Servlet Response</h1>%n");
        out.printf("<hr/>%n");
        out.printf("<p>%n");
        out.printf("Hello, world!%n");
        out.printf("</p>%n");
        out.printf("</body>%n");

        out.printf("</html>%n");

        // flush the output stream buffer
        out.flush();

        // close the output stream
        out.close();

        // write a "log" statement
        System.out.printf("[INFO] HelloWorldServlet - %s - Request successfully served.%n",
                new Timestamp(System.currentTimeMillis()).toString());
    }
}