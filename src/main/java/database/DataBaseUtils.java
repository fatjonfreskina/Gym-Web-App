package database;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DataBaseUtils {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/gwa_db";
    static final String USER = "robot";
    static final String PASS = "robot";

    static final String CREATE_FILEPATH = "src/main/database/create.sql";
    static final String SEED_FILEPATH = "src/main/database/seed.sql";

    public static void main(String[] args){

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Creates the database
            createDatabase(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Initializes the database
     * @param conn JDBC connection
     */
    private static void createDatabase(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            List<String> statements = parseSQL(CREATE_FILEPATH);
            for(String statement:statements){
                stmt.execute(statement);
                System.out.println(statement);
            }
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
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
