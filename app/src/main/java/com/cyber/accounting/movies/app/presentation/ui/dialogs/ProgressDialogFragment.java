package com.cyber.accounting.movies.app.presentation.ui.dialogs;

/**
 * Created by Yasser.Ibrahim on 6/21/2018.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.cyber.accounting.movies.app.R;
import com.cyber.accounting.movies.app.presentation.ui.utils.Constants;

public class ProgressDialogFragment extends DialogFragment {
    private static final String TAG = ProgressDialogFragment.class.getSimpleName();

    public static void show(FragmentManager fm) {
        show(fm, R.string.dialog_msg_loading);
    }

    public static void show(FragmentManager fm, @StringRes int messageId) {
        // We're not using dialogFragment.show() method because we may call this DialogFragment
        // in onActivityResult() method and there will be a state loss exception

        if (fm.findFragmentByTag(TAG) == null) {
            fm.beginTransaction().add(newInstance(messageId), TAG).commitAllowingStateLoss();
        }
    }

    public static void hide(FragmentManager fm) {
        DialogFragment fragment = (DialogFragment) fm.findFragmentByTag(TAG);
        if (fragment != null) {
            fragment.dismissAllowingStateLoss();
        }
    }

    public static ProgressDialogFragment newInstance(@StringRes int messageId) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_MESSAGE_ID, messageId);

        ProgressDialogFragment dialog = new ProgressDialogFragment();
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(getArguments().getInt(Constants.ARG_MESSAGE_ID)));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        // Disable the back button
        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        };
        dialog.setOnKeyListener(keyListener);

        return dialog;
    }
}