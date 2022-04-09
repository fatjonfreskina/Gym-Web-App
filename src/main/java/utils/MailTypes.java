package utils;

import constants.Constants;
import jakarta.mail.MessagingException;
import resource.*;

import java.security.NoSuchAlgorithmException;

/**
 * @author Simone D'Antimo
 * @author Franceco Caldivezzi
 * @author Riccardo Forzan
 */
public class MailTypes {

    //TODO: Include Multipart for formatting mails in HTML

    private static final String HOST = "ssl0.ovh.net";
    private static final int PORT = 465;
    private static final String FROM_EMAIL = "test@projectzero.me";
    private static final String FROM_PASSWORD = "RgrfDS34678@fgreq.few73";
    private static final MailManager MANAGER = new MailManager(HOST, PORT, FROM_EMAIL, FROM_PASSWORD);

    /**
     * Mail sent to confirm the account
     * @param person recipient of the message
     * @throws MessagingException thrown if there is an error while sending
     */
    public static void registrationConfirmed(Person person) throws MessagingException {

        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" + "\n\n" + "we inform you that you've been successfully registered to our gym" + "\n\nKind regards,\n" + "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Registration Confirmed", emailContent);
    }

    /**
     * Mail sent to confirm the account
     * @param person recipient of the message
     * @throws MessagingException thrown if there is an error while sending
     * @throws NoSuchAlgorithmException thrown if there is an error while calculating the token
     */
    public static void mailForConfirmRegistration(Person person) throws MessagingException, NoSuchAlgorithmException {

        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" + "\n\n" + "we inform you that you've been successfully registered to our gym\n" + "please click this:\n" + Constants.CONFIRMATION_URL + EncryptionManager.encrypt(person.getEmail()) + "\n\nKind regards,\n" + "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Registration Confirmed", emailContent);
    }

    /**
     * This mail is sent when someone subscribed for a course in our gym (to the secretary)
     * @param person recipient of the message
     * @param person recipient of the message
     * @param subscription
     * @throws MessagingException thrown if there is an error while sending
     */
    public static void mailForSubscriptionToCourse(Person person, Subscription subscription) throws MessagingException {

        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" + "\n\n" + "we inform you that your subscription to " + subscription.getCourseName() + " course is confirmed, you can pay at the gym next time you will come" + "\n\nKind regards,\n" + "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA subscription confirmed", emailContent);
    }

    /**
     * This mail is sent when someone requests for a password changes
     * @param person recipient of the message
     * @param passwordReset password reset token to be sent
     * @throws MessagingException thrown if there is an error while sending
     */
    public static void mailForPasswordChanges(Person person, PasswordReset passwordReset) throws MessagingException {

        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" + "\n\n" + "we inform you that we received a request to change your password." + "\nIf you ask for this, please, click the link below. Otherwise ignore this email\n" + Constants.PASSWORD_CHANGE_URL + passwordReset.getToken() + "\nthe link will expire in 30 minutes" + "\n\nKind regards,\n" + "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Password reset request", emailContent);
    }

    /**
     * This mail is sent when someone has a new a medical certificate inserted
     * @param person recipient of the message
     * @throws MessagingException thrown if there is an error while sending
     */
    public static void mailForMedicalCertificateUploaded(Person person) throws MessagingException {

        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" + "\n\n" + "we inform you that we have received your Medical Certificate" + "\n\nKind regards,\n" + "The Gwa Team";
        MANAGER.sendMail(person.getEmail(), "GWA Medical certificate uploaded", emailContent);
    }

    /**
     * This mail is sent when the medical certificate is expiring
     * @param person recipient of the message
     * @param medicalCertificate medical certificate near to expiration date
     * @throws MessagingException thrown if there is an error while sending
     */
    public static void mailForMedicalCertificateExpiring(Person person, MedicalCertificate medicalCertificate) throws MessagingException {

        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + "\n\n" + "we inform you that your medical certificate is expiring in date " + medicalCertificate.getExpirationDate() + " we ask you to provide a new medical certificate before the expiration date, otherwise you will not be " + "able to attending courses" + "\n\nKind regards,\n" + "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Medical certificate expiring", emailContent);
    }

    /**
     * This mail is sent when a lecture is deleted
     * @param person recipient of the message
     * @param lectureTimeSlot lecture time slot deleted
     * @throws MessagingException
     */
    public static void mailForCancellationLecture(Person person, LectureTimeSlot lectureTimeSlot) throws MessagingException {
        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" + "\n\n" + "we inform you that the lecture of " + lectureTimeSlot.getCourseName() + " in date " + lectureTimeSlot.getDate() + " at " + lectureTimeSlot.getStartTime() + " is cancelled. We really apologize for the inconvenient" + "\n\nKind regards,\n" + "The Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Lecture cancelled", emailContent);
    }

    /**
     * This mail is sent when a substitution for a trainer has been specified
     * @param person recipient of the message
     * @param lectureTimeSlot lecture time slot modified
     * @param motivation more details can be provided by the secretary
     * @throws MessagingException
     */
    public static void mailForTrainerChanged(Person person, LectureTimeSlot lectureTimeSlot, String motivation) throws MessagingException {
        String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n" + "\n\n" + "we inform you that the lecture of " + lectureTimeSlot.getCourseName() + " in date " + lectureTimeSlot.getDate() + " at " + lectureTimeSlot.getStartTime() + " is cancelled\n";
        if(motivation != null) {
            emailContent += motivation;
        }
        emailContent += "We really apologize for the inconvenient" + "\n\nKind regards,\n" + "The Gwa Team";
        MANAGER.sendMail(person.getEmail(), "GWA Lecture cancelled", emailContent);
    }

}
