package servlet.access;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import servlet.AbstractServlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterServlet extends AbstractServlet
{
    Logger log = LogManager.getLogger("francesco_caldivezzi_logger");
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException
    {


        req.getRequestDispatcher(Constants.PATH_REGISTER).forward(req, res);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException
    {
        String taxCode,firstName,lastName,address,email,password,confirmPassword;
        Integer telephoneNumber;
        Date birthDate;

        for (Part p: req.getParts())
        {
            switch (p.getName())
            {
                case Constants.TAX_CODE:
                    try (InputStream is = p.getInputStream())
                    {
                        taxCode = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    }
                    break;
                case Constants.FIRST_NAME:
                    try (InputStream is = p.getInputStream())
                    {
                        firstName = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    }
                    break;
                case Constants.LAST_NAME:
                    try (InputStream is = p.getInputStream())
                    {
                        lastName = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    }
                    break;
                case Constants.BIRTH_DATE:
                    try (InputStream is = p.getInputStream())
                    {
                         birthDate = Date.valueOf(new String(is.readAllBytes(), StandardCharsets.UTF_8));
                    }
                    break;
                case Constants.ADDRESS:
                    try (InputStream is = p.getInputStream())
                    {
                        address = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    }
                    break;
                case Constants.TELEPHONE_NUMBER:
                    try (InputStream is = p.getInputStream())
                    {
                         telephoneNumber= Integer.parseInt(new String(is.readAllBytes(), StandardCharsets.UTF_8));
                    }
                    break;
                case Constants.AVATAR:
                    break;
                case Constants.EMAIL:
                    try (InputStream is = p.getInputStream())
                    {
                        email = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    }
                    break;
                case Constants.PASSWORD:
                    try (InputStream is = p.getInputStream())
                    {
                        password = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    }
                    break;
                case Constants.CONFIRM_PASSWORD:
                    try (InputStream is = p.getInputStream())
                    {
                        confirmPassword = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    }
                    break;
                case Constants.MEDICAL_CERTIFICATE:
                    break;
            }

        }
        req.getRequestDispatcher(Constants.PATH_REGISTER).forward(req, res);
    }
}
