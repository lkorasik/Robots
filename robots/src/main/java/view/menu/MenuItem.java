package view.menu;


import javax.swing.*;

public class MenuItem extends JMenu {
    public MenuItem(String title, String description, int keyEvent) {
        super(title);
        setMnemonic(keyEvent);
        getAccessibleContext().setAccessibleDescription(description);
    }

    public void updateState(String title, String description){
        setText(title);
        getAccessibleContext().setAccessibleDescription(description);
    }
}
