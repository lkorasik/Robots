package view.menu;


import translation.LanguageBundle;
import translation.LanguageChangeListener;
import translation.LocalizationTextKeys;

import javax.swing.*;

public class RMenuItem extends JMenu implements LanguageChangeListener {
    private final String translationKey;

    public RMenuItem(String key, String description, int keyEvent) {
        super(LanguageBundle.getInstance().getString(key));
        translationKey = key;
        setMnemonic(keyEvent);
        getAccessibleContext().setAccessibleDescription(description);
    }

    @Override
    public void onChange() {
        setText(LanguageBundle.getInstance().getString(translationKey));
    }
}
