package com.cyber.accounting.movies.app.presentation.ui.utils;

import java.util.Locale;

public class LanguageUtil {
    public static String getLanguageName(String language) {
        try {
            return new Locale(language).getDisplayLanguage();
        } catch (Exception ex) {

        }

        return language;
    }
}
