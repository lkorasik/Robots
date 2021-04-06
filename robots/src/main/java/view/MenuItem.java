package view;

import model.Constants;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class MenuItem extends JMenuItem {
    public MenuItem(String title, int keyEvent, TypeMenuItem typeMenuItem) {
        super(title, keyEvent);

        addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName(), typeMenuItem);
            this.invalidate();
        });
    }

    private void setLookAndFeel(String className, TypeMenuItem typeMenuItem) {
        if (typeMenuItem == TypeMenuItem.Exit)
            this.processEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        else {
            try {
                UIManager.setLookAndFeel(className);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }
        }
    }

    private JMenuItem getExitItem() {
        JMenuItem exit = new JMenuItem(Constants.MainApplicationFrame.EXIT_MENU, KeyEvent.VK_S);
        exit.addActionListener(e -> {

            this.invalidate();
        });
        return exit;
    }


}
