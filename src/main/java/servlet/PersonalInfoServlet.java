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

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import java.awt.image.BufferedImage;
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
     * successfully processed.
     * @param req  The HTTP request.
     * @param res  The HTTP response.
     * @throws ServletException If some internal error happens.
     * @throws IOException If something happens when writing the response to the output stream.
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
                // Retrieve user data from the database.
                p = new GetPersonByEmailDatabase(getConnection(), session.getAttribute("email").toString()).execute();

                if (avatar.getSize() != 0)
                {
                    // Save the avatar of the user to disk.
                    error = saveFile(avatar, p.getTaxCode());
                    if (error == Codes.OK)
                    {
                        // Update the path to the avatar image file of the given user in the database.
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

                            req.setAttribute("personalInfo", person);
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

        //req.setAttribute("personalInfo", p);

        // Return the appropriate error message and send back to the "Personal Info" page.
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
     * Saves the avatar of a given user in the filesystem.
     * @param file The file containing the avatar image to store.
     * @param taxCode The user's tax code.
     * @return An error code, indicating if and what error occurred.
     */
    private Codes saveFile(Part file, String taxCode)
    {
        if ((file != null) && file.getSize() != 0)
        {
            // Build the path to the avatar folder of the given user, which will contain his/her avatar image.
            final String filename = file.getSubmittedFileName();
            final Matcher matcher = Constants.ACCEPTED_AVATAR_FILENAME_REGEX.matcher(filename);
            if (!matcher.find())
                return Codes.INVALID_FILE_TYPE;

            final String extension = matcher.group(2);

            final String dirPath = (Constants.AVATAR_PATH_FOLDER + "/" + taxCode).replace('/', File.separatorChar);

            // Create the avatar folder of the given user, if it does not exist yet.
            final File createDirectory = new File(dirPath);
            if (!createDirectory.exists())
                createDirectory.mkdir();

            // Delete any existing, if any, file inside the avatar folder of the given user.
            File[] files = createDirectory.listFiles();
            if(files != null)
            {
                for (File f: files)
                {
                    f.delete();
                }
            }

            //final String filePath = dirPath + File.separator + Constants.AVATAR + "." + extension;

            //TODO: quick fix for bug "avatarPath has extension .jpg no matter what", just save as jpg and call it a day.
            //@author Marco Alessio
            final File saveFile = new File(dirPath + File.separator + Constants.AVATAR + ".jpg");

            try {
                BufferedImage img = ImageIO.read(file.getInputStream());

                //TODO: quick fix for "Personal Info" avatar layout issue.
                //Just save the portion of image relevant to show.
                //@author Marco Alessio

                final int width = img.getWidth();
                final int height = img.getHeight();
                final double aspectRatio = (double)width / (double)height;

                final int ratioWidthTarget = 4;
                final int ratioHeightTarget = 3;
                final double ratioTarget = (double)ratioWidthTarget / (double)ratioHeightTarget; //4:3

                final int newWidth, newHeight;
                if (aspectRatio >= ratioTarget) //Image larger than target aspect ratio.
                {
                    newHeight = (height / ratioHeightTarget) * ratioHeightTarget;
                    newWidth = (newHeight / ratioHeightTarget) * ratioWidthTarget;
                }
                else //Image higher than target aspect ratio.
                {
                    newWidth = (width / ratioWidthTarget) * ratioWidthTarget;
                    newHeight = (newWidth / ratioWidthTarget) * ratioHeightTarget;
                }

                final int x = (width - newWidth) / 2;
                final int y = (height - newHeight) / 2;

                img = img.getSubimage(x, y, newWidth, newHeight);

                ImageIO.write(img, "jpg", saveFile);
            }
            catch (IOException e)
            {
                return Codes.CANNOT_UPLOAD_FILE;
            }

            /*
            // Copy the data from the input file to the output one.
            try (InputStream content = file.getInputStream();
                 OutputStream writer = new FileOutputStream(filePath,false))
            {
                final byte[] bytes = new byte[1024];

                int read;
                while ((read = content.read(bytes)) != -1)
                    writer.write(bytes, 0, read);
            } catch (IOException e)
            {
                return Codes.CANNOT_UPLOAD_FILE;
            }
            */

            return Codes.OK;
        }

        return Codes.CANNOT_UPLOAD_FILE;
    }
}