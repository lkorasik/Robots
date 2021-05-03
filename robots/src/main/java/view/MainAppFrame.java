package view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.logController.Logger;
import controller.robotController.RobotController;
import model.Constants;
import model.robotModel.RobotLogic;
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
    private final GameFrame gameFrame;
    private final LogFrame logFrame;

    public MainAppFrame(RobotController robotController, RobotLogic robotLogic) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(Constants.MainApplicationFrame.INSET,
                Constants.MainApplicationFrame.INSET,
                screenSize.width - Constants.MainApplicationFrame.INSET * 2,
                screenSize.height - Constants.MainApplicationFrame.INSET * 2);
        setContentPane(desktopPane);

        logFrame = new LogFrame(Logger.getDefaultLogSource());
        gameFrame = new GameFrame(robotController, robotLogic);

        setJMenuBar(createMenuBar());

        setExitDialog();

        createLastSessionDialog();
    }

    private void createLastSessionDialog() {
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
                    loadFramesUsingJson();
                } else {
                    loadFrameDefaultValues();
                }
            }
        });
    }

    private void loadFrameDefaultValues(){
        var size = new Dimension(Constants.PropertyDefaultValues.LOG_FRAME_WIDTH, Constants.PropertyDefaultValues.LOG_FRAME_HEIGHT);
        var location = new Point(Constants.PropertyDefaultValues.LOG_FRAME_POSITION_X, Constants.PropertyDefaultValues.LOG_FRAME_POSITION_Y);
        Logger.debug(Constants.MainApplicationFrame.PROTOCOL_WORKING);
        updateFrame(size, location, logFrame);

        size = new Dimension(Constants.PropertyDefaultValues.GAME_FRAME_WIDTH, Constants.PropertyDefaultValues.GAME_FRAME_HEIGHT);
        location = new Point(Constants.PropertyDefaultValues.GAME_FRAME_POSITION_X, Constants.PropertyDefaultValues.GAME_FRAME_POSITION_Y);
        updateFrame(size, location, gameFrame);
    }

    private void loadFramesUsingJson(){
        try {
            HashMap<String, InternalFrameClosing> map = new ObjectMapper().readValue(new File(SerializeInfo.CONFIG), new TypeReference<HashMap<String, InternalFrameClosing>>() {});

            var sourceFrame = map.get(GameFrame.class.getName());
            updateFrame(sourceFrame.getSize(), sourceFrame.getLocation(), gameFrame);

            sourceFrame = map.get(LogFrame.class.getName());
            updateFrame(sourceFrame.getSize(), sourceFrame.getLocation(), logFrame);
            Logger.debug(Constants.MainApplicationFrame.PROTOCOL_WORKING);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void updateFrame(Dimension size, Point location, InternalFrameClosing target){
        target.setMainParams(size, location);
        addWindow(target);
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
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(SerializeInfo.CONFIG), map);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
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
