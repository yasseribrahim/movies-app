package com.cyber.accounting.movies.app.domain.controller;

import com.cyber.accounting.movies.app.BuildConfig;
import com.cyber.accounting.movies.app.domain.models.movies.MovieDetails;
import com.cyber.accounting.movies.app.domain.models.movies.Movies;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by interactive on 7/23/18.
 */

public interface Controller {
    interface NotificationController {
        @GET(BuildConfig.BASE_MOVIE_URL + "{filter}")
        Observable<Movies> getMovies(@Path("filter") String filter, @Query("api_key") String apiKey, @Query("page") int page);

        @GET(BuildConfig.BASE_MOVIE_URL + "{movie_id}")
        Observable<MovieDetails> getMovieDetails(@Path("movie_id") long movieId, @Query("api_key") String apiKey);
    }
}
