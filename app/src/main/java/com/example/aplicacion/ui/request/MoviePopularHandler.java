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
 * Handler for popular movies operations.
 * Manages asynchronous popular movies requests and result processing.
 */
public class MoviePopularHandler {

    private static final String TAG = "MoviePopularHandler";

    private final MutableLiveData<List<MovieModel>> moviesLiveData;
    private PopularRunnable currentRunnable;
    private Future<?> currentFuture;

    public MoviePopularHandler(MutableLiveData<List<MovieModel>> moviesLiveData) {
        this.moviesLiveData = moviesLiveData;
    }

    /**
     * Fetches popular movies.
     * @param pageNumber Page number for pagination
     */
    public void search(int pageNumber) {
        currentRunnable = new PopularRunnable(pageNumber);
        currentFuture = AppExecutors.getInstance().networkIO().submit(currentRunnable);
    }

    /**
     * Cancels current operation.
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
     * Runnable for executing popular movies requests in background thread.
     */
    private class PopularRunnable implements Runnable {

        private final int pageNumber;
        private volatile boolean cancelled = false;

        public PopularRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        @Override
        public void run() {
            try {
                Call<MovieSearchResponse> call = MovieService.getMovieApi()
                        .getPopular(Credentials.API_KEY, pageNumber);
                
                Response<MovieSearchResponse> response = call.execute();

                if (cancelled) {
                    Log.d(TAG, "Popular movies fetch cancelled");
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    moviesLiveData.postValue(movies);
                } else {
                    String errorBody = response.errorBody() != null ? 
                            response.errorBody().string() : "Unknown error";
                    Log.e(TAG, "Popular movies fetch failed: " + errorBody);
                    moviesLiveData.postValue(null);
                }

            } catch (IOException e) {
                Log.e(TAG, "Error fetching popular movies: " + e.getMessage(), e);
                moviesLiveData.postValue(null);
            }
        }

        public void cancel() {
            cancelled = true;
        }
    }
}
