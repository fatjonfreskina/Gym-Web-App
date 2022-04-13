package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.ErrorCodes;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;


/**
 * Base class for all servlets that deals with REST. It provides some convenient methods to avoid
 * duplicating the code in all REST servlets, such as:
 * <ul>
 *     <li>
 *         Method {@code sendDataResponse()} to send a response with the specified data in JSON format.
 *     </li>
 *     <li>
 *         Method {@code sendErrorResponse()} to send a response with the specified error in JSON format.
 *     </li>
 *     <li>
 *         Method {@code checkAcceptMediaType()} to check if the "accept" header in the HTTP request
 *         is present and it reports accepting JSON format.
 *     </li>
 *     <li>
 *         Method {@code checkContentTypeMediaType()} to check if the "Content-Type" header in the
 *         HTTP request is present and it reports accepting JSON format.
 *     </li>
 *     <li>
 *         Default implementation of methods {@code doDelete()}, {@code doGet()}, {@code doHead()},
 *         {@code doOptions()}, {@code doPost()}, {@code doPut()} and {@code doTrace()},
 *         that returns a METHOD_NOT_ALLOWED error.
 *     </li>
 * </ul>
 *
 * @author Marco Alessio
 */
public class AbstractRestServlet extends AbstractServlet
{
    protected static final Gson GSON = new GsonBuilder()/*.setPrettyPrinting()*/.create();

    protected static final String ACCEPT_HEADER = "accept";

    protected static final String UTF8_ENCODING = "utf-8";

    protected static final String JSON_MEDIA_TYPE = "application/json";
    protected static final String ALL_MEDIA_TYPE = "*/*";


    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }


    @Override
    public void destroy()
    {
        super.destroy();
    }


    /**
     * Check if the "accept" header in the HTTP request is present and reports accepting JSON format,
     * otherwise send back an error response with the appropriate error code.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    protected void checkAcceptMediaType(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        final String accept = req.getHeader(ACCEPT_HEADER);

        // Check if the "accept" HTTP header is not found in the request.
        if (accept == null)
        {
            sendErrorResponse(res, ErrorCodes.ACCEPT_MISSING);
            return;
        }

        // Check if the "accept" HTTP header does not report accepting JSON format.
        if ((!accept.contains(JSON_MEDIA_TYPE)) && (!accept.equals(ALL_MEDIA_TYPE)))
        {
            sendErrorResponse(res, ErrorCodes.MEDIA_TYPE_NOT_SUPPORTED);
            return;
        }

        // HTTP header "accept" reports accepting JSON format => nothing to do.
    }


    /**
     * Check if the "Content-Type" header in the HTTP request is present and reports accepting JSON format,
     * otherwise send back an error response with the appropriate error code.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    protected void checkContentTypeMediaType(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        final String contentType = req.getContentType();

        // Check if the "Content-Type" HTTP header is not found in the request.
        if (contentType == null)
        {
            sendErrorResponse(res, ErrorCodes.CONTENTTYPE_MISSING);
            return;
        }

        // Check if the "Content-Type" HTTP header does not report accepting JSON format.
        if (!contentType.contains(JSON_MEDIA_TYPE))
        {
            sendErrorResponse(res, ErrorCodes.MEDIA_TYPE_NOT_SUPPORTED);
            return;
        }

        // HTTP header "Content-Type" reports accepting JSON format => nothing to do.
    }


    /**
     * Send a response with the specified data in JSON format.
     * @param res The HTTP response.
     * @param data The data to send in JSON format.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    protected void sendDataResponse(HttpServletResponse res, Object data) throws IOException
    {
        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);

        // Set HTTP error code.
        res.setStatus(HttpServletResponse.SC_OK);

        // Write the output.
        final PrintWriter out = res.getWriter();
        out.print(GSON.toJson(data));

        // Flush the output stream and close it.
        out.flush();
        out.close();
    }


    /**
     * Send a response with the specified data in JSON format.
     * @param res The HTTP response.
     * @param list The list of data to send in JSON format.
     * @param type The type of the list elements, needed by Gson to perform the serialization.
     *             Must use this code: {@code new TypeToken<List<ElementType>>() {}.getType()},
     *             replacing {@code ElementType} with the correct value.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    protected void sendDataResponse(HttpServletResponse res, Object list, Type type) throws IOException
    {
        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);

        // Set HTTP error code.
        res.setStatus(HttpServletResponse.SC_OK);

        // Write the output.
        final PrintWriter out = res.getWriter();
        out.print(GSON.toJson(list, type));

        // Flush the output stream and close it.
        out.flush();
        out.close();
    }


    /**
     * Send a response with the specified error in JSON format.
     * @param res The HTTP response.
     * @param code The error to send in JSON format.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    protected void sendErrorResponse(HttpServletResponse res, ErrorCodes code) throws IOException
    {
        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);

        // Set HTTP error code.
        res.setStatus(code.getHTTPCode());

        // Write the output.
        final PrintWriter out = res.getWriter();
        out.print(GSON.toJson(new Message(code.getErrorMessage(), true)));

        // Flush the output stream and close it.
        out.flush();
        out.close();
    }


    /**
     * Default implementation, which sends a METHOD_NOT_ALLOWED error back as response.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws ServletException Never thrown.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        sendErrorResponse(res, ErrorCodes.METHOD_NOT_ALLOWED);
    }


    /**
     * Default implementation, which sends a METHOD_NOT_ALLOWED error back as response.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws ServletException Never thrown.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        sendErrorResponse(res, ErrorCodes.METHOD_NOT_ALLOWED);
    }


    /**
     * Default implementation, which sends a METHOD_NOT_ALLOWED error back as response.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws ServletException Never thrown.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        sendErrorResponse(res, ErrorCodes.METHOD_NOT_ALLOWED);
    }


    /**
     * Default implementation, which sends a METHOD_NOT_ALLOWED error back as response.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws ServletException Never thrown.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        sendErrorResponse(res, ErrorCodes.METHOD_NOT_ALLOWED);
    }


    /**
     * Default implementation, which sends a METHOD_NOT_ALLOWED error back as response.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws ServletException Never thrown.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        sendErrorResponse(res, ErrorCodes.METHOD_NOT_ALLOWED);
    }


    /**
     * Default implementation, which sends a METHOD_NOT_ALLOWED error back as response.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws ServletException Never thrown.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        sendErrorResponse(res, ErrorCodes.METHOD_NOT_ALLOWED);
    }


    /**
     * Default implementation, which sends a METHOD_NOT_ALLOWED error back as response.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @throws ServletException Never thrown.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        sendErrorResponse(res, ErrorCodes.METHOD_NOT_ALLOWED);
    }
}