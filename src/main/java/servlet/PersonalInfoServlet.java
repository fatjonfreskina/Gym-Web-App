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
import java.util.regex.Matcher;

/**
 * Shows all the personal information about the current user (must be logged in).
 *
 * @author Marco Alessio
 */
public class PersonalInfoServlet extends AbstractServlet
{
    /**
     * Retrieves the "Personal Info" page, which shows all personal information about the logged-in user.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws ServletException If some internal error happens.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        // Retrieve the email of the user from the session.

        HttpSession session = req.getSession(false);
        if (session == null)
        {
            // This should never happen, given the use of PersonalInfoFilter.

            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);
            return;
        }

        final String email = session.getAttribute(Constants.EMAIL).toString();
        if (email == null)
        {
            // This should never happen, given the use of PersonalInfoFilter.

            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);
            return;
        }

        // Retrieve all data about the given user.
        Person person;
        try
        {
            person = new GetPersonByEmailDatabase(getDataSource().getConnection(), email).execute();
        } catch (NamingException | SQLException e)
        {
            // This should never happen, given the use of PersonalInfoFilter.

            throw new ServletException(e);
        }

        // Generate "Personal Info" page.
        req.setAttribute("personalInfo", person);
        req.getRequestDispatcher(Constants.PATH_PERSONALINFO).forward(req, res);
    }


    /**
     * Handles the post request by saving the avatar and sending a response message representing if the request has been
     * successfully processed
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        Part avatar = req.getPart("avatar");
        HttpSession session = req.getSession(false);
        Codes error = Codes.OK;
        Person p = null;


        if (avatar != null)
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

    /**
     * Saves the avatar
     * @param file  the file
     * @param taxCode  the user tax code
     * @return
     * @throws IOException
     */
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

    /**
     * Deletes a given folder
     * @param folder  the folder to delete
     */
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