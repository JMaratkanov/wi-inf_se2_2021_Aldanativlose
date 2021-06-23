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
    }

    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String USER = "user";

    }

    public static class Errors {
        public static final String NOUSERFOUND = "Nutzer konnte nicht gefunden werden, sind sie bereits registriert?";
        public static final String EXISTINGUSER = "Dieser Nutzer existiert bereits, loggen sie sich mit ihrer Email und Passwort ein";
        public static final String SQLERROR = "sql error";
        public static final String DATABASE = "database error";
        public static final String ILLEGALCHAR = "Sie versuchen ein nicht erlaubtes Zeichen zu verwenden. " +  "\n" + " Nicht erlaubt sind: '!'  ','  ':'";
    }

}
