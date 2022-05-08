package resource;

import java.sql.Date;

/**
 * This Java Bean contains all the info about a medical certificate.
 *
 * @author Alberto Campeol
 */
public class MedicalCertificate {

    private final String personEmail;

    private final Date expirationDate;

    private final String doctorName;

    private final String path;

    /**
     * Default constructor, with all parameters
     *
     * @param personEmail    the email whose owner has a medical certificate
     * @param expirationDate the expiration date of the medical certificate
     * @param doctorName     the name of the doctor who signed this medical certificate
     * @param path           a path for the uploaded medical certificate, as a static resource
     */

    public MedicalCertificate(final String personEmail, final Date expirationDate, final String doctorName, final String path) {
        this.personEmail = personEmail;
        this.expirationDate = expirationDate;
        this.doctorName = doctorName;
        this.path = path;
    }


    /**
     * Gets the person's email
     *
     * @return the person's email
     */
    public final String getPersonEmail() {
        return personEmail;
    }

    /**
     * Gets the medical certificate's expiration date
     *
     * @return the medical certificate's expiration date
     */
    public final Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Gets the doctor who signed this medical certificate
     *
     * @return the doctor who signed this medical certificate
     */
    public final String getDoctorName() {
        return doctorName;
    }

    /**
     * Gets the path to this medical certificate
     *
     * @return the path to this medical certificate
     */
    public final String getPath() {
        return path;
    }

}
