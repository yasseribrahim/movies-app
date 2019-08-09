package com.cyber.accounting.movies.app.presentation.presenters.movie;

import com.cyber.accounting.movies.app.presentation.presenters.MainPresenter;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface MoviesPresenter extends MainPresenter {
    void getMovies(String filter, int page);

    void getMovieDetails(long movieId);
}
