package servlet;

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
        out.println("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="utf-8">
                        
                        <style type="text/css">
                            
                            .menu_bar
                            {
                                list-style-type: none;
                                margin: 0;
                                padding: 0;
                                
                                overflow: hidden;
                                background-color: #AFEEEE;
                            }
                            
                            .menu_item
                            {
                                display: block;
                                float: left;
                                
                                color: black;
                                font-size: 16px;
                                text-align: center;
                                padding: 16px 16px;
                            }
                            
                            .menu_item:hover
                            {
                                background-color: #5F9EA0;
                            }
                        
                        </style>
                        
                        <title>GWA Homepage</title>
                    </head>
                    <body>
                        <div style="width: 100%; height: 100%;">
                            <div style="width: 100%; height: 100%; float: top;">
                                <ul class="menu_bar">
                                    <li class="menu_item"><a href="#">Home</a></li>
                                    <li class="menu_item"><a href="#">Hours</a></li>
                                    <li class="menu_item"><a href="#">The Gym</a></li>
                                    <li class="menu_item"><a href="#">Courses</a></li>
                                    <li class="menu_item"><a href="#">Staff</a></li>
                                    <li class="menu_item"><a href="#">Prices</a></li>
                                    <li class="menu_item"><a href="#">Contact Us</a></li>
                                    <li class="menu_item"><a href="#">Login</a></li>
                                    <li class="menu_item"><a href="#">Register</a></li>
                                </ul>
                            </div>
                            <div style="width: 100%; height: 100%;">
                                <h1>Index Servlet Response</h1>
                                <hr/>
                                <p>Hello, world!</p>
                            </div>
                            <div style="width: 100%; height: 100%; float: bottom;">
                                <ul class="menu_bar">
                                    <li class="menu_item">Useless 1!</li>
                                    <li class="menu_item">Useless 2!</li>
                                    <li class="menu_item">Useless 3!</li>
                                </ul>
                            </div>
                        </div>
                    </body>
                    </html>
                    """);


        // flush the output stream buffer
        out.flush();

        // close the output stream
        out.close();

        // write a "log" statement
        System.out.printf("[INFO] IndexServlet - %s - Request successfully served.%n",
                new Timestamp(System.currentTimeMillis()));
    }
}