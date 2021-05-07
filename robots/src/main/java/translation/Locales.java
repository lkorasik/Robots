package translation;

import java.util.Locale;

public enum Locales {
    RU("ru", "Русский"),
    CZ("cz", "Čeština"),
    EN("en", "English");

    private final String locale;
    private final String fullName;

    Locales(String locale, String fullName) {
        this.locale = locale;
        this.fullName = fullName;
    }

    public String getLocale(){
        return locale;
    }

    public String getFullName(){
        return fullName;
    }
}
