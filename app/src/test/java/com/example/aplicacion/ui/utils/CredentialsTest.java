package com.example.aplicacion.ui.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Credentials class.
 * Tests API configuration and constants.
 */
public class CredentialsTest {

    @Test
    public void testBaseUrlIsNotEmpty() {
        assertNotNull("Base URL should not be null", Credentials.BASE_URL);
        assertFalse("Base URL should not be empty", Credentials.BASE_URL.isEmpty());
    }

    @Test
    public void testBaseUrlIsValid() {
        assertTrue("Base URL should start with https", 
                Credentials.BASE_URL.startsWith("https://"));
    }

    @Test
    public void testApiKeyIsNotNull() {
        assertNotNull("API key should not be null", Credentials.API_KEY);
    }

    @Test
    public void testPopularFlagDefaultValue() {
        assertFalse("POPULAR flag should be false by default", Credentials.POPULAR);
    }
}
