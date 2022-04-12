package servlet.secretary;

import constants.Constants;
import constants.ErrorCodes;
import dao.medicalcertificate.GetMedicalCertificateDatabase;
import dao.medicalcertificate.InsertMedicalCertificateDatabase;
import dao.medicalcertificate.UpdateMedicalCertificateDatabase;
import dao.person.GetPersonByEmailDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import resource.*;

import java.io.IOException;
import java.sql.Date;
import jakarta.mail.MessagingException;
import resource.Message;
import resource.Person;
import servlet.AbstractServlet;
import utils.InputValidation;
import utils.MailTypes;

import javax.naming.NamingException;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author Simone D'Antimo
 * TODO Check medical certificate extension
 * Il mail sender va ma è commentato per debugging purpose
 */

public class AddMedicalCertificateServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.PATH_SECRETARY_ADD_CERTIFICATE).forward(req, res);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String email = null;
        String doctorName = null;
        Date expirationDate = null;
        Part medicalCertificate = null;

        boolean registrable = true;
        Message message = new Message(ErrorCodes.OK.getErrorMessage(), false);
        ErrorCodes error = parseParams(req, res);


        medicalCertificate = req.getPart(Constants.MEDICAL_CERTIFICATE);
        if(medicalCertificate == null){
            error = ErrorCodes.EMPTY_INPUT_FIELDS;
            registrable = false;
        }else if (!medicalCertificate.getContentType().equals("application/pdf")){
            error = ErrorCodes.INVALID_FILE_TYPE;
            registrable = false;
        }

        if (error.getErrorCode() != ErrorCodes.OK.getErrorCode()) {
            message = new Message(error.getErrorMessage(), true);
            registrable = false;
        }

        if (registrable) // it means that params are ok for now
        {
            email = req.getParameter(Constants.MEDICALCERTIFICATE_PERSON);
            doctorName = req.getParameter(Constants.MEDICALCERTIFICATE_DOCTORNAME);
            expirationDate = Date.valueOf(req.getParameter(Constants.MEDICALCERTIFICATE_EXPIRATIONDATE));


            //insertUser
            error = insertCertificate(email, expirationDate,doctorName, medicalCertificate);
            if (error.getErrorCode() != ErrorCodes.OK.getErrorCode()) {
                message = new Message(error.getErrorMessage(), true);
                registrable = false;
            }
        }

        res.setStatus(error.getHTTPCode());
        req.setAttribute(Constants.MESSAGE, message);

        if (!registrable) {
            req.getRequestDispatcher(Constants.PATH_SECRETARY_ADD_CERTIFICATE).forward(req, res);
        }else {
            req.getRequestDispatcher(Constants.PATH_SECRETARY_ADD_CERTIFICATE).forward(req, res);
        }
    }

    public ErrorCodes parseParams(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String email = null;
        String doctorName = null;
        Date expirationDate = null;
        ErrorCodes error = ErrorCodes.OK;
        //Error nel get params
        try {

            doctorName = req.getParameter("doctorname");
            email = req.getParameter(Constants.MEDICALCERTIFICATE_PERSON);
            expirationDate = Date.valueOf(req.getParameter(Constants.MEDICALCERTIFICATE_EXPIRATIONDATE));

        } catch (IllegalArgumentException e)
        {
            error = ErrorCodes.INVALID_FIELDS;
        }

        if (error.getErrorCode() == ErrorCodes.OK.getErrorCode())
        {
            if (
                    doctorName == null || doctorName.isEmpty() ||
                    email == null || email.isEmpty() ||
                    expirationDate == null) // Check if some fields are empty
            {
                error = ErrorCodes.EMPTY_INPUT_FIELDS;
            }if (!InputValidation.isValidEmailAddress(email)) {
                error = ErrorCodes.NOT_A_MAIL;
            }
            if(!expirationDate.after(Calendar.getInstance().getTime())){
                error = ErrorCodes.INVALID_FIELDS;
            }
        }
        return error;
    }

    private ErrorCodes insertCertificate(String email, Date expirationDate, String doctorName, Part medicalCertificate) {

        ErrorCodes error = ErrorCodes.OK;
        Person p1 = null;
        List<MedicalCertificate> med1;

        try {
            //Find if the Medical certificate is associated to a person
            p1 = (new GetPersonByEmailDatabase(getDataSource().getConnection(), email)).execute();
            med1 = (new GetMedicalCertificateDatabase(getDataSource().getConnection(), p1)).execute();

            if(p1 != null) { //User exist, let insert the certificate

                error = saveFile(medicalCertificate, p1.getTaxCode());
                String pathCertificate = Constants.MEDICAL_CERTIFICATE_PATH_FOLDER + File.separator + p1.getTaxCode() +
                        File.separator + Constants.MEDICAL_CERTIFICATE + ".pdf";

                try {

                    MedicalCertificate m = new MedicalCertificate(email, expirationDate, doctorName, pathCertificate);

                    if (med1.isEmpty()) //Med1 null --> Insert certificate
                        new InsertMedicalCertificateDatabase(getDataSource().getConnection(), m).execute();

                    else //Med1 not null
                        new UpdateMedicalCertificateDatabase(getDataSource().getConnection(), m).execute();


                    /* Mail is working only with TRUE Existing email
                    try{
                        //Send mail that everything correct
                        MailTypes.mailForMedicalCertificateUploaded(p1);
                    }catch (MessagingException e) {

                    error = ErrorCodes.INTERNAL_ERROR;
                }
                     */

                } catch (SQLException | NamingException e) {
                    error = ErrorCodes.INVALID_FIELDS;
                }
            }else{
                error = ErrorCodes.EMAIL_NOT_FOUND;
            }
        }catch (SQLException | NamingException e){
            error = ErrorCodes.INTERNAL_ERROR;
        }catch(IOException e){
            error = ErrorCodes.INVALID_FILE_TYPE;
        }

        return error;
    }

    private ErrorCodes saveFile(Part file, String taxCode) throws IOException {
        ErrorCodes error = ErrorCodes.OK;
        File createDirectory;
        OutputStream writer = null;
        InputStream content = null;
        String path = null;

        if ((file != null) && file.getSize() != 0) {
            if (!file.getContentType().equals("application/pdf"))
                throw new IOException("Not PDF");
            else
                path = Constants.MEDICAL_CERTIFICATE_PATH_FOLDER + File.separator + taxCode;

            if (error.getErrorCode() == ErrorCodes.OK.getErrorCode()) //can proceed to save
            {
                createDirectory = new File(path);
                if (!createDirectory.exists())
                    createDirectory.mkdir();

                path = path + File.separator + Constants.MEDICAL_CERTIFICATE + ".pdf";

                try {
                    writer = new FileOutputStream(path);
                    content = file.getInputStream();
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = content.read(bytes)) != -1)
                        writer.write(bytes, 0, read);
                } catch (IOException e) {
                    error = ErrorCodes.CANNOT_UPLOAD_FILE;
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
}