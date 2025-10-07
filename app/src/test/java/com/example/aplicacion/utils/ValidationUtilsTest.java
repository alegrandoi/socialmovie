package com.example.aplicacion.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ValidationUtils class.
 */
public class ValidationUtilsTest {

    @Test
    public void testValidEmail() {
        assertTrue("Valid email should return true", 
                ValidationUtils.isValidEmail("test@example.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse("Invalid email should return false", 
                ValidationUtils.isValidEmail("testexample.com"));
    }

    @Test
    public void testEmptyEmail() {
        assertFalse("Empty email should return false", 
                ValidationUtils.isValidEmail(""));
    }

    @Test
    public void testNullEmail() {
        assertFalse("Null email should return false", 
                ValidationUtils.isValidEmail(null));
    }

    @Test
    public void testValidPassword() {
        assertTrue("Valid password should return true", 
                ValidationUtils.isValidPassword("password123"));
    }

    @Test
    public void testShortPassword() {
        assertFalse("Short password should return false", 
                ValidationUtils.isValidPassword("12345"));
    }

    @Test
    public void testEmptyPassword() {
        assertFalse("Empty password should return false", 
                ValidationUtils.isValidPassword(""));
    }

    @Test
    public void testPasswordsMatch() {
        assertTrue("Matching passwords should return true", 
                ValidationUtils.passwordsMatch("password123", "password123"));
    }

    @Test
    public void testPasswordsDontMatch() {
        assertFalse("Non-matching passwords should return false", 
                ValidationUtils.passwordsMatch("password123", "password456"));
    }

    @Test
    public void testIsNotEmptyWithValidString() {
        assertTrue("Non-empty string should return true", 
                ValidationUtils.isNotEmpty("test"));
    }

    @Test
    public void testIsNotEmptyWithEmptyString() {
        assertFalse("Empty string should return false", 
                ValidationUtils.isNotEmpty(""));
    }

    @Test
    public void testIsNotEmptyWithWhitespace() {
        assertFalse("Whitespace-only string should return false", 
                ValidationUtils.isNotEmpty("   "));
    }
}
