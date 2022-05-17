package utils;

import constants.Constants;
import jakarta.mail.MessagingException;
import resource.*;

import java.security.NoSuchAlgorithmException;

/**
 * Class used to handle the emails sent by the application
 *
 * @author Simone D'Antimo
 * @author Franceco Caldivezzi
 * @author Riccardo Forzan
 */
public class MailTypes {

    private static final String HOST = "ssl0.ovh.net";
    private static final int PORT = 465;
    private static final String FROM_EMAIL = "test@projectzero.me";
    private static final String FROM_PASSWORD = "RgrfDS34678@fgreq.few73";
    private static final MailManager MANAGER = new MailManager(HOST, PORT, FROM_EMAIL, FROM_PASSWORD);

    /**
     * Mail sent to confirm the account
     *
     * @param person recipient of the message
     * @throws MessagingException thrown if there is an error while sending
     */
    public static void registrationConfirmed(Person person) throws MessagingException {
        StringBuilder sb=new StringBuilder();
        sb.append("Dear ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append(",\n\n\nwe inform you that you've been successfully registered to our gym\n\nKind regards,\nThe Gwa Team");

        //String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n\n\nwe inform you that you've been successfully registered to our gym\n\nKind regards,\nThe Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Registration Confirmed", sb.toString());
    }

    /**
     * Mail sent to confirm the account
     *
     * @param person recipient of the message
     * @throws MessagingException       thrown if there is an error while sending
     * @throws NoSuchAlgorithmException thrown if there is an error while calculating the token
     */
    public static void mailForConfirmRegistration(Person person) throws MessagingException, NoSuchAlgorithmException {
        StringBuilder sb=new StringBuilder();
        sb.append("Dear ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append(",\n\n\nwe inform you that you've been successfully registered to our gym\nplease click this:\n");
        sb.append(Constants.CONFIRMATION_URL);
        sb.append(EncryptionManager.encrypt(person.getEmail()));
        sb.append("\n\nKind regards,\nThe Gwa Team");

        //String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n\n\nwe inform you that you've been successfully registered to our gym\nplease click this:\n" + Constants.CONFIRMATION_URL + EncryptionManager.encrypt(person.getEmail()) + "\n\nKind regards,\nThe Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Registration Confirmed", sb.toString());
    }

    /**
     * This mail is sent when someone subscribed for a course in our gym (to the secretary)
     *
     * @param person       recipient of the message
     * @param subscription subscription data to be sent
     * @throws MessagingException thrown if there is an error while sending
     */
    @SuppressWarnings("unused")
    public static void mailForSubscriptionToCourse(Person person, Subscription subscription) throws MessagingException {

        StringBuilder sb=new StringBuilder();
        sb.append("Dear ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append(",\n\n\nwe inform you that your subscription to ");
        sb.append(subscription.getCourseName());
        sb.append(" course is confirmed, you can pay at the gym next time you will come\n\nKind regards,\nThe Gwa Team");


        //String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n\n\nwe inform you that your subscription to " + subscription.getCourseName() + " course is confirmed, you can pay at the gym next time you will come\n\nKind regards,\nThe Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA subscription confirmed", sb.toString());
    }

    /**
     * This mail is sent when someone requests for a password changes
     *
     * @param person        recipient of the message
     * @param passwordReset password reset token to be sent
     * @throws MessagingException thrown if there is an error while sending
     */
    public static void mailForPasswordChanges(Person person, PasswordReset passwordReset) throws MessagingException {
        StringBuilder sb=new StringBuilder();
        sb.append("Dear ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append(",\n\n\nwe inform you that we received a request to change your password.\nIf you ask for this, please, click the link below. Otherwise ignore this email\n");
        sb.append(Constants.PASSWORD_CHANGE_URL);
        sb.append(passwordReset.getToken());
        sb.append("\nthe link will expire in 30 minutes\n\nKind regards,\nThe Gwa Team");

        //String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n\n\nwe inform you that we received a request to change your password.\nIf you ask for this, please, click the link below. Otherwise ignore this email\n" + Constants.PASSWORD_CHANGE_URL + passwordReset.getToken() + "\nthe link will expire in 30 minutes\n\nKind regards,\nThe Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Password reset request", sb.toString());
    }

    /**
     * This mail is sent when someone has a new a medical certificate inserted
     *
     * @param person recipient of the message
     * @throws MessagingException thrown if there is an error while sending
     */
    public static void mailForMedicalCertificateUploaded(Person person) throws MessagingException {
        StringBuilder sb=new StringBuilder();
        sb.append("Dear ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append(",\n\n\nwe inform you that we have received your Medical Certificate\n\nKind regards,\nThe Gwa Team");

        //String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n\n\nwe inform you that we have received your Medical Certificate\n\nKind regards,\nThe Gwa Team";
        MANAGER.sendMail(person.getEmail(), "GWA Medical certificate uploaded", sb.toString());
    }

    /**
     * This mail is sent when the medical certificate is expiring
     *
     * @param person             recipient of the message
     * @param medicalCertificate medical certificate near to expiration date
     * @throws MessagingException thrown if there is an error while sending
     */
    @SuppressWarnings("unused")
    public static void mailForMedicalCertificateExpiring(Person person, MedicalCertificate medicalCertificate) throws MessagingException {
        StringBuilder sb=new StringBuilder();
        sb.append("Dear ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append("\n\nwe inform you that your medical certificate is expiring in date ");
        sb.append(medicalCertificate.getExpirationDate());
        sb.append(" we ask you to provide a new medical certificate before the expiration date, otherwise you will not be able to attending courses\n\nKind regards,\nThe Gwa Team");

        //String emailContent = "Dear " + person.getName() + " " + person.getSurname() + "\n\nwe inform you that your medical certificate is expiring in date " + medicalCertificate.getExpirationDate() + " we ask you to provide a new medical certificate before the expiration date, otherwise you will not be able to attending courses\n\nKind regards,\nThe Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Medical certificate expiring", sb.toString());
    }

    /**
     * This mail is sent when a lecture is deleted
     *
     * @param person          recipient of the message
     * @param lectureTimeSlot lecture time slot deleted
     * @throws MessagingException thrown if there is an error while sending
     */
    @SuppressWarnings("unused")
    public static void mailForCancellationLecture(Person person, LectureTimeSlot lectureTimeSlot) throws MessagingException {
        StringBuilder sb=new StringBuilder();
        sb.append("Dear ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append(",\n\n\nwe inform you that the lecture of ");
        sb.append(lectureTimeSlot.getCourseName());
        sb.append(" in date ");
        sb.append(lectureTimeSlot.getDate());
        sb.append(" at ");
        sb.append(lectureTimeSlot.getStartTime());
        sb.append(" is cancelled. We really apologize for the inconvenient\n\nKind regards,\nThe Gwa Team");

       // String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n\n\nwe inform you that the lecture of " + lectureTimeSlot.getCourseName() + " in date " + lectureTimeSlot.getDate() + " at " + lectureTimeSlot.getStartTime() + " is cancelled. We really apologize for the inconvenient\n\nKind regards,\nThe Gwa Team";

        MANAGER.sendMail(person.getEmail(), "GWA Lecture cancelled", sb.toString());
    }

    /**
     * This mail is sent when a substitution for a trainer has been specified
     *
     * @param person          recipient of the message
     * @param lectureTimeSlot lecture time slot modified
     * @param motivation      more details can be provided by the secretary
     * @throws MessagingException thrown if there is an error while sending
     */
    @SuppressWarnings("unused")
    public static void mailForTrainerChanged(Person person, LectureTimeSlot lectureTimeSlot, String motivation) throws MessagingException {
        StringBuilder sb=new StringBuilder();
        sb.append("Dear ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append(",\n\n\nwe inform you that the trainer for the lecture of ");
        sb.append(lectureTimeSlot.getCourseName());
        sb.append(" in date ");
        sb.append(lectureTimeSlot.getDate());
        sb.append(" at ");
        sb.append(lectureTimeSlot.getStartTime());

        sb.append(" is substituted because:\n");
        if (motivation != null) {
            sb.append( motivation);
        }
        sb.append(".\nWe really apologize for the inconvenient\n\nKind regards,\nThe Gwa Team");



        /*String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n\n\nwe inform you that the lecture of " + lectureTimeSlot.getCourseName() + " in date " + lectureTimeSlot.getDate() + " at " + lectureTimeSlot.getStartTime() + " is cancelled\n";
        if (motivation != null) {
            emailContent += motivation;
        }
        emailContent += "\nWe really apologize for the inconvenient\n\nKind regards,\nThe Gwa Team";*/
        MANAGER.sendMail(person.getEmail(), "GWA Trainer Substitution", sb.toString());
    }

    /**
     * This mail is sent when a substitution for a trainer has been specified
     *
     * @param person          recipient of the message
     * @param lectureTimeSlot lecture time slot modified
     * @throws MessagingException thrown if there is an error while sending
     */
    public static void mailForLectureChanged(Person person, LectureTimeSlot lectureTimeSlot) throws MessagingException {
        StringBuilder sb=new StringBuilder();
        sb.append("Dear ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append(",\n\n\nwe inform you that the trainer for the lecture of ");
        sb.append(lectureTimeSlot.getCourseName());
        sb.append(" in date ");
        sb.append(lectureTimeSlot.getDate());
        sb.append(" at ");
        sb.append(lectureTimeSlot.getStartTime());
        sb.append(" is rescheduled.\nWe really apologize for the inconvenient\n\nKind regards,\nThe Gwa Team");

        /*String emailContent = "Dear " + person.getName() + " " + person.getSurname() + ",\n\n\nwe inform you that the lecture of " + lectureTimeSlot.getCourseName() + " in date " + lectureTimeSlot.getDate() + " at " + lectureTimeSlot.getStartTime() + " is rescheduled\n";
        emailContent += "\nWe really apologize for the inconvenient\n\nKind regards,\nThe Gwa Team";*/
        MANAGER.sendMail(person.getEmail(), "GWA Trainer Substitution", sb.toString());
    }
}