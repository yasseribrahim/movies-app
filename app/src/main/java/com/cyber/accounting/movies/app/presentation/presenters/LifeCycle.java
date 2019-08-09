package com.cyber.accounting.movies.app.presentation.presenters;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface LifeCycle {
    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
