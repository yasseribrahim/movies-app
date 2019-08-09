package com.cyber.accounting.movies.app.domain.interactors.movies;

import android.view.View;

import com.cyber.accounting.movies.app.BuildConfig;
import com.cyber.accounting.movies.app.domain.controller.Controller;
import com.cyber.accounting.movies.app.domain.interactors.BaseInteractor;
import com.cyber.accounting.movies.app.domain.models.movies.MovieDetails;
import com.cyber.accounting.movies.app.domain.models.movies.Movies;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.HttpException;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public class MoviesInteractorImp extends BaseInteractor implements MoviesInteractor {
    private final Controller.NotificationController controller;
    private final MoviesCallbackStates callback;

    public MoviesInteractorImp(Controller.NotificationController controller, MoviesCallbackStates callback) {
        this.controller = controller;
        this.callback = callback;
    }

    @Override
    public void getMovies(String filter, final int page) {
        callback.showProgress();
        prepare(controller.getMovies(filter, BuildConfig.API_KEY, page), new GetMoviesObserver(callback, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovies(filter, page);
            }
        }));
    }

    @Override
    public void getMovieDetails(final long movieId) {
        callback.showProgress();
        prepare(controller.getMovieDetails(movieId, BuildConfig.API_KEY), new GetMovieDetailsObserver(callback, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovieDetails(movieId);
            }
        }));
    }

    private final class GetMoviesObserver extends BaseObserver<Movies> {
        private View.OnClickListener listener;

        public GetMoviesObserver(CallbackStates callback, View.OnClickListener listener) {
            super(callback);
            this.listener = listener;
        }

        @Override
        public void onNext(Movies movies) {
            callback.onGetMoviesComplete(movies);
            super.onNext(movies);
        }

        @Override
        public void onComplete() {
            super.onComplete();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if (e instanceof HttpException) {
                if (((HttpException) e).code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                    callback.unAuthorized();
                    return;
                }
            }
            String message = callback.getErrorMessage(e);
            callback.failure(message != null ? message : e.getMessage(), listener);
        }
    }

    private final class GetMovieDetailsObserver extends BaseObserver<MovieDetails> {
        private View.OnClickListener listener;

        public GetMovieDetailsObserver(CallbackStates callback, View.OnClickListener listener) {
            super(callback);
            this.listener = listener;
        }

        @Override
        public void onNext(MovieDetails details) {
            callback.onGetMovieDetailsComplete(details);
            super.onNext(details);
        }

        @Override
        public void onComplete() {
            super.onComplete();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if (e instanceof HttpException) {
                if (((HttpException) e).code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                    callback.unAuthorized();
                    return;
                }
            }
            String message = callback.getErrorMessage(e);
            callback.failure(message != null ? message : e.getMessage(), listener);
        }
    }
}
