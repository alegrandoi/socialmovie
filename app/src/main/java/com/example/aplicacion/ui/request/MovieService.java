package com.example.aplicacion.ui.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.aplicacion.ui.utils.Credentials;
import com.example.aplicacion.ui.utils.MovieApi;

/**
 * Service class for handling Retrofit configuration and API instance creation.
 * Provides a singleton instance of MovieApi for making network requests to TMDB.
 */
public class MovieService {

    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Credentials.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retrofitBuilder.build();
    private static final MovieApi movieApi = retrofit.create(MovieApi.class);

    /**
     * Returns the singleton MovieApi instance.
     * @return MovieApi instance configured with base URL and Gson converter
     */
    public static MovieApi getMovieApi() {
        return movieApi;
    }
}
