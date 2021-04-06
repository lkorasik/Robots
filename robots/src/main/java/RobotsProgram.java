import controller.robotController.RobotController;
import model.property.PropertyWorker;
import model.robotModel.RobotLogic;
import view.MainAppFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {
            RobotLogic robotLogic = new RobotLogic();
            MainAppFrame mainAppFrame = new MainAppFrame(new RobotController(robotLogic), robotLogic);
            mainAppFrame.pack();
            mainAppFrame.setVisible(true);
            mainAppFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
