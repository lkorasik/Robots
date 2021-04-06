package view.menu;

import controller.LogController.Logger;
import model.Constants;
import view.MainAppFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class MenuInternalItem extends JMenuItem {
    public MenuInternalItem(String title, int keyEvent, TypeMenuItem typeMenuItem, MainAppFrame mainAppFrame) {
        super(title, keyEvent);
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
}

