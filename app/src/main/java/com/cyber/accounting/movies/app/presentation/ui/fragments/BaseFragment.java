package com.cyber.accounting.movies.app.presentation.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cyber.accounting.movies.app.R;
import com.cyber.accounting.movies.app.presentation.ui.dialogs.ProgressDialogFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

public abstract class BaseFragment extends Fragment {
    private AppCompatActivity activity;

    protected abstract View getSnackBarAnchorView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    public Snackbar showRetrySnackBar(String message, View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar.make(getSnackBarAnchorView(), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.label_retry, clickListener);
        snackbar.show();
        return snackbar;
    }

    public Snackbar showConnectionSnackBar(View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar.make(getSnackBarAnchorView(), R.string.message_error_connection, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.label_retry, clickListener);
        snackbar.show();
        return snackbar;
    }

    public Snackbar showInfoSnackBar(@StringRes int message) {
        Snackbar snackbar = Snackbar.make(getSnackBarAnchorView(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }

    public Snackbar showInfoSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getSnackBarAnchorView(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    public void onShowProgress() {
        ProgressDialogFragment.show(activity.getSupportFragmentManager());
    }

    public void onHideProgress() {
        ProgressDialogFragment.hide(activity.getSupportFragmentManager());
    }

    protected void showToastLong(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void onUnAuthorized() {
    }

    protected void showToastShort(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public String onGetErrorMessage(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            if (exception.response().code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                return getString(R.string.error_no_authorization);
            } else if (exception.response().code() == HttpsURLConnection.HTTP_BAD_REQUEST ||
                    exception.response().code() == HttpsURLConnection.HTTP_NOT_FOUND ||
                    exception.response().code() == HttpsURLConnection.HTTP_UNAVAILABLE ||
                    exception.response().code() == HttpsURLConnection.HTTP_INTERNAL_ERROR ||
                    exception.response().code() == HttpsURLConnection.HTTP_BAD_GATEWAY) {
                return getString(R.string.error_service_unavailable);
            } else {
                return getString(R.string.message_error_connection);
            }
        } else if (throwable instanceof SocketTimeoutException) {
            return getString(R.string.error_service_unavailable);
        } else if (throwable instanceof UnknownHostException) {
            return getString(R.string.message_error_connection);
        } else if (throwable instanceof ConnectException) {
            return getString(R.string.message_error_connection);
        } else if (throwable instanceof SSLHandshakeException) {
            return getString(R.string.message_error_connection);
        }
        return null;
    }
}
