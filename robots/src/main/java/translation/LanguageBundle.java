package translation;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageBundle {
    private final String filename = "localization/lang";
    private final ResourceBundle bundle;
    private static LanguageBundle instance;

    public static LanguageBundle getInstance(Locales locale){
        if (instance == null)
            instance = new LanguageBundle(new Locale(locale.getLocale()));

        return instance;
    }

    private LanguageBundle(Locale locale) {
        bundle = ResourceBundle.getBundle(filename, locale);
    }

    public String getString(String key){
        return bundle.getString(key);
    }
}
