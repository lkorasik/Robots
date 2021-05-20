package view;

import controller.logController.Logger;
import controller.robotController.RobotController;
import model.Constants;
import model.robotModel.RobotLogic;
import translation.LocalizationTextKeys;
import view.frameClosing.FrameClosing;
import view.logFrame.LogFrame;
import view.menu.RMenuInternalItem;
import view.menu.RMenuItem;
import view.menu.TypeMenuItem;
import view.robotFrame.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class MainAppFrame extends FrameClosing {
    private final GameFrame gameFrame;
    private final LogFrame logFrame;

    public MainAppFrame(RobotController robotController, RobotLogic robotLogic) {
        super();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(Constants.MainApplicationFrame.INSET,
                Constants.MainApplicationFrame.INSET,
                screenSize.width - Constants.MainApplicationFrame.INSET * 2,
                screenSize.height - Constants.MainApplicationFrame.INSET * 2);

        setMinimumSize(new Dimension(1200, 600));

        logFrame = new LogFrame(Logger.getDefaultLogSource());
        gameFrame = new GameFrame(robotController, robotLogic);

        var split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, logFrame.getView(), gameFrame.getView());
        setContentPane(split);

        setJMenuBar(createMenuBar());
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        menuBar.add(createProgramMenuItem());
        menuBar.add(createThemeMenuItem());
        menuBar.add(createLogMenuItem());
        menuBar.add(createLanguageMenuItem());

        return menuBar;
    }

    private RMenuItem createProgramMenuItem() {
        var programMenuItem = new RMenuItem(
                LocalizationTextKeys.TOOLBAR_PROGRAM,
                LocalizationTextKeys.PROGRAM_MENU_DESCRIPTION,
                KeyEvent.VK_P);

        var exitItem = new RMenuInternalItem(
                LocalizationTextKeys.PROGRAM_EXIT,
                KeyEvent.VK_S,
                TypeMenuItem.EXIT,
                this);
        programMenuItem.add(exitItem);

        return programMenuItem;
    }

    private RMenuItem createThemeMenuItem() {
        var menuItem = new RMenuItem(
                LocalizationTextKeys.TOOLBAR_VIEW_MODE,
                LocalizationTextKeys.VIEW_MODE_MENU_DESCRIPTION,
                KeyEvent.VK_T);
        //TODO: In MainAppFrame display mode, In LocalizationTextKeys view mode

        var systemSchemeItem = new RMenuInternalItem(
                LocalizationTextKeys.VIEW_MODE_SYSTEM_SCHEME,
                KeyEvent.VK_S,
                TypeMenuItem.THEME_SYSTEM,
                this);
        menuItem.add(systemSchemeItem);

        var universalSchemeItem = new RMenuInternalItem(
                LocalizationTextKeys.VIEW_MODE_UNIVERSAL_SCHEME,
                KeyEvent.VK_S,
                TypeMenuItem.THEME_UNIVERSAL,
                this);
        menuItem.add(universalSchemeItem);

        return menuItem;
    }

    private RMenuItem createLogMenuItem() {
        var logMenuItem = new RMenuItem(
                LocalizationTextKeys.TOOLBAR_TESTS,
                LocalizationTextKeys.TESTS_MENU_DESCRIPTION,
                KeyEvent.VK_L);

        var messageToLogItem = new RMenuInternalItem(
                LocalizationTextKeys.TESTS_MESSAGE_TO_LOG,
                KeyEvent.VK_S,
                TypeMenuItem.LOG,
                this);
        logMenuItem.add(messageToLogItem);

        return logMenuItem;
    }

    private RMenuItem createLanguageMenuItem() {
        var menuItem = new RMenuItem(
                LocalizationTextKeys.TOOLBAR_LANGUAGE,
                LocalizationTextKeys.LANGUAGE_MENU_DESCRIPTION,
                KeyEvent.VK_A);

        var englishItem = new RMenuInternalItem(
                LocalizationTextKeys.LANGUAGE_ENGLISH,
                KeyEvent.VK_E,
                TypeMenuItem.LANGUAGE,
                this);
        menuItem.add(englishItem);

        var russianItem = new RMenuInternalItem(
                LocalizationTextKeys.LANGUAGE_RUSSIAN,
                KeyEvent.VK_R,
                TypeMenuItem.LANGUAGE,
                this);
        menuItem.add(russianItem);

        var czechLanguage = new RMenuInternalItem(
                LocalizationTextKeys.LANGUAGE_CZECH,
                KeyEvent.VK_C,
                TypeMenuItem.LANGUAGE,
                this);
        menuItem.add(czechLanguage);

        return menuItem;
    }
}
