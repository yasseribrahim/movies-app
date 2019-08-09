package com.cyber.accounting.movies.app.presentation.ui.utils.paper;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PaperUtils {
    public static final String NOTIFICATIONS_MARKED_KEY = "NOTIFICATIONS_MARKED_KEY";

    public static List<Long> getNotificationsMarked() {
        return Paper.book().read(NOTIFICATIONS_MARKED_KEY, new ArrayList<>());
    }

    public static void saveNotificationsMarked(List<Long> marked) {
        Paper.book().write(NOTIFICATIONS_MARKED_KEY, marked);
    }
}
