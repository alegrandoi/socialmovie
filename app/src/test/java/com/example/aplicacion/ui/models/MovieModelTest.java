package com.example.aplicacion.ui.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for MovieModel class.
 * Tests model data integrity and methods.
 */
public class MovieModelTest {

    private MovieModel movie;

    @Before
    public void setUp() {
        movie = new MovieModel();
    }

    @Test
    public void testMovieModelNotNull() {
        assertNotNull("MovieModel should not be null", movie);
    }

    @Test
    public void testSetAndGetTitle() {
        String testTitle = "Test Movie";
        movie.setTitle(testTitle);
        assertEquals("Title should match", testTitle, movie.getTitle());
    }

    @Test
    public void testSetAndGetMovieId() {
        int testId = 12345;
        movie.setMovie_id(testId);
        assertEquals("Movie ID should match", testId, movie.getMovie_id());
    }

    @Test
    public void testSetAndGetPosterPath() {
        String testPath = "/test_poster.jpg";
        movie.setPoster_path(testPath);
        assertEquals("Poster path should match", testPath, movie.getPoster_path());
    }

    @Test
    public void testSetAndGetReleaseDate() {
        String testDate = "2025-10-07";
        movie.setRelease_date(testDate);
        assertEquals("Release date should match", testDate, movie.getRelease_date());
    }

    @Test
    public void testSetAndGetVoteAverage() {
        float testRating = 8.5f;
        movie.setVote_average(testRating);
        assertEquals("Vote average should match", testRating, movie.getVote_average(), 0.01f);
    }

    @Test
    public void testDefaultValuesAreZeroOrNull() {
        MovieModel newMovie = new MovieModel();
        assertEquals("Default movie ID should be 0", 0, newMovie.getMovie_id());
        assertEquals("Default vote average should be 0.0f", 0.0f, newMovie.getVote_average(), 0.01f);
    }
}
