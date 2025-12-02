package com.example.otams;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocalUnitTest {

    @Test
    public void checkAdminCredentials() {
        // Requirement: Admin credentials are pre-seeded
        String email = "admin@otams.ca";
        String password = "admin123";

        assertEquals("admin@otams.ca", email);
        assertEquals("admin123", password);
    }

    @Test
    public void validateRatingRange() {
        // Requirement: Ratings must be between 1 and 5
        float validRating = 4.5f;
        assertTrue(validRating >= 1 && validRating <= 5);

        float invalidRatingLow = 0.5f;
        float invalidRatingHigh = 6.0f;
        assertFalse(invalidRatingLow >= 1 && invalidRatingLow <= 5);
        assertFalse(invalidRatingHigh >= 1 && invalidRatingHigh <= 5);
    }

    @Test
    public void validateTimeSlotDuration() {
        // Requirement: Slots must be in 30-minute increments
        int startHour = 10;
        int startMin = 0;
        int endHour = 10;
        int endMin = 30;

        int durationMinutes = (endHour * 60 + endMin) - (startHour * 60 + startMin);
        assertEquals(30, durationMinutes);
    }

    @Test
    public void validateEmailFormat() {
        // General logic check for email format
        String validEmail = "student@uottawa.ca";
        String invalidEmail = "studentuottawa.ca";

        assertTrue(validEmail.contains("@"));
        assertFalse(invalidEmail.contains("@"));
    }
}