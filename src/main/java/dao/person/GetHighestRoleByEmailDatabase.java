package dao.person;

import resource.TypeOfRoles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetHighestRoleByEmailDatabase
{
    private static final String STATEMENT = """
    SELECT * FROM personroles 
    WHERE person = ?;
    """;

    /*
    // TODO remmed out to build project

    private final Connection connection;
    private final PersonRoles roles;

    public GetHighestRoleByEmailDatabase(final Connection connection, final PersonRoles roles)
    //TODO creare beans ? Con variabili person (String) e roles (List), qui nel DAO invece fare il check del role piu alto
    {
        this.connection = connection;
        this.roles = roles;
    }
    public TypeOfRoles execute() throws SQLException{

        PreparedStatement pstmt = null;
        ResultSet rs = null;


    }
     */
}
