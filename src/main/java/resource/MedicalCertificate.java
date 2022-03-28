package resource;

import java.sql.Date;

/**
 * This Java Bean contains all the info about a medical certificate.
 * @author Alberto Campeol
 */
public class MedicalCertificate {

    private final String personEmail;

    private final Date expirationDate;

    private final String doctorName;

    private final String path;

    /**
     * Default constructor, with all parameters
     * @param personEmail the email whose owner has a medical certificate
     * @param expirationDate the expiration date of the medical certificate
     * @param doctorName the name of the doctor who signed this medical certificate
     * @param path a path for the uploaded medical certificate, as a static resource
     */

    public MedicalCertificate(final String personEmail, final Date expirationDate, final String doctorName, final String path)
    {
        this.personEmail = personEmail;
        this.expirationDate = expirationDate;
        this.doctorName = doctorName;
        this.path = path;
    }

    public final String getPersonEmail()
    {
        return personEmail;
    }

    public final Date getExpirationDate()
    {
        return expirationDate;
    }

    public final String getDoctorName()
    {
        return doctorName;
    }

    public final String getPath()
    {
        return path;
    }

}
