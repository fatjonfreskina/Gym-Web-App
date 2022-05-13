package constants;

import java.util.regex.Pattern;

/**
 * Class that contains all constant values used across the whole application
 */
public class Constants {

    /**
     * Datasource of the application
     */
    public static final String DATASOURCE = "java:/comp/env/jdbc/gwa-res";

    /**
     * URL that corresponds to the login page
     */
    public static final String RELATIVE_URL_LOGIN = "/login";

    /**
     * URL that corresponds to the personal info of the user
     */
    public static final String RELATIVE_URL_PERSONALINFO = "/personal_info";

    /**
     * URL that corresponds to the homepage
     */
    public static final String RELATIVE_URL_HOME = "/";

    /**
     * URL that corresponds to the trainer attendance
     */
    public static final String RELATIVE_URL_TRAINER_MANAGE_ATTENDANCE = "/trainer/attendance";

    /**
     * URL parameter for adding a week
     */
    public static final String URL_PARAMETER_TRAINER_HOME_PAGE_ADD_WEEKS = "addWeeks";

    /**
     * Path to the gym JSP page
     */
    public static final String PATH_THE_GYM = "/jsp/thegym.jsp";

    /**
     * Path to prices JSP page
     */
    public static final String PATH_PRICES = "/jsp/prices.jsp";

    /**
     * Path to register JSP page
     */
    public static final String PATH_REGISTER = "/jsp/access/register.jsp";

    /**
     * Path to contact us JSP page
     */
    public static final String PATH_CONTACT_US = "/jsp/contactus.jsp";

    /**
     * Path to calendar JSP page
     */
    public static final String PATH_CALENDAR = "/jsp/calendar.jsp";

    /**
     * Path to confirmed registration JSP page
     */
    public static final String PATH_CONFIRMED_REGISTRATION = "/jsp/access/confirmed_registration.jsp";

    /**
     * Path to confirm registration JSP page
     */
    public static final String PATH_CONFIRM_REGISTRATION = "/jsp/access/confirm_registration.jsp";

    /**
     * Path to login JSP page
     */
    public static final String PATH_LOGIN = "/jsp/access/login.jsp";

    /**
     * Path to courses JSP page
     */
    public static final String PATH_COURSES = "/jsp/courses.jsp";

    /**
     * Path to staff JSP page
     */
    public static final String PATH_STAFF = "/jsp/staff.jsp";

    /**
     * Path to roles JSP page
     */
    public static final String PATH_ROLES = "/jsp/access/roles.jsp";

    /**
     * Path to unauthorized JSP page
     */
    public static final String PATH_UNAUTHORIZED = "/jsp/access/unauthorized.jsp";

    /**
     * Path for the trainee JSP page
     */
    public static final String PATH_TRAINEE = "/jsp/trainee/trainee.jsp";

    /**
     * Path for password forgot JSP page
     */
    public static final String PATH_PASSWORD_FORGOT = "/jsp/auth/password_forgot.jsp";

    /**
     * Path for password change JSP page
     */
    public static final String PATH_PASSWORD_CHANGE = "/jsp/auth/password_change.jsp";

    /**
     * Path for secretary's home JSP page
     */
    public static final String PATH_SECRETARY_HOME = "/jsp/secretary/home.jsp";

    /**
     * Path for secretary's add courses JSP page
     */
    public static final String PATH_SECRETARY_ADD_COURSES = "/jsp/secretary/addcourses.jsp";

    /**
     * Path for secretary's manage subscriptions JSP page
     */
    public static final String PATH_SECRETARY_MANAGES_SUBSCRIPTION = "/jsp/secretary/managesubscription.jsp";

    /**
     * Path for secretary's add account JSP page
     */
    public static final String PATH_SECRETARY_ADD_ACCOUNT = "/jsp/secretary/addaccount.jsp";

    /**
     * Path for secretary's role management JSP page
     */
    public static final String PATH_SECRETARY_MANAGE_ROLES = "/jsp/secretary/manageroles.jsp";

    /**
     * Path for secretary's add medical certificate JSP page
     */
    public static final String PATH_SECRETARY_ADD_CERTIFICATE = "/jsp/secretary/addmedicalcertificate.jsp";

    /**
     * Path for trainer's home JSP page
     */
    public static final String PATH_TRAINER_HOME = "/jsp/trainer/home.jsp";

    /**
     * Path for trainer's manage attendance JSP page
     */
    public static final String PATH_TRAINER_MANAGE_ATTENDANCE = "/jsp/trainer/manageAttendance.jsp";

    /**
     * Path for user's personal JSP page
     */
    public static final String PATH_PERSONALINFO = "/jsp/personalinfo.jsp";

    /**
     * Email address that would be ideally used to send emails from our jobs
     */
    public static final String NOREPLYEMAIL = "noreply@gwa.com";

    /**
     *
     */
    public static final String MAILSMTPHOST = "mail.smtp.host";

    /**
     * Name of this host
     */
    public static final String HOST = "localhost";

    /**
     * Template URL for confirmation
     */
    public static final String CONFIRMATION_URL = "http://localhost:8080/wa2122-gwa/confirm_registration?token=";

    /**
     * Template URL for changing password
     */
    public static final String PASSWORD_CHANGE_URL = "http://localhost:8080/wa2122-gwa/password_change?token=";

    /**
     * Name of the parameter passed using GET or POST method for the field tax code
     */
    public static final String TAX_CODE = "tax_code";

    /**
     * Name of the parameter passed using GET or POST method for the field first name
     */
    public static final String FIRST_NAME = "first_name";

    /**
     * Name of the parameter passed using GET or POST method for the field last name
     */
    public static final String LAST_NAME = "last_name";

    /**
     * Name of the parameter passed using GET or POST method for the field birth date
     */
    public static final String BIRTH_DATE = "birth_date";

    /**
     * Name of the parameter passed using GET or POST method for the field address
     */
    public static final String ADDRESS = "address";

    /**
     * Name of the parameter passed using GET or POST method for the field telephone number
     */
    public static final String TELEPHONE_NUMBER = "telephone_number";

    /**
     * Name of the parameter passed using GET or POST method for the field avatar
     */
    public static final String AVATAR = "avatar";

    /**
     * Name of the parameter passed using GET or POST method for the field email
     */
    public static final String EMAIL = "email";

    /**
     * Name of the parameter passed using GET or POST method for the field password
     */
    public static final String PASSWORD = "password";

    /**
     * Name of the parameter passed using GET or POST method for the field confirm password
     */
    public static final String CONFIRM_PASSWORD = "confirm_password";

    /**
     * Name of the parameter passed using GET or POST method for the field medical certificate
     */
    public static final String MEDICAL_CERTIFICATE = "medical_certificate";

    /**
     * Name of the parameter passed using GET or POST method for the field message
     */
    public static final String MESSAGE = "message";

    /**
     * Name of the parameter passed using GET or POST method for the field person
     */
    public static final String EMAILCONFIRMATION_PERSON = "person";

    /**
     * Name of the parameter passed using GET or POST method for the field token
     */
    public static final String EMAILCONFIRMATION_TOKEN = "token";

    /**
     * Name of the parameter passed using GET or POST method for the field expiration date
     */
    public static final String EMAILCONFIRMATION_EXPIRATIONDATE = "expirationdate";

    /**
     * Name of the parameter passed using GET or POST method for the field role
     */
    public static final String PERSONROLES_ROLE = "role";

    /**
     * Person's object field name for email attribute
     */
    public static final String PERSON_EMAIL = "email";

    /**
     * Person's object field name for name attribute
     */
    public static final String PERSON_NAME = "name";

    /**
     * Person's object field name for surname attribute
     */
    public static final String PERSON_SURNAME = "surname";

    /**
     * Person's object field name for password attribute
     */
    public static final String PERSON_PSW = "psw";

    /**
     * Person's object field name for taxcode attribute
     */
    public static final String PERSON_TAXCODE = "taxcode";

    /**
     * Person's object field name for birth date attribute
     */
    public static final String PERSON_BIRTHDATE = "birthdate";

    /**
     * Person's object field name for telephone attribute
     */
    public static final String PERSON_TELEPHONE = "telephone";

    /**
     * Person's object field name for address attribute
     */
    public static final String PERSON_ADDRESS = "address";

    /**
     * Person's object field name for avatar path attribute
     */
    public static final String PERSON_AVATARPATH = "avatarpath";

    /**
     * PasswordReset's object field name for token attribute
     */
    public static final String PASSWORDRESET_TOKEN = "token";

    /**
     * PasswordReset's object field name for expiration date attribute
     */
    public static final String PASSWORDRESET_EXPIRATIONDATE = "expirationdate";

    /**
     * PasswordReset's object field name for person attribute
     */
    public static final String PASSWORDRESET_PERSON = "person";

    /**
     * MedicalCertificate's object field name for person attribute
     */
    public static final String MEDICALCERTIFICATE_PERSON = "person";

    /**
     * MedicalCertificate's object field name for expiration date attribute
     */
    public static final String MEDICALCERTIFICATE_EXPIRATIONDATE = "expirationdate";

    /**
     * MedicalCertificate's object field name for doctor name attribute
     */
    public static final String MEDICALCERTIFICATE_DOCTORNAME = "doctorname";

    /**
     * MedicalCertificate's object field name for path attribute
     */
    public static final String MEDICALCERTIFICATE_PATH = "path";

    /**
     * Reservation's object field name for trainee attribute
     */
    public static final String RESERVATION_TRAINEE = "trainee";

    /**
     * Reservation's object field name for lecture room attribute
     */
    public static final String RESERVATION_LECTUREROOM = "lectureroom";

    /**
     * Reservation's object field name for lecture date attribute
     */
    public static final String RESERVATION_LECTUREDATE = "lecturedate";

    /**
     * Reservation's object field name for lecture start time attribute
     */
    public static final String RESERVATION_LECTURESTARTTIME = "lecturestarttime";

    /**
     * Teaches' object field name for course edition id attribute
     */
    public static final String TEACHES_COURSEEDITIONID = "courseeditionid";

    /**
     * Teaches' object field name for course name attribute
     */
    public static final String TEACHES_COURSENAME = "coursename";

    /**
     * Teaches' object field name for trainer attribute
     */
    public static final String TEACHES_TRAINER = "trainer";

    /**
     * Room's object field name for name attribute
     */
    public static final String ROOM_NAME = "name";

    /**
     * Room's object field name for slots attribute
     */
    public static final String ROOM_SLOTS = "slots";

    /**
     * LectureTimeSlot's object field name for room name attribute
     */
    public static final String LECTURETIMESLOT_ROOMNAME = "roomname";

    /**
     * LectureTimeSlot's object field name for date attribute
     */
    public static final String LECTURETIMESLOT_DATE = "date";

    /**
     * LectureTimeSlot's object field name for start time attribute
     */
    public static final String LECTURETIMESLOT_STARTTIME = "starttime";

    /**
     * LectureTimeSlot's object field name for course edition id attribute
     */
    public static final String LECTURETIMESLOT_COURSEEDITIONID = "courseeditionid";

    /**
     * LectureTimeSlot's object field name for course name attribute
     */
    public static final String LECTURETIMESLOT_COURSENAME = "coursename";

    /**
     * LectureTimeSlot's object field name for substitution attribute
     */
    public static final String LECTURETIMESLOT_SUBSTITUTION = "substitution";

    /**
     * CourseEdition's object field name for id attribute
     */
    public static final String COURSEEDITION_ID = "id";

    /**
     * CourseEdition's object field name for name attribute
     */
    public static final String COURSEEDITION_NAME = "name";

    /**
     * Course's object field name for name attribute
     */
    public static final String COURSE_NAME = "name";

    /**
     * Course's object field name for description attribute
     */
    public static final String COURSE_DESCRIPTION = "description";

    /**
     * SubscriptionType's object field name for course edition id attribute
     */
    public static final String SUBSCRIPTIONTYPE_COURSEEDITIONID = "courseeditionid";

    /**
     * SubscriptionType's object field name for course name id attribute
     */
    public static final String SUBSCRIPTIONTYPE_COURSENAME = "coursename";

    /**
     * SubscriptionType's object field name for duration attribute
     */
    public static final String SUBSCRIPTIONTYPE_DURATION = "duration";

    /**
     * SubscriptionType's object field name for cost attribute
     */
    public static final String SUBSCRIPTIONTYPE_COST = "cost";

    /**
     * Subscription's object field name for course edition id attribute
     */
    public static final String SUBSCRIPTION_COURSEEDITIONID = "courseeditionid";

    /**
     * Subscription's object field name for course name attribute
     */
    public static final String SUBSCRIPTION_COURSENAME = "coursename";

    /**
     * Subscription's object field name for duration attribute
     */
    public static final String SUBSCRIPTION_DURATION = "duration";

    /**
     * Subscription's object field name for start day attribute
     */
    public static final String SUBSCRIPTION_STARTDAY = "startday";

    /**
     * Subscription's object field name for trainee attribute
     */
    public static final String SUBSCRIPTION_TRAINEE = "trainee";

    /**
     * Subscription's object field name for discount attribute
     */
    public static final String SUBSCRIPTION_DISCOUNT = "discount";

    /**
     * Path for storing avatars inside the application
     */
    public static final String AVATAR_PATH_FOLDER = "../gwa/avatars";

    /**
     * Path for storing medical certificates inside the application
     */
    public static final String MEDICAL_CERTIFICATE_PATH_FOLDER = "../gwa/medical_certificates";

    /**
     * Regex to specify which file name are accepted for avatar file
     */
    public static final Pattern ACCEPTED_AVATAR_FILENAME_REGEX = Pattern.compile(
            "^(.*)\\.(png|jpg|jpeg)$", Pattern.DOTALL);

    /**
     * Regex to specify which file name are accepted for medical certificate file
     */
    public static final Pattern ACCEPTED_MEDICAL_CERTIFICATE_FILENAME_REGEX = Pattern.compile(
            "^(.*)\\.(pdf)$", Pattern.DOTALL);

    /**
     * Duration of a day expressed in milliseconds
     */
    public static final long DAY = 1000 * 60 * 60 * 24;

    /**
     * JSON exception thrown when an error occurred in parsing time
     */
    public static final String UNPARSABLE_TIME = "Unparsable time: ";

    /**
     * JSON exception thrown when an error occurred in parsing date
     */
    public static final String UNPARSABLE_DATE = "Unparsable date: ";

}
