package globals;

import com.vaadin.flow.component.Component;

public class Globals {
    public static String CURRENT_USER = "current_User";

    public static class Pages {
        public static final String SHOW_MAIN = "";
        public static final String LOGIN_VIEW = "login";

        public static final String SELECTION_VIEW = "selection";
        public static final String HOME_VIEW = "home";
        public static final String SETTINGS_VIEW = "settings";
        public static final String AD_VIEW = "ads";
        public static final String APPLICATION_VIEW = "application";
    }

    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String USER = "user";

    }

    //"Technische" Fehler
    public static class Errors {
        public static final String NOUSERFOUND = "No User could be found! Please check your credentials!";
        public static final String EXISTINGUSER = "There is already a user with this email address!";
        public static final String SQLERROR = "There were problems with the SQL code. Please contact the developer!";
        public static final String DATABASE = "A failure occured while trying to connect to database with JDBC. Please contact the admin";
        public static final String ILLEGALCHAR = "Sie versuchen ein nicht erlaubtes Zeichen zu verwenden. " +  "\n" + " Nicht erlaubt sind: '!'  ','  ':'";
        public static final String DOUBLEAPPLICATION = "You have already sent an application for this offer!";
    }

}
