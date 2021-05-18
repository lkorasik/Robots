package view.menu;

import controller.logController.Logger;
import model.Constants;
import translation.LanguageBundle;
import translation.LanguageChangeListener;
import translation.Locales;
import view.MainAppFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class RMenuInternalItem extends JMenuItem implements LanguageChangeListener {
    private final String translationKey;

    public RMenuInternalItem(String key, int keyEvent, TypeMenuItem typeMenuItem, MainAppFrame mainAppFrame) {
        super(LanguageBundle.getInstance().getString(key), keyEvent);
        translationKey = key;
        switch (typeMenuItem) {
            case EXIT -> addActionListener(event ->
                    mainAppFrame.dispatchEvent(new WindowEvent(mainAppFrame, WindowEvent.WINDOW_CLOSING)));
            case LOG -> addActionListener(event ->
                    Logger.debug(Constants.MainApplicationFrame.LOG_MESSAGE));
            case THEME_SYSTEM -> addActionListener(event -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName(), mainAppFrame);
                mainAppFrame.invalidate();
            });
            case THEME_UNIVERSAL -> addActionListener(event -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName(), mainAppFrame);
                mainAppFrame.invalidate();
            });
            case LANGUAGE -> addActionListener(event -> {
                var lang = event.getActionCommand();

                if(lang.equals(Locales.RU.getFullName()))
                    LanguageBundle.getInstance().changeLanguage(Locales.RU);
                else if(lang.equals(Locales.CZ.getFullName()))
                    LanguageBundle.getInstance().changeLanguage(Locales.CZ);
                else
                    LanguageBundle.getInstance().changeLanguage(Locales.EN);

                mainAppFrame.invalidate();
            });
        }
    }

    private void setLookAndFeel(String className, MainAppFrame mainAppFrame) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(mainAppFrame);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }
    }

    @Override
    public void onChange() {
        setText(LanguageBundle.getInstance().getString(translationKey));
    }
}

