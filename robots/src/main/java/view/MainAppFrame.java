package view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.logController.Logger;
import controller.robotController.RobotController;
import fileWorker.FileWorker;
import model.Constants;
import model.robotModel.RobotLogic;
import serialization.SerializeInfo;
import translation.LanguageBundle;
import translation.LanguageChangeListener;
import translation.Locales;
import translation.LocalizationTextKeys;
import view.frameClosing.FrameClosing;
import view.frameClosing.InternalFrameClosing;
import view.logFrame.LogFrame;
import view.menu.MenuInternalItem;
import view.menu.MenuItem;
import view.menu.TypeMenuItem;
import view.robotFrame.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


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

        LanguageBundle.getInstance().addLanguageChangeListener(() -> setJMenuBar(createMenuBar()));

        logFrame = new LogFrame(Logger.getDefaultLogSource());
        gameFrame = new GameFrame(robotController, robotLogic);

        var split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, logFrame.getView(), gameFrame.getView());
        setContentPane(split);

        setJMenuBar(createMenuBar());

        setExitDialog();

        createLastSessionDialog();
    }

    protected void addWindow(JInternalFrame frame) {
        //desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void createLastSessionDialog() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                if (FileWorker.existsFile(SerializeInfo.CONFIG_FRAME_FILE)) {
                    Object[] options = {Constants.ExitPaneOptions.YES, Constants.ExitPaneOptions.NO};
                    var decision = JOptionPane
                            .showOptionDialog(
                                    e.getWindow(),
                                    Constants.PreviousSessionOptions.MESSAGE,
                                    Constants.PreviousSessionOptions.TITLE,
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[0]);
                    if (decision == JOptionPane.YES_OPTION) {
                        loadFramesUsingJson();
                    } else {
                        loadFrameDefaultValues();
                    }
                } else {
                    loadFrameDefaultValues();
                }
            }
        });
    }

    private void loadFrameDefaultValues() {
        var size = new Dimension(Constants.PropertyFrame.LOG_FRAME_WIDTH, Constants.PropertyFrame.LOG_FRAME_HEIGHT);
        var location = new Point(Constants.PropertyFrame.LOG_FRAME_POS_X, Constants.PropertyFrame.LOG_FRAME_POS_Y);
        updateFrame(size, location, logFrame);

        size = new Dimension(Constants.PropertyFrame.GAME_FRAME_WIDTH, Constants.PropertyFrame.GAME_FRAME_HEIGHT);
        location = new Point(Constants.PropertyFrame.GAME_FRAME_POS_X, Constants.PropertyFrame.GAME_FRAME_POS_Y);
        updateFrame(size, location, gameFrame);
    }

    private void loadFramesUsingJson() {
        if (FileWorker.existsFile(SerializeInfo.CONFIG_FRAME_FILE))
            try {
                HashMap<String, InternalFrameClosing> map = new ObjectMapper().readValue(
                        new File(SerializeInfo.CONFIG_FRAME_FILE),
                        new TypeReference<>() {
                        });

                var sourceFrame = map.get(GameFrame.class.getName());
                updateFrame(sourceFrame.getSize(), sourceFrame.getLocation(), gameFrame);

                sourceFrame = map.get(LogFrame.class.getName());
                updateFrame(sourceFrame.getSize(), sourceFrame.getLocation(), logFrame);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        else {
            loadFrameDefaultValues();
        }
    }

    private void updateFrame(Dimension size, Point location, InternalFrameClosing internalFrame) {
        internalFrame.setMainParams(size, location);
        addWindow(internalFrame);
    }

    private void setExitDialog() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                var map = new HashMap<String, InternalFrameClosing>();
                map.put(GameFrame.class.getName(), gameFrame);
                map.put(LogFrame.class.getName(), logFrame);

                try {
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(
                            new File(SerializeInfo.CONFIG_FRAME_FILE), map);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createProgramMenuItem());
        menuBar.add(createThemeMenuItem());
        menuBar.add(createLogMenuItem());
        menuBar.add(createLanguageMenuItem());

        return menuBar;
    }

    private MenuItem createProgramMenuItem() {
        var programMenuItem = new MenuItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.TOOLBAR_PROGRAM),
                LanguageBundle.getInstance().getString(LocalizationTextKeys.PROGRAM_MENU_DESCRIPTION),
                KeyEvent.VK_P);
        programMenuItem.add(new MenuInternalItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.PROGRAM_EXIT),
                KeyEvent.VK_S,
                TypeMenuItem.EXIT,
                this));
        return programMenuItem;
    }

    private MenuItem createThemeMenuItem() {
        var menuItem = new MenuItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.TOOLBAR_VIEW_MODE),
                LanguageBundle.getInstance().getString(LocalizationTextKeys.VIEW_MODE_MENU_DESCRIPTION),
                KeyEvent.VK_T);
        //TODO: In MainAppFrame display mode, In LocalizationTextKeys view mode
        menuItem.add(new MenuInternalItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.VIEW_MODE_SYSTEM_SCHEME),
                KeyEvent.VK_S,
                TypeMenuItem.THEME_SYSTEM,
                this));
        menuItem.add(new MenuInternalItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.VIEW_MODE_UNIVERSAL_SCHEME),
                KeyEvent.VK_S,
                TypeMenuItem.THEME_UNIVERSAL,
                this));
        return menuItem;
    }

    private MenuItem createLogMenuItem() {
        var logMenuItem = new MenuItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.TOOLBAR_TESTS),
                LanguageBundle.getInstance().getString(LocalizationTextKeys.TESTS_MENU_DESCRIPTION),
                KeyEvent.VK_L);
        logMenuItem.add(new MenuInternalItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.TESTS_MESSAGE_TO_LOG),
                KeyEvent.VK_S,
                TypeMenuItem.LOG,
                this));
        return logMenuItem;
    }

    private MenuItem createLanguageMenuItem() {
        var menuItem = new MenuItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.TOOLBAR_LANGUAGE),
                LanguageBundle.getInstance().getString(LocalizationTextKeys.LANGUAGE_MENU_DESCRIPTION),
                KeyEvent.VK_A);
        menuItem.add(new MenuInternalItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.LANGUAGE_ENGLISH),
                KeyEvent.VK_E,
                TypeMenuItem.LANGUAGE,
                this
        ));
        menuItem.add(new MenuInternalItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.LANGUAGE_RUSSIAN),
                KeyEvent.VK_R,
                TypeMenuItem.LANGUAGE,
                this
        ));
        menuItem.add(new MenuInternalItem(
                LanguageBundle.getInstance().getString(LocalizationTextKeys.LANGUAGE_CZECH),
                KeyEvent.VK_C,
                TypeMenuItem.LANGUAGE,
                this
        ));

        return menuItem;
    }
}
