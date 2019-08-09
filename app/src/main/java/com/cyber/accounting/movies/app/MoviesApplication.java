package com.cyber.accounting.movies.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.cyber.accounting.movies.app.domain.modules.ApiModule;

import java.io.File;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */
public class MoviesApplication extends MultiDexApplication {
    private static ApplicationComponent component;
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        application = this;

        Fabric.with(this, new Crashlytics());

        Paper.init(this);

        buildDagger();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void buildDagger() {
        component = DaggerApplicationComponent.
                builder().
                apiModule(new ApiModule(new File(getCacheDir(), "responses"), this, BuildConfig.HOST)).
                build();
    }
}