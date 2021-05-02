package view;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.logController.Logger;
import controller.robotController.RobotController;
import model.Constants;
import model.property.PropertyContainer;
import model.property.PropertyWorker;
import model.robotModel.RobotLogic;
import model.serialization.ConfigFieldName;
import model.serialization.InternalFrameSerializer;
import model.serialization.SerializeInfo;
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
    private final JDesktopPane desktopPane = new JDesktopPane();
    private GameFrame gameFrame;
    private final LogFrame logFrame;

    public MainAppFrame(RobotController robotController, RobotLogic robotLogic) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(Constants.MainApplicationFrame.INSET,
                Constants.MainApplicationFrame.INSET,
                screenSize.width - Constants.MainApplicationFrame.INSET * 2,
                screenSize.height - Constants.MainApplicationFrame.INSET * 2);
        setContentPane(desktopPane);

        var propertyContainer = PropertyWorker.instance.load();

        logFrame = new LogFrame(Logger.getDefaultLogSource());
        gameFrame = new GameFrame(robotController, robotLogic);

        setJMenuBar(createMenuBar());

        setExitDialog(propertyContainer);

        createLastSessionDialog(propertyContainer);
    }

    private void createLastSessionDialog(PropertyContainer propertyContainer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Object[] options = {Constants.ExitPaneOptions.YES, Constants.ExitPaneOptions.NO};
                var decision = JOptionPane
                        .showOptionDialog(
                                e.getWindow(),
                                Constants.LoadPreviousSessionOptions.MESSAGE,
                                Constants.LoadPreviousSessionOptions.TITLE,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                if (decision == JOptionPane.YES_OPTION) {
                    //createLogWindow(propertyContainer);
                    //createGameWindow(propertyContainer);

                    try {
                        var frame  = new ObjectMapper().readValue(new File(SerializeInfo.GAME_FRAME_FILENAME), InternalFrameClosing.class);
                        gameFrame.setSize(frame.getWidth(), frame.getHeight());
                        gameFrame.setLocation(frame.getX(), frame.getY());
                        addWindow(gameFrame);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    try {
                        var frame  = new ObjectMapper().readValue(new File(SerializeInfo.LOG_FRAME_FILENAME), InternalFrameClosing.class);
                        logFrame.setSize(frame.getWidth(), frame.getHeight());
                        logFrame.setLocation(frame.getX(), frame.getY());
                        addWindow(logFrame);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    createLogWindow();
                    createGameWindow();
                }
            }
        });
    }

    private void setExitDialog(PropertyContainer propertyContainer) {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //updateConfiguration(propertyContainer);
                //PropertyWorker.instance.save(propertyContainer);


                try {
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(SerializeInfo.GAME_FRAME_FILENAME), gameFrame);
                } catch (IOException exception){
                    exception.printStackTrace();
                }

                try{
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(SerializeInfo.LOG_FRAME_FILENAME), logFrame);
                } catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        });
    }

    private void updateConfiguration(PropertyContainer propertyContainer) {
        propertyContainer.logFramePositionX = logFrame.getX();
        propertyContainer.logFramePositionY = logFrame.getY();
        propertyContainer.logFrameWidth = logFrame.getWidth();
        propertyContainer.logFrameHeight = logFrame.getHeight();

        propertyContainer.gameFramePositionX = gameFrame.getX();
        propertyContainer.gameFramePositionY = gameFrame.getY();
        propertyContainer.gameFrameWidth = gameFrame.getWidth();
        propertyContainer.gameFrameHeight = gameFrame.getHeight();
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void createGameWindow(PropertyContainer propertyContainer) {
        gameFrame.setSize(propertyContainer.gameFrameWidth, propertyContainer.gameFrameHeight);
        gameFrame.setLocation(propertyContainer.gameFramePositionX, propertyContainer.gameFramePositionY);
        addWindow(gameFrame);
    }

    private void createLogWindow(PropertyContainer propertyContainer) {
        logFrame.setSize(propertyContainer.logFrameWidth, propertyContainer.logFrameHeight);
        logFrame.setLocation(propertyContainer.logFramePositionX, propertyContainer.logFramePositionY);
        addWindow(logFrame);
        Logger.debug(Constants.MainApplicationFrame.PROTOCOL_WORKING);
    }

    private void createGameWindow() {
        gameFrame.setSize(Constants.PropertyDefaultValues.GAME_FRAME_WIDTH, Constants.PropertyDefaultValues.GAME_FRAME_HEIGHT);
        gameFrame.setLocation(Constants.PropertyDefaultValues.GAME_FRAME_POSITION_X, Constants.PropertyDefaultValues.GAME_FRAME_POSITION_Y);
        addWindow(gameFrame);
    }

    private void createLogWindow() {
        logFrame.setSize(Constants.PropertyDefaultValues.LOG_FRAME_WIDTH, Constants.PropertyDefaultValues.LOG_FRAME_HEIGHT);
        logFrame.setLocation(Constants.PropertyDefaultValues.LOG_FRAME_POSITION_X, Constants.PropertyDefaultValues.LOG_FRAME_POSITION_Y);
        addWindow(logFrame);
        Logger.debug(Constants.MainApplicationFrame.PROTOCOL_WORKING);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createProgramMenuItem());
        menuBar.add(createThemeMenuItem());
        menuBar.add(createLogMenuItem());

        return menuBar;
    }

    private MenuItem createThemeMenuItem() {
        var menuItem = new MenuItem(
                Constants.MainApplicationFrame.DISPLAY_MODE_MENU,
                Constants.MainApplicationFrame.DISPLAY_MODE_MENU_DESCRIPTION,
                KeyEvent.VK_T);
        menuItem.add(new MenuInternalItem(
                Constants.MainApplicationFrame.SYSTEM_SCHEME,
                KeyEvent.VK_S,
                TypeMenuItem.THEME_SYSTEM,
                this));
        menuItem.add(new MenuInternalItem(
                Constants.MainApplicationFrame.UNIVERSAL_SCHEME,
                KeyEvent.VK_S,
                TypeMenuItem.THEME_UNIVERSAL,
                this));
        return menuItem;
    }

    private MenuItem createLogMenuItem() {
        var logMenuItem = new MenuItem(
                Constants.MainApplicationFrame.TEST_MENU,
                Constants.MainApplicationFrame.TEST_MENU_DESCRIPTION,
                KeyEvent.VK_L);
        logMenuItem.add(new MenuInternalItem(
                Constants.MainApplicationFrame.TESTS_MESSAGE_TO_LOG,
                KeyEvent.VK_S,
                TypeMenuItem.LOG,
                this));
        return logMenuItem;
    }

    private MenuItem createProgramMenuItem() {
        var programMenuItem = new MenuItem(
                Constants.MainApplicationFrame.PROGRAM_MENU,
                Constants.MainApplicationFrame.PROGRAM_MENU_DESCRIPTION,
                KeyEvent.VK_P);
        programMenuItem.add(new MenuInternalItem(
                Constants.MainApplicationFrame.EXIT_MENU,
                KeyEvent.VK_S,
                TypeMenuItem.EXIT,
                this));
        return programMenuItem;
    }

    /*
    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the lone menu.
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("New");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new");
//        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
//        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }
     */
}
