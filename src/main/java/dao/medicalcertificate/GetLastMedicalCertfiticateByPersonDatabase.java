package dao.medicalcertificate;

import constants.Constants;
import resource.MedicalCertificate;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francesco Caldivezzi
 * */

public class GetLastMedicalCertfiticateByPersonDatabase {

    private static final String STATEMENT = """
            select *
            from medicalcertificate
            where person = ? and expirationdate = (select max(expirationdate) from medicalcertificate where person = ?)            
            """;
    private final Connection conn;
    private final Person person;


    public GetLastMedicalCertfiticateByPersonDatabase(final Connection conn, final Person person)
    {
        this.conn = conn;
        this.person = person;
    }

    public MedicalCertificate execute() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MedicalCertificate ret= null;
        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, Constants.PERSON_EMAIL);
            pstmt.setString(2, Constants.PERSON_EMAIL);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                ret = new MedicalCertificate(
                        rs.getString(Constants.MEDICALCERTIFICATE_PERSON),
                        rs.getDate(Constants.MEDICALCERTIFICATE_EXPIRATIONDATE),
                        rs.getString(Constants.MEDICALCERTIFICATE_DOCTORNAME),
                        rs.getString(Constants.MEDICALCERTIFICATE_PATH)
                );
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            conn.close();
        }
        return ret;
    }

}
