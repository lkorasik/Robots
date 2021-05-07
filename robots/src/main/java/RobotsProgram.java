import controller.robotController.RobotController;
import model.robotModel.RobotLogic;
import translation.LanguageBundle;
import translation.Locales;
import view.MainAppFrame;

import javax.swing.*;
import java.awt.*;

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

        LanguageBundle.create(Locales.EN);

        SwingUtilities.invokeLater(() -> {
            RobotLogic robotLogic = new RobotLogic();
            MainAppFrame mainAppFrame = new MainAppFrame(new RobotController(robotLogic), robotLogic);
            mainAppFrame.pack();
            mainAppFrame.setVisible(true);
            mainAppFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
