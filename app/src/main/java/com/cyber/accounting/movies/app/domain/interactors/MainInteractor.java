package com.cyber.accounting.movies.app.domain.interactors;

import android.view.View;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface MainInteractor {
    void onDestroy();

    interface CallbackStates {
        void failure(String message, View.OnClickListener onClickListener);

        void showProgress();

        void hideProgress();

        void unAuthorized();

        String getErrorMessage(Throwable throwable);
    }
}
