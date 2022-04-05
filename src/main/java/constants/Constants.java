package constants;

public class Constants {

    public static final String DATASOURCE = "java:/comp/env/jdbc/gwa-res";

    //GENERAL APPLICATION PATHS
    public static final String PATH_THE_GYM = "/jsp/thegym.jsp";
    public static final String PATH_PRICES = "/jsp/prices.jsp";
    public static final String PATH_REGISTER = "/jsp/access/register.jsp";
    public static final String PATH_ABOUTUS = "/jsp/aboutus.jsp";
    public static final String PATH_CALENDAR = "/jsp/calendar.jsp";
    public static final String PATH_CONFIRMED_REGISTRATION = "/jsp/access/confirmed_registration.jsp";
    public static final String PATH_CONFIRM_REGISTRATION = "/jsp/access/confirm_registration.jsp";
    public static final String PATH_LOGIN = "/jsp/access/login.jsp";
    public static final String PATH_COURSES="/jsp/courses.jsp";
    public static final String PATH_STAFF="/jsp/staff.jsp";

    //PASSWORD FLOW PATHS
    public static final String PATH_PASSWORD_FORGOT = "/jsp/auth/password_forgot.jsp";
    public static final String PATH_PASSWORD_CHANGE = "/jsp/auth/password_change.jsp";

    //TRAINER PATHS
    public static final String PATH_TRAINER_HOME = "/jsp/trainer/home.jsp";

    public static final String PATH_PERSONALINFO = "/jsp/personalinfo.jsp";

    //EMAIL CONSTANTS
    public static final String NOREPLYEMAIL = "noreply@gwa.com";
    public static final String HOST = "localhost";
    public static final String MAILSMTPHOST = "mail.smtp.host";
    public static final String CONFIRMATION_URL = "http://127.0.0.1:8080/wa2122-gwa/confirm_registration?token=";

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

    //UPLOAD FILE CONSTANTS
    public static final String AVATAR_PATH_FOLDER = "../gwa/avatars";
    public static final String MEDICAL_CERTIFICATE_PATH_FOLDER = "../gwa/medical_certificates";

    //ACCEPTED EXTENSIONS
    public static final String[] ACCPETED_EXTENSIONS_AVATAR = new String[]{"png", "jpg", "jpeg"};
    public static final String[] ACCPETED_EXTENSIONS_MEDICAL_CERTIFICATE = new String[]{"pdf"};

    public static final long DAY = 1000 * 60 * 60 * 24;


}
