package view.robotView;

import controller.robotController.RobotController;
import model.Constants;
import model.robotModel.RobotLogic;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;

public class GameWindow extends JInternalFrame {
    private final RobotVisualizer robotVisualizer;

    public GameWindow(RobotController robotController, RobotLogic robotLogic) {
        super(Constants.GameWindow.WINDOW_TITLE, true, true, true, true);
        robotVisualizer = new RobotVisualizer(robotController, robotLogic);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(robotVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        setCloseDialog();
    }

    private void setCloseDialog(){
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);

        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                Object[] options = {Constants.ExitPaneOptions.YES, Constants.ExitPaneOptions.NO};
                var decision = JOptionPane
                        .showOptionDialog(e.getInternalFrame(), Constants.ExitPaneOptions.WINDOW_MESSAGE,
                                Constants.ExitPaneOptions.WINDOW_TITLE, JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (decision == 0) {
                    //e.getInternalFrame().setVisible(false);
                    e.getInternalFrame().dispose();
                }
            }
        });
    }
}
