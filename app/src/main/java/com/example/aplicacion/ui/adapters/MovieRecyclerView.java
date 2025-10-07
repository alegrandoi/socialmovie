package com.example.aplicacion.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion.R;
import com.example.aplicacion.ui.models.MovieModel;

import java.util.List;

/**
 * Adapter for displaying movies in RecyclerView.
 * Extends BaseMovieAdapter to eliminate code duplication.
 * Supports both popular movies and search results display modes.
 */
public class MovieRecyclerView extends BaseMovieAdapter {

    private List<MovieModel> mMovies;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        super(onMovieListener);
    }

    @Override
    protected List<MovieModel> getMovieList() {
        return mMovies;
    }

    @Override
    protected void setMovieList(List<MovieModel> movies) {
        this.mMovies = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == DISPLAY_SEARCH){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item
                    ,parent,false);
            return new MovieViewHolder(view,onMovieListener);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout
                    ,parent,false);
            return new Popular_View_Holder(view,onMovieListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        int itemViewType = getItemViewType(i);
        MovieModel movie = mMovies.get(i);
        
        if (itemViewType == DISPLAY_SEARCH) {
            // Search view binding
            MovieViewHolder searchHolder = (MovieViewHolder) holder;
            searchHolder.ratingBar.setRating(convertRating(movie.getVote_average()));
            loadMovieImage(holder, movie.getPoster_path(), searchHolder.imageView);
        } else {
            // Popular view binding
            Popular_View_Holder popularHolder = (Popular_View_Holder) holder;
            popularHolder.ratingBar22.setRating(convertRating(movie.getVote_average()));
            loadMovieImage(holder, movie.getPoster_path(), popularHolder.imageView22);
        }
    }

    /**
     * Updates the movie list and notifies observers.
     * @param movies New list of movies to display
     */
    public void setmMovies(List<MovieModel> movies) {
        setMovieList(movies);
        notifyMoviesChanged();
    }
}
