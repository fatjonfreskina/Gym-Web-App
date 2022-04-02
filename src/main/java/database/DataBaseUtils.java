package database;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DataBaseUtils {

    public static void main(String[] args){

        //Initialize the DataBase schema
        ArrayList<String> createStatements = null;
        try {
            createStatements = DataBaseUtils.ParseSQL("src/main/database/create.sql");
            //System.out.println(createStatements);

            //TODO: Retrieve a JDBC connection and execute the create statements
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        //Faker faker = new Faker();
        //Create users
        //DataBaseUtils.createUsers(faker, 16);

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
    private static ArrayList<String> ParseSQL(String filepath) throws FileNotFoundException {

        //List of statements
        ArrayList<String> statements = new ArrayList<>();

        //Print the current path
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);

        //Open the given file
        File myObj = new File(filepath);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            //Read one line
            String data = myReader.nextLine();
            //Skip empty lines, blank lines and comment lines that start with --
            boolean skip = data.isEmpty() || data.isBlank() || data.startsWith("--");

            if(!skip) {
                statements.add(data);
            }

        }

        return statements;
    }

}
