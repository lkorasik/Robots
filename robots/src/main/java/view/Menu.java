package view;

import model.Constants;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Menu extends JMenu {
    public Menu(String title, String description, int keyEvent) {
        super(title);
        setMnemonic(keyEvent);
        getAccessibleContext().setAccessibleDescription(description);
    }
}
