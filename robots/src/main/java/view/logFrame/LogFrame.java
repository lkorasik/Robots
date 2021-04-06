package view.logFrame;

import model.logModel.LogChangeListener;
import model.logModel.LogEntry;
import controller.LogController.LogWindowSource;
import model.Constants;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;

public class LogFrame extends JInternalFrame implements LogChangeListener {
    private LogWindowSource windowSource;
    private TextArea logContent;

    public LogFrame(LogWindowSource windowSource) {
        super(Constants.LogWindow.WINDOW_TITLE, true, true, true, true);
        this.windowSource = windowSource;
        this.windowSource.registerListener(this);

        logContent = new TextArea("");
        logContent.setSize(Constants.LogWindow.WIDTH, Constants.LogWindow.HEIGHT);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();

        setExitDialog();
    }

    private void setExitDialog() {
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);

        addInternalFrameListener(new InternalFrameAdapter() {
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
                    //e.getInternalFrame().setVisible(false);
                    e.getInternalFrame().dispose();
                    windowSource.unregisterListener((LogChangeListener) e.getInternalFrame());
                }
            }
        });
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : windowSource.getLogEntries()) {
            content.append(entry.getMessage()).append("\n");
        }
        logContent.setText(content.toString());
        logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
