package view.frameClosing;

import model.Constants;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameClosing extends JFrame {
    public FrameClosing() {
        super();
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
                if (decision == JOptionPane.YES_OPTION)
                    FrameClosing.this.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
                else
                    FrameClosing.this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }
}
