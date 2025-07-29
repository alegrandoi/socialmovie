package com.example.aplicacion.ui.utils;

import com.example.aplicacion.BuildConfig;

public class Credentials {

    public static final String BASE_URL = "https://api.themoviedb.org";

    /**
     * API key supplied via Gradle build configuration.
     * <p>
     * Defined in {@code BuildConfig.MOVIE_DB_API_KEY} which reads from the
     * {@code MOVIE_DB_API_KEY} property or environment variable at build time.
     */
    public static final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    public static boolean POPULAR = false;
}
