package controller.robotController;

import model.robotsModels.PlayerRobotModel.PlayerRobotLogic;
import view.robotFrame.RobotVisualizer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;

public class RobotController {
    private Timer timer;
    private RobotVisualizer robotVisualizer;
    private final PlayerRobotLogic robotLogic;
    private Point sizeRobotVisualizer;

    public RobotController(PlayerRobotLogic robotLogic) {
        this.robotLogic = robotLogic;
    }

    public void setRobotVisualizer(RobotVisualizer robotVisualizer) {
        this.robotVisualizer = robotVisualizer;
        sizeRobotVisualizer = new Point(robotVisualizer.getWidth(),robotVisualizer.getHeight() );
    }

    public void initEventTimer() {
        timer = new Timer("events robot generator", true);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robotVisualizer.onRedrawEvent();
            }
        }, 0, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                System.out.println(robotVisualizer.getWidth());
                robotLogic.updatePosition(robotVisualizer.getWidth(), robotVisualizer.getHeight());
            }
        }, 0, 10);

        robotVisualizer.setDoubleBuffered(true);
    }

    public Point2D getRobotPosition() {
        return robotLogic.getRobotPosition();
    }

    public void setTargetPosition(Point2D moveTarget, Point2D seeTarget) {
        robotLogic.setMovePosition(moveTarget);
        robotLogic.setSeePosition(seeTarget);
    }

    public void setSeePosition(Point seeTarget) {
        robotLogic.setSeePosition(seeTarget);
    }
}
