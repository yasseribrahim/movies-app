package com.cyber.accounting.movies.app.domain.interactors.movies;

import com.cyber.accounting.movies.app.domain.interactors.MainInteractor;
import com.cyber.accounting.movies.app.domain.models.movies.MovieDetails;
import com.cyber.accounting.movies.app.domain.models.movies.Movies;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface MoviesInteractor extends MainInteractor {
    void getMovies(String filter, int page);

    void getMovieDetails(long movieId);

    interface MoviesCallbackStates extends CallbackStates {
        void onGetMoviesComplete(Movies movies);

        void onGetMovieDetailsComplete(MovieDetails details);
    }
}
