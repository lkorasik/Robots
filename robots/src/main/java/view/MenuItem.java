package view;

import model.Constants;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MenuItem extends JMenu {
    public MenuItem(String title, String description, int keyEvent) {
        super(title);
        setMnemonic(keyEvent);
        getAccessibleContext().setAccessibleDescription(description);
    }
}
