package view.exitDialoBuilder;

import controller.logController.LogWindowSource;
import model.Constants;
import model.logModel.LogChangeListener;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExitDialogBuilder implements InternalExitDialogBuilder, WindowExitDialogBuilder {
    private InternalFrameAdapter internalFrameAdapter;
    private WindowAdapter windowAdapter;
    private LogWindowSource source;

    public static InternalExitDialogBuilder getInstance(){
        return (InternalExitDialogBuilder) new ExitDialogBuilder();
    }

    public static WindowExitDialogBuilder getInstance(JFrame frame){
        return (WindowExitDialogBuilder) new ExitDialogBuilder(frame);
    }

    private ExitDialogBuilder() {
        internalFrameAdapter = new InternalFrameAdapter() {
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
                    e.getInternalFrame().dispose();

                    if (source != null)
                        source.unregisterListener((LogChangeListener) e.getInternalFrame());
                }
            }
        };
    }

    private ExitDialogBuilder(JFrame frame) {
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
                if (decision == JOptionPane.YES_OPTION) {

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
