package view.robotView;

import controller.robotController.RobotController;
import model.robotModel.RobotLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RobotVisualizer extends JPanel {
    private RobotController robotController;
    private RobotLogic robotLogic;

    public RobotVisualizer(RobotController robotController, RobotLogic robotLogic) {
        this.robotController = robotController;
        this.robotLogic = robotLogic;

        this.robotController.setRobotVisualizer(this);
        this.robotController.initEventTimer();
    }

    private static void fillOval(Graphics graphics, int centerX, int centerY, int diam1, int diam2) {
        graphics.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics graphics, int centerX, int centerY, int diam1, int diam2) {
        graphics.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    public void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    public void drawRobot(Graphics2D graphics2D, int x, int y, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        graphics2D.setTransform(t);
        graphics2D.setColor(Color.MAGENTA);
        fillOval(graphics2D, x, y, 30, 10);
        graphics2D.setColor(Color.BLACK);
        drawOval(graphics2D, x, y, 30, 10);
        graphics2D.setColor(Color.WHITE);
        fillOval(graphics2D, x + 10, y, 5, 5);
        graphics2D.setColor(Color.BLACK);
        drawOval(graphics2D, x + 10, y, 5, 5);
    }

    public void drawTarget(Graphics2D graphics2D, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        graphics2D.setTransform(t);
        graphics2D.setColor(Color.GREEN);
        fillOval(graphics2D, x, y, 5, 5);
        graphics2D.setColor(Color.BLACK);
        drawOval(graphics2D, x, y, 5, 5);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        drawRobot(g2d, RobotLogic.round(
                robotLogic.getRobotPositionX()),
                RobotLogic.round(robotLogic.getRobotPositionY()),
                robotLogic.getRobotDirection());
        drawTarget(g2d, robotLogic.getTargetPositionX(), robotLogic.getTargetPositionY());
    }
}
