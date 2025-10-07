package com.example.aplicacion.ui.request;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.aplicacion.AppExecutors;
import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.response.MovieSearchResponse;
import com.example.aplicacion.ui.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Handler for movie search operations.
 * Manages asynchronous search requests and result processing.
 */
public class MovieSearchHandler {

    private static final String TAG = "MovieSearchHandler";

    private final MutableLiveData<List<MovieModel>> moviesLiveData;
    private SearchRunnable currentRunnable;
    private Future<?> currentFuture;

    public MovieSearchHandler(MutableLiveData<List<MovieModel>> moviesLiveData) {
        this.moviesLiveData = moviesLiveData;
    }

    /**
     * Executes a movie search.
     * @param query Search query
     * @param pageNumber Page number
     */
    public void search(String query, int pageNumber) {
        currentRunnable = new SearchRunnable(query, pageNumber);
        currentFuture = AppExecutors.getInstance().networkIO().submit(currentRunnable);
    }

    /**
     * Cancels current search operation.
     */
    public void cancel() {
        if (currentRunnable != null) {
            currentRunnable.cancel();
            currentRunnable = null;
        }
    }

    /**
     * Gets the current Future for timeout management.
     * @return Current Future or null
     */
    public Future<?> getFuture() {
        return currentFuture;
    }

    /**
     * Runnable for executing search requests in background thread.
     */
    private class SearchRunnable implements Runnable {

        private final String query;
        private final int pageNumber;
        private volatile boolean cancelled = false;

        public SearchRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
        }

        @Override
        public void run() {
            try {
                Call<MovieSearchResponse> call = MovieService.getMovieApi()
                        .searchMovie(Credentials.API_KEY, query, pageNumber);
                
                Response<MovieSearchResponse> response = call.execute();

                if (cancelled) {
                    Log.d(TAG, "Search cancelled");
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    moviesLiveData.postValue(movies);
                } else {
                    String errorBody = response.errorBody() != null ? 
                            response.errorBody().string() : "Unknown error";
                    Log.e(TAG, "Search failed: " + errorBody);
                    moviesLiveData.postValue(null);
                }

            } catch (IOException e) {
                Log.e(TAG, "Error fetching search results: " + e.getMessage(), e);
                moviesLiveData.postValue(null);
            }
        }

        public void cancel() {
            cancelled = true;
        }
    }
}
