package constants;

public class Constants {

    public static final String DATASOURCE = "java:/comp/env/jdbc/gwa-res";

    //GENERAL APPLICATION PATHS
    public static final String RELATIVE_URL_LOGIN = "/login";
    public static final String RELATIVE_URL_LOGOUT = "/logout";
    public static final String RELATIVE_URL_PERSONALINFO = "/personal_info";
    public static final String RELATIVE_URL_UNAUTHORIZED = "/unauthorized";
    public static final String RELATIVE_URL_HOME = "/";

    //PATH PARAMETERS
    public static final String URL_PARAMETER_TRAINER_HOME_PAGE_ADD_WEEKS = "addWeeks";

    //GENERAL APPLICATION PATHS
    public static final String PATH_THE_GYM = "/jsp/thegym.jsp";
    public static final String PATH_PRICES = "/jsp/prices.jsp";
    public static final String PATH_REGISTER = "/jsp/access/register.jsp";
    public static final String PATH_ABOUTUS = "/jsp/aboutus.jsp";
    public static final String PATH_CALENDAR = "/jsp/calendar.jsp";
    public static final String PATH_CONFIRMED_REGISTRATION = "/jsp/access/confirmed_registration.jsp";
    public static final String PATH_CONFIRM_REGISTRATION = "/jsp/access/confirm_registration.jsp";
    public static final String PATH_LOGIN = "/jsp/access/login.jsp";
    public static final String PATH_COURSES = "/jsp/courses.jsp";
    public static final String PATH_STAFF = "/jsp/staff.jsp";
    public static final String PATH_ROLES = "/jsp/access/roles.jsp";

    //TRAINEE PATH
    public static final String PATH_TRAINEE = "/jsp/trainee/trainee.jsp";

    //PASSWORD FLOW PATHS
    public static final String PATH_PASSWORD_FORGOT = "/jsp/auth/password_forgot.jsp";
    public static final String PATH_PASSWORD_CHANGE = "/jsp/auth/password_change.jsp";

    //SECRETARY APPLICATION PATH
    public static final String PATH_SECRETARY_HOME = "/jsp/secretary/home.jsp";
    public static final String PATH_SECRETARY_ADD_COURSES = "/jsp/secretary/addcourses.jsp";
    public static final String PATH_SECRETARY_MANAGES_SUBSCRIPTION = "/jsp/secretary/managesubscription.jsp";
    public static final String PATH_SECRETARY_ADD_ACCOUNT = "/jsp/secretary/addaccount.jsp";
    public static final String PATH_SECRETARY_MANAGE_ROLES = "/jsp/secretary/manageroles.jsp";
    public static final String PATH_SECRETARY_ADD_CERTIFICATE = "/jsp/secretary/addmedicalcertificate.jsp";

    //TRAINER PATHS
    public static final String PATH_TRAINER_HOME = "/jsp/trainer/home.jsp";

    public static final String PATH_PERSONALINFO = "/jsp/personalinfo.jsp";

    //EMAIL CONSTANTS
    public static final String NOREPLYEMAIL = "noreply@gwa.com";
    public static final String HOST = "localhost";
    public static final String MAILSMTPHOST = "mail.smtp.host";
    public static final String CONFIRMATION_URL = "http://127.0.0.1:8080/wa2122-gwa/confirm_registration?token=";
    public static final String PASSWORD_CHANGE_URL = "http://127.0.0.1:8080/wa2122-gwa/password_change?token=";

    //PARAMETERS POST/GET CONSTANTS
    public static final String TAX_CODE = "tax_code";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String BIRTH_DATE = "birth_date";
    public static final String ADDRESS = "address";
    public static final String TELEPHONE_NUMBER = "telephone_number";
    public static final String AVATAR = "avatar";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirm_password";
    public static final String MEDICAL_CERTIFICATE = "medical_certificate";
    public static final String MESSAGE = "message";

    //FIELDS DELLE TABELLE NOMETABELLA_NOMEFIELD

    //emailconfirmation
    public static final String EMAILCONFIRMATION_PERSON = "person";
    public static final String EMAILCONFIRMATION_TOKEN = "token";
    public static final String EMAILCONFIRMATION_EXPIRATIONDATE = "expirationdate";

    //typeofroles
    public static final String TYPEOFROLES_ROLE = "role";

    //personroles
    public static final String PERSONROLES_PERSON = "person";
    public static final String PERSONROLES_ROLE = "role";

    //person
    public static final String PERSON_EMAIL = "email";
    public static final String PERSON_NAME = "name";
    public static final String PERSON_SURNAME = "surname";
    public static final String PERSON_PSW = "psw";
    public static final String PERSON_TAXCODE = "taxcode";
    public static final String PERSON_BIRTHDATE = "birthdate";
    public static final String PERSON_TELEPHONE = "telephone";
    public static final String PERSON_ADDRESS = "address";
    public static final String PERSON_AVATARPATH = "avatarpath";

    //passwordreset
    public static final String PASSWORDRESET_TOKEN = "token";
    public static final String PASSWORDRESET_EXPIRATIONDATE = "expirationdate";
    public static final String PASSWORDRESET_PERSON = "person";

    //medicalcertificate
    public static final String MEDICALCERTIFICATE_PERSON = "person";
    public static final String MEDICALCERTIFICATE_EXPIRATIONDATE = "expirationdate";
    public static final String MEDICALCERTIFICATE_DOCTORNAME = "doctorname";
    public static final String MEDICALCERTIFICATE_PATH = "path";

    //reservation
    public static final String RESERVATION_TRAINEE = "trainee";
    public static final String RESERVATION_LECTUREROOM = "lectureroom";
    public static final String RESERVATION_LECTUREDATE = "lecturedate";
    public static final String RESERVATION_LECTURESTARTTIME = "lecturestarttime";

    //teaches
    public static final String TEACHES_COURSEEDITIONID = "courseeditionid";
    public static final String TEACHES_COURSENAME = "coursename";
    public static final String TEACHES_TRAINER = "trainer";

    //room
    public static final String ROOM_NAME = "name";
    public static final String ROOM_SLOTS = "slots";

    //lecturetimeslot
    public static final String LECTURETIMESLOT_ROOMNAME = "roomname";
    public static final String LECTURETIMESLOT_DATE = "date";
    public static final String LECTURETIMESLOT_STARTTIME = "starttime";
    public static final String LECTURETIMESLOT_COURSEEDITIONID = "courseeditionid";
    public static final String LECTURETIMESLOT_COURSENAME = "coursename";
    public static final String LECTURETIMESLOT_SUBSTITUTION = "substitution";

    //coursedition
    public static final String COURSEEDITION_ID = "id";
    public static final String COURSEEDITION_NAME = "name";

    //course
    public static final String COURSE_NAME = "name";
    public static final String COURSE_DESCRIPTION = "description";

    //subscriptiontype
    public static final String SUBSCRIPTIONTYPE_COURSEEDITIONID = "courseeditionid";
    public static final String SUBSCRIPTIONTYPE_COURSENAME = "coursename";
    public static final String SUBSCRIPTIONTYPE_DURATION = "duration";
    public static final String SUBSCRIPTIONTYPE_COST = "cost";

    //subscription
    public static final String SUBSCRIPTION_COURSEEDITIONID = "courseeditionid";
    public static final String SUBSCRIPTION_COURSENAME = "coursename";
    public static final String SUBSCRIPTION_DURATION = "duration";
    public static final String SUBSCRIPTION_STARTDAY = "startday";
    public static final String SUBSCRIPTION_TRAINEE = "trainee";
    public static final String SUBSCRIPTION_DISCOUNT = "discount";


    //UPLOAD FILE CONSTANTS
    public static final String AVATAR_PATH_FOLDER = "../gwa/avatars";
    public static final String MEDICAL_CERTIFICATE_PATH_FOLDER = "../gwa/medical_certificates";

    //ACCEPTED EXTENSIONS
    public static final String[] ACCEPTED_EXTENSIONS_AVATAR = new String[]{"png", "jpg", "jpeg"};
    public static final String[] ACCEPTED_EXTENSIONS_MEDICAL_CERTIFICATE = new String[]{"pdf"};

    public static final long DAY = 1000 * 60 * 60 * 24;


}
