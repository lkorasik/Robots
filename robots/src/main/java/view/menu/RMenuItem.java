package view.menu;


import javax.swing.*;

public class RMenuItem extends JMenu {
    public RMenuItem(String title, String description, int keyEvent) {
        super(title);
        setMnemonic(keyEvent);
        getAccessibleContext().setAccessibleDescription(description);
    }

    public void updateState(String title, String description){
        setText(title);
        getAccessibleContext().setAccessibleDescription(description);
    }
}
