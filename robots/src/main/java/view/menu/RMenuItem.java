package view.menu;


import translation.LanguageBundle;
import translation.LanguageChangeListener;
import translation.LocalizationTextKeys;

import javax.swing.*;

public class RMenuItem extends JMenu implements LanguageChangeListener {
    private final LocalizationTextKeys translationKey;
    private final LocalizationTextKeys descriptionTranslationKey;

    public RMenuItem(LocalizationTextKeys key, LocalizationTextKeys descriptionKey, int keyEvent) {
        super(LanguageBundle.getInstance().getString(key.getKey()));
        translationKey = key;
        descriptionTranslationKey = descriptionKey;
        setMnemonic(keyEvent);
        getAccessibleContext().setAccessibleDescription(LanguageBundle.getInstance().getString(descriptionTranslationKey.getKey()));
        LanguageBundle.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onChange() {
        setText(LanguageBundle.getInstance().getString(translationKey.getKey()));
        getAccessibleContext().setAccessibleDescription(descriptionTranslationKey.getKey());
    }
}
