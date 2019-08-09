package com.cyber.accounting.movies.app.presentation.presenters.movie;

import android.view.View;

import com.cyber.accounting.movies.app.MoviesApplication;
import com.cyber.accounting.movies.app.domain.controller.Controller;
import com.cyber.accounting.movies.app.domain.interactors.movies.MoviesInteractor;
import com.cyber.accounting.movies.app.domain.interactors.movies.MoviesInteractorImp;
import com.cyber.accounting.movies.app.domain.models.movies.MovieDetails;
import com.cyber.accounting.movies.app.domain.models.movies.Movies;
import com.cyber.accounting.movies.app.presentation.presenters.callbacks.MoviesCallback;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public class MoviesPresenterImp implements MoviesPresenter, MoviesInteractor.MoviesCallbackStates {
    @Inject
    protected Retrofit retrofit;

    private MoviesCallback view;
    private MoviesInteractor interactor;

    public MoviesPresenterImp(MoviesCallback view) {
        MoviesApplication.getComponent().inject(this);
        this.view = view;
        this.interactor = new MoviesInteractorImp(retrofit.create(Controller.NotificationController.class), this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        view = null;
        interactor.onDestroy();
    }

    @Override
    public String getErrorMessage(Throwable throwable) {
        if (view != null) {
            return view.onGetErrorMessage(throwable);
        }
        return null;
    }

    @Override
    public void getMovies(String filter, int page) {
        interactor.getMovies(filter, page);
    }

    @Override
    public void getMovieDetails(long movieId) {
        interactor.getMovieDetails(movieId);
    }

    @Override
    public void failure(String message, View.OnClickListener onClickListener) {
        if (view != null) {
            view.onShowError(message, onClickListener);
        }
    }

    @Override
    public void showProgress() {
        if (view != null) {
            view.onShowProgress();
        }
    }

    @Override
    public void hideProgress() {
        if (view != null) {
            view.onHideProgress();
        }
    }

    @Override
    public void unAuthorized() {
        if (view != null) {
            view.onUnAuthorized();
        }
    }

    @Override
    public void onGetMoviesComplete(Movies movies) {
        if (view != null) {
            view.onGetMoviesComplete(movies);
        }
    }

    @Override
    public void onGetMovieDetailsComplete(MovieDetails details) {
        if (view != null) {
            view.onGetMovieDetailsComplete(details);
        }
    }
}
