package model.robotsModels.playerModel;

import model.robotsModels.RobotLogic;

import java.awt.*;
import java.awt.geom.Point2D;

public class PlayerLogic extends RobotLogic {
    private Point2D predPosition;

    public PlayerLogic(){
        super();
        predPosition = new Point(0, 0);
    }

    public void setMovePosition(Point2D point) {
        predPosition = point;
    }

    public void setSeePosition(Point2D point) {
        targetPosition = point;
        robotDirection = angleTo(robotPosition.getX(), robotPosition.getY(), targetPosition.getX(), targetPosition.getY());
    }

    public void updatePosition(int width, int height) {
        if (predPosition.getX() > width)
            predPosition.setLocation(width, predPosition.getY());
        else if (predPosition.getX() < 0)
            predPosition.setLocation(0, predPosition.getY());
        else if (predPosition.getY() < 0)
            predPosition.setLocation(predPosition.getX(), 0);
        else if (predPosition.getY() > height)
            predPosition.setLocation(predPosition.getX(), height);
        else
            moveRobot();
    }

    protected void moveRobot() {
        robotDirection = angleTo(robotPosition.getX(), robotPosition.getY(), targetPosition.getX(), targetPosition.getY());
        robotPosition = predPosition;
    }
}
