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
 * Adapter for displaying search results in RecyclerView.
 * Extends BaseMovieAdapter to eliminate code duplication.
 * Uses different layout (movie_list_item_buscar) with additional movie details.
 */
public class MovieRecyclerViewBuscar extends BaseMovieAdapter {
    private List<MovieModel> mMoviesBuscar;

    public MovieRecyclerViewBuscar(OnMovieListener onMovieListenerBuscar) {
        super(onMovieListenerBuscar);
    }

    @Override
    protected List<MovieModel> getMovieList() {
        return mMoviesBuscar;
    }

    @Override
    protected void setMovieList(List<MovieModel> movies) {
        this.mMoviesBuscar = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == DISPLAY_SEARCH){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item_buscar
                    ,parent,false);
            return new MovieViewHolder(view,onMovieListenerBuscar);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout
                    ,parent,false);
            return new Popular_View_Holder(view,onMovieListenerBuscar);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        int itemViewType = getItemViewType(i);
        MovieModel movie = mMoviesBuscar.get(i);
        
        if (itemViewType == DISPLAY_SEARCH) {
            // Search view binding with additional details
            MovieViewHolderBuscar searchHolder = (MovieViewHolderBuscar) holder;
            searchHolder.titleBuscar.setText(movie.getTitle());
            searchHolder.release_dateBuscar.setText(movie.getRelease_date());
            searchHolder.durationBuscar.setText(movie.getOriginal_language());
            searchHolder.ratingBarBuscar.setRating(convertRating(movie.getVote_average()));
            loadMovieImage(holder, movie.getPoster_path(), searchHolder.imageViewBuscar);
        } else {
            // Popular view binding
            Popular_View_Holder popularHolder = (Popular_View_Holder) holder;
            popularHolder.ratingBar22.setRating(convertRating(movie.getVote_average()));
            loadMovieImage(holder, movie.getPoster_path(), popularHolder.imageView22);
        }
    }

    /**
     * Updates the movie list and notifies observers.
     * @param moviesBuscar New list of movies to display from search
     */
    public void setmMoviesBuscar(List<MovieModel> moviesBuscar) {
        setMovieList(moviesBuscar);
        notifyMoviesChanged();
    }
}
