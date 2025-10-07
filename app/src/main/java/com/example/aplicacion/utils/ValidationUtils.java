package com.example.aplicacion.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Utility class for input validation.
 * Provides reusable validation methods for common inputs.
 */
public final class ValidationUtils {

    // Prevent instantiation
    private ValidationUtils() {
        throw new AssertionError("Cannot instantiate ValidationUtils class");
    }

    /**
     * Validates email format.
     * @param email Email string to validate
     * @return true if email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return email.matches(Constants.Validation.EMAIL_REGEX);
    }

    /**
     * Validates password length and format.
     * @param password Password string to validate
     * @return true if password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        int length = password.length();
        return length >= Constants.Validation.MIN_PASSWORD_LENGTH 
                && length <= Constants.Validation.MAX_PASSWORD_LENGTH;
    }

    /**
     * Checks if two passwords match.
     * @param password First password
     * @param confirmPassword Second password
     * @return true if passwords match, false otherwise
     */
    public static boolean passwordsMatch(String password, String confirmPassword) {
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            return false;
        }
        return password.equals(confirmPassword);
    }

    /**
     * Validates if string is not empty.
     * @param text String to validate
     * @return true if not empty, false otherwise
     */
    public static boolean isNotEmpty(String text) {
        return !TextUtils.isEmpty(text) && !text.trim().isEmpty();
    }

    /**
     * Validates phone number format.
     * @param phone Phone number to validate
     * @return true if phone is valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        return Patterns.PHONE.matcher(phone).matches();
    }
}
