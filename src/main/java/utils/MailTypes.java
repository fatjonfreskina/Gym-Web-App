package utils;
import constants.Constants;
import jakarta.mail.MessagingException;
import resource.*;

import java.security.NoSuchAlgorithmException;

public class MailTypes {

    /**
     * @TODO Include Multipart for formatting mails in HTML
     *
     * Setup the mail manager with the correct parameters.
     * @param host The SMTP host used in order to send the message
     * @param port The SMTP host port used in order to send the message
     * @param email The e-mail address of the sender
     * @param password The password of the sender's e-mail address
     *
     *  @method registrationConfirmed : If a registration go well
     *  @method mailForConfirmRegistration : Sent when a registration request comes
     *  @method mailForSubscriptionToCourse : When a secretary register someone to a course
     *  @method mailForPasswordChanges : When someone request for a password reset, todo: add token
     *  @method mailForMedicalCertificateUploaded : When a secretary register a valid medical certificate
     *  @method mailForMedicalCertificateExpiring : When a medical certificate is expiring
     *  @method mailForTrainerChanged : When a lesson will be held by a substitute (Substitute must be insert in the DB before)
     *  @method mailForCancellationLecture : When a lesson has been cancelled
     */

    private static final String HOST = "ssl0.ovh.net";
    private static final int PORT = 465;
    private static final String FROM_EMAIL = "test@projectzero.me";
    private static final String FROM_PASSWORD = "RgrfDS34678@fgreq.few73";
    private static final MailManager MANAGER = new MailManager(HOST, PORT, FROM_EMAIL, FROM_PASSWORD);

    //This mail is sent when someone registers for the first time in our website
    //Include the bean of the person registered

    public static void registrationConfirmed(Person person) throws MessagingException{

        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" +
                "\n\n" +
                "we inform you that you've been successfully registered to our gym" +
                "\n\nKind regards,\n" +
                "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Registration Confirmed", emailContent);
    }

    public static void mailForConfirmRegistration(Person person) throws MessagingException, NoSuchAlgorithmException {

        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" +
                "\n\n" +
                "we inform you that you've been successfully registered to our gym\n" +
                "please click this:\n" +
                Constants.CONFIRMATION_URL + EncryptionManager.encrypt(person.getEmail()) +
                "\n\nKind regards,\n" +
                "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Registration Confirmed", emailContent);
    }

    //This mail is sent when someone subscribed for a course in our gym (to the secretary)
    public static void mailForSubscriptionToCourse(Person person, Subscription subscription) throws MessagingException {

        String emailContent = "Dear "+person.getName()+" "+person.getSurname()+",\n"+
                "\n\n" +
                "we inform you that your subscription to " + subscription.getCourseName() +
                " course is confirmed, you can pay at the gym next time you will come" +
                "\n\nKind regards,\n"+
                "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA subscription confirmed", emailContent);
    }

    // This mail is sent when someone requests for a password changes
    public static void mailForPasswordChanges(Person person, PasswordReset passwordReset) throws MessagingException {

        String emailContent = "Dear " + person.getName() + " " + person.getSurname()+",\n"+
                "\n\n" +
                "we inform you that we received a request to change your password." +
                "\n If you ask for this, please, click the link below. Otherwise ignore this email\n"
                + passwordReset.getToken() +
                "\nthe link will expire in 30 minutes" +
                "\n\nKind regards,\n"+
                "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Password reset request", emailContent);
    }

    //This mail is sent when someone Uploads a medical certificate
    public static void mailForMedicalCertificateUploaded(Person person) throws MessagingException {

        String emailContent = "Dear "+person.getName()+" "+person.getSurname()+",\n"+
                "\n\n" +
                "we inform you that we have received your Medical Certificate" +
                "\n\nKind regards,\n"+
                "The Gwa Team";
        MANAGER.sendMail(person.getEmail(), "GWA Medical certificate uploaded", emailContent);
    }

    public static void mailForMedicalCertificateExpiring(Person person, MedicalCertificate medicalCertificate) throws MessagingException {

        String emailContent = "Dear "+person.getName()+" "+person.getSurname()+
                "\n\n" +
                "we inform you that your medical certificate is expiring in date " + medicalCertificate.getExpirationDate() +
                " we ask you to provide a new medical certificate before the expiration date, otherwise you will not be " +
                "able to attending courses" +
                "\n\nKind regards,\n"+
                "The Gwa Team";

            MANAGER.sendMail(person.getEmail(), "GWA Medical certificate expiring", emailContent);
    }

    //If a Trainer will be substituted
    public static void mailForTrainerChanged(Person trainee, LectureTimeSlot lectureTimeSlot) throws MessagingException {

        String emailContent = "Dear "+trainee.getName()+" "+trainee.getSurname()+",\n"+
                "\n\n" +
                "we inform you that the lecture of " + lectureTimeSlot.getCourseName() +
                " course , in date " + lectureTimeSlot.getDate() + " at " + lectureTimeSlot.getStartTime() + " will be held by "
                + lectureTimeSlot.getSubstitution() +
                "\n\nKind regards,\n"+
                "The Gwa Team";

        MANAGER.sendMail(trainee.getEmail(), "GWA Substitutions in a course", emailContent);
    }

    //mail if lecture is removed
    public static void mailForCancellationLecture(Person person, LectureTimeSlot lectureTimeSlot) throws MessagingException {
        String emailContent = "Dear "+person.getName()+" "+person.getSurname()+",\n"+
                "\n\n" +
                "we inform you that the lecture of " + lectureTimeSlot.getCourseName() +
                " in date " + lectureTimeSlot.getDate() + " at " + lectureTimeSlot.getStartTime() +
                " is cancelled. We really apologize for the inconvenient" +
                "\n\nKind regards,\n"+
                "The Gwa Team";

            MANAGER.sendMail(person.getEmail(), "GWA Lecture cancelled", emailContent);
    }
}
