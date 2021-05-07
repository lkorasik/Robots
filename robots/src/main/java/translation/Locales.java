package translation;

public enum Locales {
    RU("ru"),
    EN("en");

    private final String locale;

    Locales(String locale) {
        this.locale = locale;
    }

    public String getLocale(){
        return locale;
    }
}
