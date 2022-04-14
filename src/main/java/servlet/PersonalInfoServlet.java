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
        Part avartar = null;
        avartar = req.getPart("avatar");
        HttpSession session = req.getSession(false);
        Codes error = Codes.OK;
        Message message = new Message(error.getErrorMessage(), false);
        Person p = null;
        if(avartar != null)
        {
            try
            {
                p = new GetPersonByEmailDatabase(getDataSource().getConnection(),(String) session.getAttribute("email")).execute();
                if(avartar.getSize() != 0)
                {
                    error = saveFile(avartar,p.getTaxCode());
                    if(error == Codes.OK)
                    {
                        String avatarPath = Constants.AVATAR_PATH_FOLDER + File.separator + p.getTaxCode() + File.separator
                                + Constants.AVATAR + "." + avartar.getContentType().split(File.separator)[1];
                        new UpdatePersonDatabase(getDataSource().getConnection(),
                                new Person(p.getEmail(),p.getName(),p.getSurname(),p.getPsw(),p.getTaxCode(),p.getBirthDate(),p.getTelephone(),p.getAddress(),avatarPath)).execute();
                    }
                }


                    //aggingi path su db


            } catch (SQLException | NamingException e)
            {
                error = Codes.INTERNAL_ERROR;
            }
        }
        req.setAttribute("personalInfo", p);
        if (error != Codes.OK)
        {
            res.setStatus(error.getHTTPCode());
            message = new Message(error.getErrorMessage(),true);
            req.setAttribute(Constants.MESSAGE, message);
            req.getRequestDispatcher(Constants.PATH_PERSONALINFO).forward(req, res);

        }
        else
        {
            res.setStatus(error.getHTTPCode());
            message = new Message(error.getErrorMessage(),false);
            req.setAttribute(Constants.MESSAGE, message);
            req.getRequestDispatcher(Constants.PATH_PERSONALINFO).forward(req, res);
        }
    }

    private Codes saveFile(Part file, String taxCode) throws IOException {
        Codes error = Codes.OK;
        File createDirectory;
        OutputStream writer = null;
        InputStream content = null;
        String path = null;

        if ((file != null) && file.getSize() != 0) {
            if (!Arrays.stream(Constants.ACCEPTED_EXTENSIONS_AVATAR).
                    anyMatch(file.getContentType().split(File.separator)[1]::equals))
                error = Codes.INVALID_FILE_TYPE;
            else
                path = Constants.AVATAR_PATH_FOLDER + File.separator + taxCode;

            if (error.getErrorCode() == Codes.OK.getErrorCode()) //can proceed to save
            {
                createDirectory = new File(path);
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

                path = path + File.separator + Constants.AVATAR + "." + file.getContentType().split(File.separator)[1];

                try {

                    writer = new FileOutputStream(path,false);
                    content = file.getInputStream();
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = content.read(bytes)) != -1)
                        writer.write(bytes, 0, read);
                } catch (IOException e) {
                    error = Codes.CANNOT_UPLOAD_FILE;
                } finally {
                    if (content != null)
                        content.close();
                    if (writer != null)
                        writer.close();
                }
            }
        }
        return error;
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