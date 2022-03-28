package resource;

import java.util.Date;

public class MedicalCertificate {

    private final String personEmail;

    private final Date expirationDate;

    private final String doctorName;

    private final String path;

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
