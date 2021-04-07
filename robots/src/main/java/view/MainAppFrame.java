package view;

import controller.LogController.Logger;
import controller.robotController.RobotController;
import model.Constants;
import model.property.PropertyContainer;
import model.property.PropertyWorker;
import model.robotModel.RobotLogic;
import view.exitDialoBuilder.ExitDialogBuilder;
import view.logFrame.LogFrame;
import view.menu.MenuInternalItem;
import view.menu.MenuItem;
import view.menu.TypeMenuItem;
import view.robotFrame.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class MainAppFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainAppFrame(RobotController robotController, RobotLogic robotLogic) {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(Constants.MainApplicationFrame.INSET,
                Constants.MainApplicationFrame.INSET,
                screenSize.width - Constants.MainApplicationFrame.INSET * 2,
                screenSize.height - Constants.MainApplicationFrame.INSET * 2);
        setContentPane(desktopPane);

        var configuration = PropertyWorker.load();

        createLogWindow(configuration);

        createGameWindow(configuration, robotController, robotLogic);

        setJMenuBar(createMenuBar());

        setExitDialog();
    }

    private void setExitDialog() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(ExitDialogBuilder.getInstance(this).buildWindowAdapter());
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void createGameWindow(PropertyContainer configuration, RobotController robotController, RobotLogic robotLogic){
        GameFrame gameFrame = new GameFrame(robotController, robotLogic);
        gameFrame.setSize(configuration.gameFrameWidth, configuration.gameFrameHeight);
        gameFrame.setLocation(configuration.gameFramePositionX, configuration.gameFramePositionY);
        addWindow(gameFrame);
    }

    private void createLogWindow(PropertyContainer configuration){
        LogFrame logWindow = new LogFrame(Logger.getDefaultLogSource());
        logWindow.setSize(configuration.logFrameWidth, configuration.logFrameHeight);
        logWindow.setLocation(configuration.logFramePositionX, configuration.logFramePositionY);
        addWindow(logWindow);
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
