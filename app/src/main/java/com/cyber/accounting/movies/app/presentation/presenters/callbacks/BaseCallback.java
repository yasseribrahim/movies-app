package com.cyber.accounting.movies.app.presentation.presenters.callbacks;

import android.view.View;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface BaseCallback {
    default void onShowConnectionError(View.OnClickListener onClickListener) {
    }

    default void onShowError(String message, View.OnClickListener onClickListener) {
    }

    default void onShowProgress() {
    }

    default void onHideProgress() {
    }

    default void onUnAuthorized() {
    }

    default String onGetErrorMessage(Throwable throwable) {
        return "";
    }
}
