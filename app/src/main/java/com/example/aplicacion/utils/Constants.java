package com.example.aplicacion.utils;

/**
 * Application-wide constants.
 * Centralized location for all constant values used across the app.
 */
public final class Constants {

    // Prevent instantiation
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }

    /**
     * Network and API constants
     */
    public static final class Network {
        public static final int API_TIMEOUT_SECONDS = 30;
        public static final int CONNECT_TIMEOUT_SECONDS = 15;
        public static final int READ_TIMEOUT_SECONDS = 30;
        public static final int WRITE_TIMEOUT_SECONDS = 15;
    }

    /**
     * Validation constants
     */
    public static final class Validation {
        public static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        public static final int MIN_PASSWORD_LENGTH = 6;
        public static final int MAX_PASSWORD_LENGTH = 128;
    }

    /**
     * Firebase constants
     */
    public static final class Firebase {
        public static final String USERS_NODE = "Users";
        public static final String DEFAULT_LANGUAGE = "es";
    }

    /**
     * Intent extra keys
     */
    public static final class IntentExtras {
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_TITLE = "movie_title";
        public static final String FROM_SCREEN = "from_screen";
    }

    /**
     * Preference keys
     */
    public static final class Preferences {
        public static final String PREFS_NAME = "SocialMoviePrefs";
        public static final String KEY_USER_ID = "user_id";
        public static final String KEY_USER_EMAIL = "user_email";
        public static final String KEY_IS_LOGGED_IN = "is_logged_in";
    }

    /**
     * Log tags
     */
    public static final class LogTags {
        public static final String NETWORK = "Network";
        public static final String DATABASE = "Database";
        public static final String AUTH = "Authentication";
        public static final String UI = "UI";
    }
}
