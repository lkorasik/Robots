package view.logFrame;

import controller.logController.LogWindowSource;
import model.Constants;
import model.logModel.LogChangeListener;
import model.logModel.LogEntry;
import view.frameClosing.InternalFrameClosing;

import javax.swing.*;
import java.awt.*;

public class LogFrame extends InternalFrameClosing implements LogChangeListener {
    private final LogWindowSource windowSource;
    private final JTextArea logContent;

    public LogFrame(LogWindowSource windowSource) {
        super(Constants.LogWindow.WINDOW_TITLE, true, true, true, true);
        this.windowSource = windowSource;
        this.windowSource.registerListener(this);

        logContent = new JTextArea("");
        logContent.setEditable(false);
        logContent.setFocusable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();

        setExitDialog();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    public JTextArea getView() {
        return logContent;
    }

    private void setExitDialog() {
        setActionExiting(() -> {
            if (windowSource != null)
                windowSource.unregisterListener(this);
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
}
