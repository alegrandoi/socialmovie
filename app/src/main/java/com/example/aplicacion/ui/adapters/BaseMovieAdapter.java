package com.example.aplicacion.ui.adapters;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.aplicacion.ui.models.MovieModel;
import com.example.aplicacion.ui.utils.Credentials;
import java.util.List;

/**
 * Base adapter class containing common functionality for movie RecyclerViews.
 * Eliminates code duplication between MovieRecyclerView and MovieRecyclerViewBuscar.
 * 
 * This abstract class provides:
 * - Common view type constants (DISPLAY_POP, DISPLAY_SEARCH)
 * - Shared item count logic
 * - Movie selection functionality
 * - View type determination based on Credentials.POPULAR flag
 * - Glide image loading helper method
 * 
 * Subclasses must implement:
 * - getMovieList(): Return the specific movie list
 * - setMovieList(): Update the specific movie list
 * 
 * @see MovieRecyclerView
 * @see MovieRecyclerViewBuscar
 */
public abstract class BaseMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // View type constants
    protected static final int DISPLAY_POP = 1;
    protected static final int DISPLAY_SEARCH = 2;
    
    // Base URL for movie poster images
    protected static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";
    
    // Rating conversion factor (API uses 0-10, we use 0-5)
    protected static final float RATING_DIVISOR = 2.0f;
    
    protected OnMovieListener onMovieListener;

    /**
     * Constructor for base adapter.
     * @param onMovieListener Listener for movie click events
     */
    public BaseMovieAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    /**
     * Get the movie list from the concrete adapter.
     * @return List of movies
     */
    protected abstract List<MovieModel> getMovieList();

    /**
     * Set the movie list in the concrete adapter.
     * @param movies List of movies to set
     */
    protected abstract void setMovieList(List<MovieModel> movies);

    @Override
    public int getItemCount() {
        List<MovieModel> movies = getMovieList();
        return (movies != null) ? movies.size() : 0;
    }

    /**
     * Gets the selected movie at the specified position.
     * @param position Position in the list
     * @return MovieModel at position, or null if invalid
     */
    public MovieModel getSelectedMovie(int position) {
        List<MovieModel> movies = getMovieList();
        if (movies != null && !movies.isEmpty() && position >= 0 && position < movies.size()) {
            return movies.get(position);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return Credentials.POPULAR ? DISPLAY_POP : DISPLAY_SEARCH;
    }

    /**
     * Helper method to load images using Glide.
     * Converts rating from 0-10 scale to 0-5 scale.
     * 
     * @param holder ViewHolder containing the ImageView
     * @param posterPath Poster path from API
     * @param imageView Target ImageView
     */
    protected void loadMovieImage(RecyclerView.ViewHolder holder, String posterPath, 
                                   android.widget.ImageView imageView) {
        Glide.with(holder.itemView.getContext())
             .load(IMAGE_BASE_URL + posterPath)
             .into(imageView);
    }

    /**
     * Converts API rating (0-10) to app rating (0-5).
     * @param voteAverage Vote average from API
     * @return Converted rating
     */
    protected float convertRating(float voteAverage) {
        return voteAverage / RATING_DIVISOR;
    }

    /**
     * Notifies adapter that movie list has been updated.
     * Should be called after setMovieList().
     */
    protected void notifyMoviesChanged() {
        notifyDataSetChanged();
    }
}
