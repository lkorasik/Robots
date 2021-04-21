package view;

import lombok.Setter;
import model.Constants;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class InternalFrameClosing extends JInternalFrame {

    @Setter
    private Runnable actionExiting = () -> {};

    public InternalFrameClosing(String title, Boolean resizable , Boolean closable, Boolean maximizable, Boolean iconf) {
        super(title, resizable, closable, maximizable, iconf);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                Object[] options = {Constants.ExitPaneOptions.YES, Constants.ExitPaneOptions.NO};
                var decision = JOptionPane
                        .showOptionDialog(
                                e.getInternalFrame(),
                                Constants.ExitPaneOptions.WINDOW_MESSAGE,
                                Constants.ExitPaneOptions.WINDOW_TITLE,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                if (decision == JOptionPane.YES_OPTION) {
                    actionExiting.run();
                    e.getInternalFrame().dispose();

                }
            }
        });
    }


}
