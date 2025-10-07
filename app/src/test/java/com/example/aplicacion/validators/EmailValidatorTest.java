package com.example.aplicacion.validators;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for email validation logic.
 */
public class EmailValidatorTest {

    private static final String VALID_EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Test
    public void testValidEmail() {
        String validEmail = "test@example.com";
        assertTrue("Valid email should pass", validEmail.matches(VALID_EMAIL_REGEX));
    }

    @Test
    public void testValidEmailWithSubdomain() {
        String validEmail = "user@mail.example.com";
        assertTrue("Email with subdomain should pass", validEmail.matches(VALID_EMAIL_REGEX));
    }

    @Test
    public void testValidEmailWithNumbers() {
        String validEmail = "user123@example.com";
        assertTrue("Email with numbers should pass", validEmail.matches(VALID_EMAIL_REGEX));
    }

    @Test
    public void testInvalidEmailWithoutAt() {
        String invalidEmail = "testexample.com";
        assertFalse("Email without @ should fail", invalidEmail.matches(VALID_EMAIL_REGEX));
    }

    @Test
    public void testInvalidEmailWithoutDomain() {
        String invalidEmail = "test@";
        assertFalse("Email without domain should fail", invalidEmail.matches(VALID_EMAIL_REGEX));
    }

    @Test
    public void testInvalidEmailWithSpaces() {
        String invalidEmail = "test @example.com";
        assertFalse("Email with spaces should fail", invalidEmail.matches(VALID_EMAIL_REGEX));
    }

    @Test
    public void testEmptyEmail() {
        String emptyEmail = "";
        assertFalse("Empty email should fail", emptyEmail.matches(VALID_EMAIL_REGEX));
    }

    @Test
    public void testValidEmailWithDash() {
        String validEmail = "user-name@example.com";
        assertTrue("Email with dash should pass", validEmail.matches(VALID_EMAIL_REGEX));
    }

    @Test
    public void testValidEmailWithUnderscore() {
        String validEmail = "user_name@example.com";
        assertTrue("Email with underscore should pass", validEmail.matches(VALID_EMAIL_REGEX));
    }
}
