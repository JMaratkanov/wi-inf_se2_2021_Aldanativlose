package globals;

import com.vaadin.flow.component.Component;

public class Globals {
    public static String CURRENT_USER = "current_User";

    public static class Pages {
        public static final String SHOW_MAIN = "";
        public static final String LOGIN_VIEW = "login";

        public static final String SELECTION_VIEW = "selection";
        public static final String HOME_VIEW = "home";
    }

    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String USER = "user";

    }

    public static class Errors {
        public static final String NOUSERFOUND = "nouser";
        public static final String SQLERROR = "sql";
        public static final String DATABASE = "database";
    }

}
