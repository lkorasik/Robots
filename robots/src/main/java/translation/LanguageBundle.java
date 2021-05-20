package translation;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageBundle {
    private final String filename = "localization/lang";
    private ResourceBundle bundle;
    private final ArrayList<LanguageChangeListener> listeners;
    private static LanguageBundle instance;

    public static void create(Locales locale) {
        if (instance == null)
            instance = new LanguageBundle(locale);
    }

    public static LanguageBundle getInstance() {
        return instance;
    }

    public void addLanguageChangeListener(LanguageChangeListener listener) {
        listeners.add(listener);
    }

    public void changeLanguage(Locales locale) {
        bundle = ResourceBundle.getBundle(filename, new Locale(locale.getLocale()));

        for (var listener : listeners)
            listener.onChange();
    }

    private LanguageBundle(Locales locale) {
        bundle = ResourceBundle.getBundle(filename, new Locale(locale.getLocale()));
        listeners = new ArrayList<>();
    }

    public String getString(String key) {
        return bundle.getString(key);
    }
}

