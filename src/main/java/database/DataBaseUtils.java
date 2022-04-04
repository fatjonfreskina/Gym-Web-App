package database;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

    public static void main(String[] args) throws SQLException, IOException {

        System.out.println("COMMAND LINE TOOL FOR DATABASE MANAGEMENT, USE:");
        System.out.println("* 1     - TO CREATE THE DATABASE");
        System.out.println("* 2     - TO CREATE THE DATABASE TABLES");
        System.out.println("* 3     - TO SEED THE DATABASE TABLES");
        System.out.println("* 4     - TO LOAD FAKE VALUES IN THE TABLES");
        System.out.println("* 5     - TO DROP DATABASE");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int option = Integer.parseInt(reader.readLine());

        //TODO: implement switch case

        //createDatabase();
        //createDatabaseTables();
        //seedDatabase();
        //fakeDataDatabase();
        //dropDatabase();
    }

    private static void createDatabase() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_ROBOT, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(CREATE_DATABASE_FILEPATH);
        for(String statement:statements){
            stmt.execute(statement);
        }
    }

    private static void createDatabaseTables() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_GWA, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(CREATE_TABLES_FILEPATH);
        for(String statement:statements){
            stmt.execute(statement);
        }
    }

    private static void seedDatabase() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_GWA, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(SEED_DATABASE_FILEPATH);
        for(String statement:statements){
            stmt.execute(statement);
        }
    }

    private static void fakeDataDatabase() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_GWA, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(FAKE_DATABASE_FILEPATH);
        for(String statement:statements){
            stmt.execute(statement);
        }
    }

    private static void dropDatabase() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(DB_ROBOT, USER, PASS);
        Statement stmt = conn.createStatement();
        List<String> statements = parseSQL(DROP_DATABASE_FILEPATH);
        for(String statement:statements){
            stmt.execute(statement);
            System.out.println(statement);
        }
    }


    /**
     * Creates some users
     * @param faker faker instance
     * @param numberOfUsers number of users you want to create
     */
    private static void createUsers(Faker faker, int numberOfUsers){

        String[] roles = {"Trainee","Trainer", "Secretary"};

        Random r = new Random();

        for (int i=0; i<numberOfUsers; i++) {

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

            String avatar_url = faker.avatar().image();
            //TODO: download the avatar
            //TODO: save the downloaded file into the assets
            //TODO: set the path to the downloaded file
            //TODO: if one of the firt two passages fails, then set the avatar to null

            String sql = String.format("INSERT INTO person VALUES ('%s',ARRAY['%s']::roles[],'%s','%s','%s','%s',TO_DATE('%s','DD/MM/YYYY'),'%s','%s',NULL);",email,role,firstName,lastName,pwd,tax_code,date,phone,address);
            System.out.println(sql);
            //TODO: use the DAO to perform the insert (when ready)

        }

    }

    private static void createMedicalCertificate(){
        //TODO: create a medical certificate for the inserted user
        throw new UnsupportedOperationException("Still to be implemented");
    }

    /**
     * Parses an SQL file removing all the comments
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
            if(statement == null){
                statement = new StringBuilder();
            }

            //Read one line
            String data = myReader.nextLine();

            //Skip empty lines, blank lines and comment lines that start with --
            boolean skip = data.isEmpty() || data.isBlank() || data.startsWith("--");

            if(!skip) {
                //Detect if is the end of a statement
                if(data.endsWith(";")){
                    //End this statement and remove the last ;
                    statement.append(data, 0, data.length()-1);
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
