package com.cyber.accounting.movies.app.presentation.presenters.callbacks;

import com.cyber.accounting.movies.app.domain.models.movies.MovieDetails;
import com.cyber.accounting.movies.app.domain.models.movies.Movies;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface MoviesCallback extends BaseCallback {
    default void onGetMoviesComplete(Movies movies) {
    }

    default void onGetMovieDetailsComplete(MovieDetails details) {
    }
}
