package database;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Class used by command line to set up the database environment BEFORE deploying the application
 * This class executes SQL statements using JDBC driver
 * Run the main method to perform operations on the database
 *
 * @author Riccardo Forzan
 */
public class DataBaseUtils {

    private static final String DB_ROBOT = "jdbc:postgresql://localhost:5432/robot";
    private static final String DB_GWA = "jdbc:postgresql://localhost:5432/gwa_db";
    private static final String USER = "robot";
    private static final String PASS = "robot";

    private static final String CREATE_DATABASE_FILEPATH = "src/main/database/CREATE_DATABASE.sql";
    private static final String CREATE_TABLES_FILEPATH = "src/main/database/CREATE_TABLE.sql";
    private static final String SEED_DATABASE_FILEPATH = "src/main/database/SEED.sql";
    private static final String FAKE_DATABASE_FILEPATH = "src/main/database/FAKE_DATA.sql";
    private static final String DROP_DATABASE_FILEPATH = "src/main/database/DROP.sql";

    /**
     * Main class used to prepare the database
     *
     * @param args arguments (0 expected)
     * @throws SQLException is thrown if something goes wrong while querying the database
     * @throws IOException  is thrown if something goes wrong while reading the source files
     */
    public static void main(String[] args) throws SQLException, IOException {

        System.out.println("COMMAND LINE TOOL FOR DATABASE MANAGEMENT, USE:");
        System.out.println("* 1     - TO CREATE THE DATABASE");
        System.out.println("* 2     - TO CREATE THE DATABASE TABLES");
        System.out.println("* 3     - TO SEED THE DATABASE TABLES");
        System.out.println("* 4     - TO LOAD FAKE VALUES IN THE TABLES");
        System.out.println("* 5     - TO DROP DATABASE");
        System.out.println("* 6     - TO DROP DATABASE AND EXECUTE 1 to 5 ABOVE");
        System.out.print("SELECT AN OPTION: ");

        //Read the selected option from console
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int option = Integer.parseInt(reader.readLine());

        switch (option) {
            case 1 -> createDatabase();
            case 2 -> createDatabaseTables();
            case 3 -> seedDatabase();
            case 4 -> fakeDataDatabase();
            case 5 -> dropDatabase();
            case 6 -> {
                dropDatabase();
                createDatabase();
                createDatabaseTables();
                seedDatabase();
                fakeDataDatabase();
            }
        }

    }

    /**
     * Creates the database
     *
     * @throws SQLException          if a SQL statement fails
     * @throws FileNotFoundException if the file containing the instructions is not present or cannot be opened
     */
    private static void createDatabase() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_ROBOT, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(CREATE_DATABASE_FILEPATH);
        for (String statement : statements) {
            System.out.println(String.format("Executing: %s", statement));
            stmt.execute(statement);
        }
    }

    /**
     * Creates the database tables
     *
     * @throws SQLException          if a SQL statement fails
     * @throws FileNotFoundException if the file containing the instructions is not present or cannot be opened
     */
    private static void createDatabaseTables() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_GWA, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(CREATE_TABLES_FILEPATH);
        for (String statement : statements) {
            System.out.println(String.format("Executing: %s", statement));
            stmt.execute(statement);
        }
    }

    /**
     * Seeds some tables in the database (the table with typeofroles for example)
     *
     * @throws SQLException          if a SQL statement fails
     * @throws FileNotFoundException if the file containing the instructions is not present or cannot be opened
     */
    private static void seedDatabase() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_GWA, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(SEED_DATABASE_FILEPATH);
        for (String statement : statements) {
            System.out.println(String.format("Executing: %s", statement));
            stmt.execute(statement);
        }
    }

    /**
     * Populates the database with the fake data inside the file
     *
     * @throws SQLException          if a SQL statement fails
     * @throws FileNotFoundException if the file containing the instructions is not present or cannot be opened
     */
    private static void fakeDataDatabase() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_GWA, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(FAKE_DATABASE_FILEPATH);
        for (String statement : statements) {
            System.out.println(String.format("Executing: %s", statement));
            stmt.execute(statement);
        }
    }

    /**
     * Drops THE WHOLE database
     *
     * @throws SQLException          if a SQL statement fails
     * @throws FileNotFoundException if the file containing the instructions is not present or cannot be opened
     */
    private static void dropDatabase() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_ROBOT, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(DROP_DATABASE_FILEPATH);
        for (String statement : statements) {
            System.out.println(String.format("Executing: %s", statement));
            stmt.execute(statement);
        }
    }

    /**
     * Creates some users
     *
     * @param faker         faker instance
     * @param numberOfUsers number of users you want to create
     */
    private static void createUsers(Faker faker, int numberOfUsers) {

        String[] roles = {"Trainee", "Trainer", "Secretary"};

        Random r = new Random();

        for (int i = 0; i < numberOfUsers; i++) {

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();

            //Pick a random role from the roles array
            String role = roles[r.nextInt(3)];
            //Generate a fake email
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
            //Password hash
            String pwd = "d41d8cd98f00b204e9800998ecf8427e";
            //Generate a 16 character string
            String tax_code = RandomStringUtils.randomAlphanumeric(16).toUpperCase();
            //Generate date in format dd/mm/yyyy
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatter.format(faker.date().birthday());
            //Generate fake phone number
            String phone = RandomStringUtils.randomNumeric(10);
            //Generate fake address
            String address = faker.address().fullAddress();

            String sql = String.format("INSERT INTO person VALUES ('%s',ARRAY['%s']::roles[],'%s','%s','%s','%s',TO_DATE('%s','DD/MM/YYYY'),'%s','%s',NULL);", email, role, firstName, lastName, pwd, tax_code, date, phone, address);
            System.out.println(sql);

        }

    }

    /**
     * Parses an SQL file removing all the comments
     *
     * @param filepath file to parse
     * @return collection of valid SQL statements
     * @throws FileNotFoundException thrown if a problem occurs while reading the file
     */
    private static ArrayList<String> parseSQL(String filepath) throws FileNotFoundException {

        //List of statements
        ArrayList<String> statements = new ArrayList<>();

        //Open the given file
        File myObj = new File(filepath);
        Scanner myReader = new Scanner(myObj);
        StringBuilder statement = null;

        while (myReader.hasNextLine()) {

            //Create the statement if null
            if (statement == null) {
                statement = new StringBuilder();
            }

            //Read one line
            String data = myReader.nextLine();

            //Skip empty lines, blank lines and comment lines that start with --
            boolean skip = data.isEmpty() || data.isBlank() || data.startsWith("--");

            if (!skip) {
                //Detect if is the end of a statement
                if (data.endsWith(";")) {
                    //End this statement and remove the last ;
                    statement.append(data, 0, data.length() - 1);
                    //Delete multiple whitespaces
                    String wiped = statement.toString().replaceAll("\\s+", " ").trim();
                    //Add it to the statements
                    statements.add(wiped);
                    //Start a new statement
                    statement = null;
                } else {
                    statement.append(data);
                }
            }

        }

        return statements;
    }

}
