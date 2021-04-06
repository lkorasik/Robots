package view;

import controller.LogController.LogWindowSource;
import model.Constants;
import model.logModel.LogChangeListener;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExitDialogBuilder {
    private InternalFrameAdapter internalFrameAdapter;
    private WindowAdapter windowAdapter;
    private LogWindowSource source;

    public ExitDialogBuilder() {
        internalFrameAdapter = new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                Object[] options = {Constants.ExitPaneOptions.YES, Constants.ExitPaneOptions.NO};
                var decision = JOptionPane
                        .showOptionDialog(
                                e.getInternalFrame(),
                                Constants.ExitPaneOptions.WINDOW_TITLE,
                                Constants.ExitPaneOptions.WINDOW_TITLE,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                if (decision == 0) {
                    e.getInternalFrame().dispose();

                    if (source != null)
                        source.unregisterListener((LogChangeListener) e.getInternalFrame());
                }
            }
        };
    }

    public ExitDialogBuilder(JFrame frame) {
        windowAdapter = new WindowAdapter() {
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
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        };
    }

    public ExitDialogBuilder setSource(LogWindowSource source) {
        this.source = source;

        return this;
    }

    public InternalFrameAdapter buildInternalFrameAdapter() {
        return internalFrameAdapter;
    }

    public WindowAdapter buildWindowAdapter() {
        return windowAdapter;
    }
}
