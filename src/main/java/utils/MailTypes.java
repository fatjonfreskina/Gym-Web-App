package utils;
import resource.*;

public class MailTypes {
/*
    Some Example Beans for testing the emails format

    public static void main(String args[]){

        Person person = new Person("mail", "Pippo", "Pluto","--","123" , new Date(1999,6,9),"","","");
        Subscription subscription = new Subscription(123,"Yoga",6, new Date(2022,6,15), 0, "Rodolfo");
        PasswordReset passwordReset = new PasswordReset("magiclink", new Timestamp(111), "Simone D'Antimo");
        LectureTimeSlot lectureTimeSlot = new LectureTimeSlot("--", new Date(2022,9,11), new Time(9,00,00), 2022,"Yoga", "Gianni");
        System.out.println(mailForRegistration(person) + "\n\n");
        System.out.println(mailForSubscriptionToCourse(person,subscription) + "\n\n");
        System.out.println(mailForPasswordChanged(person,passwordReset) + "\n\n");
        System.out.println(mailForMedicalCertificateUploaded(person));
        System.out.println(mailForCancellationLecture(person,lectureTimeSlot));
        System.out.println(mailForMedicalCertificateExpiring(person,new MedicalCertificate("Sim",new Date(2022,9,11),"Rossi","--")));
        System.out.println(mailForTrainerChanged(person,lectureTimeSlot));
    }
 */
    private static final String HOST = "ssl0.ovh.net";
    private static final int PORT = 465;
    private static final String FROM_EMAIL = "test@projectzero.me";
    private static final String FROM_PASSWORD = "RgrfDS34678@fgreq.few73";
    private static final MailManager MANAGER = new MailManager(HOST, PORT, FROM_EMAIL, FROM_PASSWORD);

    //This mail is sent when someone registers for the first time in our website
    public static boolean mailForRegistration(Person person) {

        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" +
                "\n\n" +
                "we inform you that you've been successfully registered to our gym" +
                "\n\nKind regards,\n" +
                "The Gwa Team";
        //return emailContent;
        try {
            MANAGER.sendMail(person.getEmail(), "GWA Registration Confirmed", emailContent);
            return true;

        } catch (Exception e) {
            System.out.println("Cannot send email");
        }
        return false;
    }

    //This mail is sent when someone subscribed for a course in our gym (to the secretary)
    public static boolean mailForSubscriptionToCourse(Person person, Subscription subscription){

        String emailContent = "Dear "+person.getName()+" "+person.getSurname()+",\n"+
                "\n\n" +
                "we inform you that your subscription to " + subscription.getCourseName() +
                " course is confirmed, you can pay at the gym next time you will come" +
                "\n\nKind regards,\n"+
                "The Gwa Team";

        //return emailContent;
        try {
            MANAGER.sendMail(person.getEmail(), "GWA subscription confirmed", emailContent);
            return true;

        } catch (Exception e) {
            System.out.println("Cannot send email");
        }
        return false;
    }
    // This mail is sent when someone requests for a password changes
    public static boolean mailForPasswordChanged(Person person, PasswordReset passwordReset){

        String emailContent = "Dear " + person.getName() + " " + person.getSurname()+",\n"+
                "\n\n" +
                "we inform you that we received a request to change your password." +
                "\n If you ask for this, please, click the link below. Otherwise ignore this email\n"
                + passwordReset.getToken() +
                "\nthe link will expire in 30 minutes" +
                "\n\nKind regards,\n"+
                "The Gwa Team";

        //return emailContent;
        try {
            MANAGER.sendMail(person.getEmail(), "GWA Password reset request", emailContent);
            return true;

        } catch (Exception e) {
            System.out.println("Cannot send email");
        }
        return false;
    }
    //This mail is sent when someone Uploads a medical certificate
    public static boolean mailForMedicalCertificateUploaded(Person person){

        String emailContent = "Dear "+person.getName()+" "+person.getSurname()+",\n"+
                "\n\n" +
                "we inform you that we have received your Medical Certificate" +
                "\n\nKind regards,\n"+
                "The Gwa Team";

        //return emailContent;
        try {
            MANAGER.sendMail(person.getEmail(), "GWA Medical certificate uploaded", emailContent);
            return true;

        } catch (Exception e) {
            System.out.println("Cannot send email");
        }
        return false;
    }

    public static boolean mailForMedicalCertificateExpiring(Person person, MedicalCertificate medicalCertificate){

        String emailContent = "Dear "+person.getName()+" "+person.getSurname()+
                "\n\n" +
                "we inform you that your medical certificate is expiring in date " + medicalCertificate.getExpirationDate() +
                " we ask you to provide a new medical certificate before the expiration date, otherwise you will not be " +
                "able to attending courses" +
                "\n\nKind regards,\n"+
                "The Gwa Team";
        //return emailContent;
        try {
            MANAGER.sendMail(person.getEmail(), "GWA Medical certificate expiring", emailContent);
            return true;

        } catch (Exception e) {
            System.out.println("Cannot send email");
        }
        return false;
    }
    //If a Trainer will be substituted
    public static boolean mailForTrainerChanged(Person trainee, LectureTimeSlot lectureTimeSlot){

        String emailContent = "Dear "+trainee.getName()+" "+trainee.getSurname()+",\n"+
                "\n\n" +
                "we inform you that the lecture of " + lectureTimeSlot.getCourseName() +
                " course , in date " + lectureTimeSlot.getDate() + " at " + lectureTimeSlot.getStartTime() + " will be held by "
                + lectureTimeSlot.getSubstitution() +
                "\n\nKind regards,\n"+
                "The Gwa Team";

        //return emailContent;
        try {
            MANAGER.sendMail(trainee.getEmail(), "GWA Substitutions in a course", emailContent);
            return true;

        } catch (Exception e) {
            System.out.println("Cannot send email");
        }
        return false;
    }
    //mail if lecture is removed
    public static boolean mailForCancellationLecture(Person person, LectureTimeSlot lectureTimeSlot){
        String emailContent = "Dear "+person.getName()+" "+person.getSurname()+",\n"+
                "\n\n" +
                "we inform you that the lecture of " + lectureTimeSlot.getCourseName() +
                " in date " + lectureTimeSlot.getDate() + " at " + lectureTimeSlot.getStartTime() +
                " is cancelled. We really apologize for the inconvenient" +
                "\n\nKind regards,\n"+
                "The Gwa Team";

        //return emailContent;
        try {
            MANAGER.sendMail(person.getEmail(), "GWA Lecture cancelled", emailContent);
            return true;

        } catch (Exception e) {
            System.out.println("Cannot send email");
        }
        return false;
    }

    //Still in Beta
    public static String mailForConfirmedRegistration(){
        return "";
    }

}
