package it.unipd.dei.webapp;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Class that can be used to populate the tables of the WebApp
 * using the javafaker library
*/
public class Populator {

    /** Populate tables using javafaker and the DAO classes
     */
    public static void main(String[] args){

        Faker faker = new Faker();

        // Create the users
        Populator.createUsers(faker, 16);

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

    //TODO: define appropriately the following methods

    /**
     * Create the tables
     */
    public void migrate(){
    }

    /**
     * Populate the tables
     */
    public void populate(){
    }

    /**
     * Delete data from the tables
     */
    public void delete(){
    }

}
