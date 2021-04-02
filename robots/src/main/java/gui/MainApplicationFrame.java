package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(Constants.MainApplicationFrame.INSET,
                Constants.MainApplicationFrame.INSET,
                screenSize.width - Constants.MainApplicationFrame.INSET * 2,
                screenSize.height - Constants.MainApplicationFrame.INSET * 2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(Constants.MainApplicationFrame.WIDTH, Constants.MainApplicationFrame.HEIGHT);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

        setExitDialog();
    }

    private void setExitDialog(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {Constants.ExitPaneOptions.YES, Constants.ExitPaneOptions.NO};
                var decision = JOptionPane
                        .showOptionDialog(
                                e.getWindow(),
                                Constants.ExitPaneOptions.WINDOW_MESSAGE,
                                Constants.ExitPaneOptions.WINDOW_TITLE,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                if (decision == 0) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(Constants.MainApplicationFrame.PROTOCOL_WORKING);
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(getLookAndFeel());
        menuBar.add(getTestMenu());
        return menuBar;
    }

    private JMenu getLookAndFeel() {
        JMenu lookAndFeelMenu = new JMenu(Constants.MainApplicationFrame.DISPLAY_MODE_MENU);
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(Constants.MainApplicationFrame.DISPLAY_MODE_MENU_DESCRIPTION);

        lookAndFeelMenu.add(getSystemLookAndFeel());
        lookAndFeelMenu.add(getCrossPlatformLookAndFeel());
        lookAndFeelMenu.add(getExitItem());
        return lookAndFeelMenu;
    }

    private JMenuItem getSystemLookAndFeel() {
        JMenuItem systemLookAndFeel = new JMenuItem(Constants.MainApplicationFrame.SYSTEM_SCHEME, KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        return systemLookAndFeel;
    }

    private JMenuItem getExitItem() {
        JMenuItem exit = new JMenuItem(Constants.MainApplicationFrame.EXIT_MENU, KeyEvent.VK_S);
        exit.addActionListener(e -> {
            this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        return exit;
    }

    private JMenuItem getCrossPlatformLookAndFeel() {
        JMenuItem crossPlatformLookAndFeel = new JMenuItem(Constants.MainApplicationFrame.UNIVERSAL_SCHEME, KeyEvent.VK_S);
        crossPlatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        return crossPlatformLookAndFeel;
    }

    private JMenu getTestMenu() {
        JMenu testMenu = new JMenu(Constants.MainApplicationFrame.TEST_MENU);
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                Constants.MainApplicationFrame.TEST_MENU_DESCRIPTION);
        testMenu.add(getLogMessageItem());
        return testMenu;
    }

    private JMenuItem getLogMessageItem() {
        JMenuItem addLogMessageItem = new JMenuItem(Constants.MainApplicationFrame.TESTS_MESSAGE_TO_LOG, KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(Constants.MainApplicationFrame.LOG_MESSAGE);
        });
        return addLogMessageItem;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
