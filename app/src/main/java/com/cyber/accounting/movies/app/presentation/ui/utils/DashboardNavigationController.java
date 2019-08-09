package com.cyber.accounting.movies.app.presentation.ui.utils;

import android.support.design.internal.TextScale;
import android.support.design.widget.BottomNavigationView;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.view.animation.FastOutSlowInInterpolator;

import java.lang.ref.WeakReference;

/**
 * Created by Amr.Ibrahim on 4/3/2017.
 */
public class DashboardNavigationController {

    private WeakReference<BottomNavigationView> bottomNavigationViewWeakReference;
    private final TransitionSet transitionSet;
    private int activeTabPosition = 0;
    private static final long ACTIVE_ANIMATION_DURATION_MS = 115L;

    public DashboardNavigationController(BottomNavigationView bottomNavigationView) {
        bottomNavigationViewWeakReference = new WeakReference<>(bottomNavigationView);

//XXX copy of BottomNavigationAnimationHelperIcs transition set
        transitionSet = new AutoTransition();
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        transitionSet.setDuration(ACTIVE_ANIMATION_DURATION_MS);
        transitionSet.setInterpolator(new FastOutSlowInInterpolator());
        TextScale textScale = new TextScale();
        transitionSet.addTransition(textScale);
    }

    public void setNavigationTab(int position) {
//XXX copy of BottomNavigationMenuView.activateNewButton

        if (activeTabPosition == position) {
            return;
        } else {
            activeTabPosition = position;
        }

        TransitionManager.beginDelayedTransition(bottomNavigationViewWeakReference.get(), transitionSet);

        bottomNavigationViewWeakReference.get().getMenu().getItem(position).setChecked(true);
    }
}