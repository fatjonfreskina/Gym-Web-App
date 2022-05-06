package dao.medicalcertificate;

import constants.Constants;
import resource.MedicalCertificate;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * This DAO is used to get the last medical certificate
 *
 * @author Francesco Caldivezzi
 * */
public class GetLastMedicalCertfiticateByPersonDatabase {

    /**
     * The SELECT query to execute on the database
     */
    private static final String STATEMENT = """
            select *
            from medicalcertificate
            where person = ? and expirationdate = (select max(expirationdate) from medicalcertificate where person = ?)            
            """;

    /**
     * Connection to the database
     */
    private final Connection conn;

    /**
     * Person object
     */
    private final Person person;

    /**
     *
     * Parametric constructor
     *
     * @param conn the database connection
     * @param person the person object to be passed to the query
     */
    public GetLastMedicalCertfiticateByPersonDatabase(final Connection conn, final Person person)
    {
        this.conn = conn;
        this.person = person;
    }

    /**
     *
     * Execute the query
     *
     * @return MedicalCertificate object matched by the query
     * @throws SQLException
     */
    public MedicalCertificate execute() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MedicalCertificate ret= null;
        try {
            pstmt = conn.prepareStatement(STATEMENT);
            pstmt.setString(1, person.getEmail());
            pstmt.setString(2, person.getEmail());
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
