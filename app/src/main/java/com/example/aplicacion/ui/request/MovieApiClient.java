package com.example.aplicacion.ui.request;package com.example.aplicacion.ui.request;



import android.util.Log;import android.util.Log;

import androidx.lifecycle.LiveData;import androidx.lifecycle.LiveData;

import androidx.lifecycle.MutableLiveData;import com.example.aplicacion.ui.models.MovieModel;

import com.example.aplicacion.AppExecutors;import com.example.aplicacion.util.AppExecutors;

import com.example.aplicacion.ui.models.MovieModel;import java.util.List;

import java.util.List;

import java.util.concurrent.Future;/**

import java.util.concurrent.TimeUnit; * Refactored MovieApiClient with separated concerns.

 * Delegates search and popular movie operations to specialized handlers.

/** * 

 * Refactored MovieApiClient with separated concerns. * This refactoring splits the original 257-line God Object into:

 *  * - MovieApiClient: Coordination and public API

 * This refactoring splits the original 257-line God Object into: * - MovieSearchHandler: Search operations

 * - MovieApiClient: Coordination and public API (this class) * - MoviePopularHandler: Popular movies operations

 * - MovieSearchHandler: Search operations logic */

 * - MoviePopularHandler: Popular movies logicpublic class MovieApiClient {

 * 

 * Benefits:    private static final int API_TIMEOUT_SECONDS = 30;

 * - Single Responsibility Principle (SRP) compliance    private static final String TAG = "MovieApiClient";

 * - Easier testing (each handler can be tested independently)

 * - Better code organization and maintainability    private final MutableLiveData<List<MovieModel>> searchMovies;

 * - Facilitates future extensions (e.g., MovieRecommendationHandler)    private final MutableLiveData<List<MovieModel>> popularMovies;

 */    

public class MovieApiClient {    private static MovieApiClient instance;

    

    private static final int API_TIMEOUT_SECONDS = 30;    private MovieSearchHandler searchHandler;

    private static final String TAG = "MovieApiClient";    private MoviePopularHandler popularHandler;



    // LiveData for observers    /**

    private final MutableLiveData<List<MovieModel>> searchMovies;     * Returns singleton instance of MovieApiClient.

    private final MutableLiveData<List<MovieModel>> popularMovies;     * @return MovieApiClient instance

         */

    // Singleton instance    public static MovieApiClient getInstance() {

    private static MovieApiClient instance;        if (instance == null) {

                instance = new MovieApiClient();

    // Specialized handlers        }

    private MovieSearchHandler searchHandler;        return instance;

    private MoviePopularHandler popularHandler;    }



    /**    private MovieApiClient() {

     * Returns singleton instance of MovieApiClient.        searchMovies = new MutableLiveData<>();

     * @return MovieApiClient instance        popularMovies = new MutableLiveData<>();

     */        searchHandler = new MovieSearchHandler(searchMovies);

    public static MovieApiClient getInstance() {        popularHandler = new MoviePopularHandler(popularMovies);

        if (instance == null) {    }

            instance = new MovieApiClient();

        }    /**

        return instance;     * Gets LiveData for search results.

    }     * @return LiveData containing list of movies from search

     */

    /**    public LiveData<List<MovieModel>> getMovies() {

     * Private constructor for singleton pattern.        return searchMovies;

     * Initializes LiveData and creates specialized handlers.    }

     */

    private MovieApiClient() {    /**

        searchMovies = new MutableLiveData<>();     * Gets LiveData for popular movies.

        popularMovies = new MutableLiveData<>();     * @return LiveData containing list of popular movies

        searchHandler = new MovieSearchHandler(searchMovies);     */

        popularHandler = new MoviePopularHandler(popularMovies);    public LiveData<List<MovieModel>> getPopularMovies() {

    }        return popularMovies;

    }

    /**

     * Gets LiveData for search results.    /**

     * Observers will be notified when new search results are available.     * Searches movies by query with pagination.

     *      * Automatically handles timeout and cancellation.

     * @return LiveData containing list of movies from search     * 

     */     * @param query Search query string

    public LiveData<List<MovieModel>> getMovies() {     * @param pageNumber Page number for pagination

        return searchMovies;     */

    }    public void searchMoviesApi(String query, int pageNumber) {

        searchHandler.cancel();

    /**        searchHandler.search(query, pageNumber);

     * Gets LiveData for popular movies.        scheduleTimeout(searchHandler.getFuture());

     * Observers will be notified when new popular movies are available.    }

     * 

     * @return LiveData containing list of popular movies    /**

     */     * Fetches popular movies with pagination.

    public LiveData<List<MovieModel>> getMoviesPop() {     * Automatically handles timeout and cancellation.

        return popularMovies;     * 

    }     * @param pageNumber Page number for pagination

     */

    /**    public void searchPopularMovies(int pageNumber) {

     * Searches movies by query with pagination.        popularHandler.cancel();

     * Cancels any ongoing search before starting a new one.        popularHandler.search(pageNumber);

     * Automatically handles timeout after 30 seconds.        scheduleTimeout(popularHandler.getFuture());

     *     }

     * @param query Search query string

     * @param pageNumber Page number for pagination (1-based)    /**

     */     * Schedules automatic timeout for API requests.

    public void searchMoviesApi(String query, int pageNumber) {     * @param future Future to cancel on timeout

        Log.d(TAG, "Searching movies: query='" + query + "', page=" + pageNumber);     */

        searchHandler.cancel();    private void scheduleTimeout(final Future<?> future) {

        searchHandler.search(query, pageNumber);        AppExecutors.getInstance().networkIO().schedule(new Runnable() {

        scheduleTimeout(searchHandler.getFuture());            @Override

    }            public void run() {

                if (future != null && !future.isDone()) {

    /**                    future.cancel(true);

     * Fetches popular movies with pagination.                }

     * Cancels any ongoing request before starting a new one.            }

     * Automatically handles timeout after 30 seconds.        }, API_TIMEOUT_SECONDS, TimeUnit.SECONDS);

     *     }

     * @param pageNumber Page number for pagination (1-based)}

     */
    public void searchMoviesPop(int pageNumber) {
        Log.d(TAG, "Fetching popular movies: page=" + pageNumber);
        popularHandler.cancel();
        popularHandler.search(pageNumber);
        scheduleTimeout(popularHandler.getFuture());
    }

    /**
     * Schedules automatic timeout for API requests.
     * If the request hasn't completed within API_TIMEOUT_SECONDS,
     * it will be cancelled automatically.
     * 
     * @param future Future representing the ongoing API request
     */
    private void scheduleTimeout(final Future<?> future) {
        if (future == null) {
            Log.w(TAG, "Cannot schedule timeout: future is null");
            return;
        }
        
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                if (!future.isDone()) {
                    Log.w(TAG, "API request timed out after " + API_TIMEOUT_SECONDS + " seconds");
                    future.cancel(true);
                }
            }
        }, API_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }
}
