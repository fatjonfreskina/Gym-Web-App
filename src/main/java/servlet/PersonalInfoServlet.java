package servlet;

import constants.Codes;
import constants.Constants;
import dao.person.GetPersonByEmailDatabase;
import dao.person.UpdatePersonDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import resource.Message;
import resource.Person;

import javax.naming.NamingException;
import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Shows all the personal information about the current user (must be logged in).
 * @author Marco Alessio
 */
public class PersonalInfoServlet extends AbstractServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        //Old version way to get the "email" of the user.
        //final String email = req.getParameter("email");

        HttpSession session = req.getSession(false);
        //This should never happen, given the use of PersonalInfoFilter.
        if (session == null)
            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);

        final String email = (String) session.getAttribute(Constants.EMAIL);
        //This should never happen, given the use of PersonalInfoFilter.
        if (email == null)
            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);

        Person person;

        try
        {
            //TODO: is still needed to query from the database, or the data is already available through the session object?
            person = new GetPersonByEmailDatabase(getDataSource().getConnection(), email).execute();
        } catch (NamingException | SQLException e)
        {
            //This should never happen, given the use of PersonalInfoFilter.
            throw new ServletException(e);
        }

        req.setAttribute("personalInfo", person);
        req.getRequestDispatcher(Constants.PATH_PERSONALINFO).forward(req, res);
    }


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        Part avatar = req.getPart("avatar");
        HttpSession session = req.getSession(false);
        Codes error = Codes.OK;
        Person p = null;


        if(avatar != null)
        {
            try
            {
                p = new GetPersonByEmailDatabase(getConnection(), session.getAttribute("email").toString()).execute();

                if(avatar.getSize() != 0)
                {
                    error = saveFile(avatar, p.getTaxCode());
                    if(error == Codes.OK)
                    {
                        final String filename = avatar.getSubmittedFileName();
                        final Matcher matcher = Constants.ACCEPTED_AVATAR_FILENAME_REGEX.matcher(filename);
                        if (matcher.find())
                        {
                            final String extension = matcher.group(2);

                            final String avatarPath = Constants.AVATAR_PATH_FOLDER + File.separator + p.getTaxCode() +
                                    File.separator + Constants.AVATAR + "." + extension;

                            final Person person = new Person(p.getEmail(), p.getName(), p.getSurname(), p.getPsw(),
                                    p.getTaxCode(), p.getBirthDate(), p.getTelephone(), p.getAddress(), avatarPath);

                            new UpdatePersonDatabase(getConnection(), person).execute();
                        }
                        else
                            error = Codes.INTERNAL_ERROR;
                    }
                }
            } catch (SQLException | NamingException e)
            {
                error = Codes.INTERNAL_ERROR;
            }
        }
        req.setAttribute("personalInfo", p);
        if (error != Codes.OK)
        {
            res.setStatus(error.getHTTPCode());
            final Message message = new Message(error.getErrorMessage(),true);
            req.setAttribute(Constants.MESSAGE, message);
            req.getRequestDispatcher(Constants.PATH_PERSONALINFO).forward(req, res);
        }
        else
        {
            res.setStatus(error.getHTTPCode());
            final Message message = new Message(error.getErrorMessage(),false);
            req.setAttribute(Constants.MESSAGE, message);
            req.getRequestDispatcher(Constants.PATH_PERSONALINFO).forward(req, res);
        }
    }

    private Codes saveFile(Part file, String taxCode) throws IOException {
        OutputStream writer = null;
        InputStream content = null;

        if ((file != null) && file.getSize() != 0)
        {
            final String filename = file.getSubmittedFileName();
            final Matcher matcher = Constants.ACCEPTED_AVATAR_FILENAME_REGEX.matcher(filename);
            if (!matcher.find())
                return Codes.INVALID_FILE_TYPE;

            final String extension = matcher.group(2);

            final String dirPath = (Constants.AVATAR_PATH_FOLDER + "/" + taxCode).replace('/', File.separatorChar);

            final File createDirectory = new File(dirPath);
            if (!createDirectory.exists())
                createDirectory.mkdir();

            File[] files = createDirectory.listFiles();
            if(files != null)
            {
                for (File f: files)
                {
                    f.delete();
                }
            }

            final String filePath = dirPath + File.separator + Constants.AVATAR + "." + extension;

            try {

                writer = new FileOutputStream(filePath,false);
                content = file.getInputStream();
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = content.read(bytes)) != -1)
                    writer.write(bytes, 0, read);
            } catch (IOException e) {
                return Codes.CANNOT_UPLOAD_FILE;
            } finally {
                if (content != null)
                    content.close();
                if (writer != null)
                    writer.close();
            }
        }
        return Codes.OK;
    }

    public static void deleteFolder(File folder)
    {
        File[] files = folder.listFiles();
        if(files!=null)
        { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
}