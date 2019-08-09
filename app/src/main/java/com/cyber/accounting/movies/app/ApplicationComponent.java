package com.cyber.accounting.movies.app;

import com.cyber.accounting.movies.app.domain.modules.ApiModule;
import com.cyber.accounting.movies.app.domain.modules.ApplicationModule;
import com.cyber.accounting.movies.app.domain.modules.PreferencesModule;
import com.cyber.accounting.movies.app.presentation.presenters.movie.MoviesPresenterImp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Yasser.Ibrahim on 6/28/2018.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class, PreferencesModule.class})
public interface ApplicationComponent {
    void inject(MoviesPresenterImp presenter);
}