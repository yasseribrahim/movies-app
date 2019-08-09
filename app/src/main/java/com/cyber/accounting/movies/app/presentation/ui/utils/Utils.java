package com.cyber.accounting.movies.app.presentation.ui.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cyber.accounting.movies.app.MoviesApplication;

/**
 * Created by Yasser.Ibrahim on 6/28/2018.
 */

public class Utils {

    public static boolean isConnectingToInternet() {
        if (MoviesApplication.getApplication().getApplicationContext() != null) {

            ConnectivityManager manager = (ConnectivityManager) MoviesApplication.getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
