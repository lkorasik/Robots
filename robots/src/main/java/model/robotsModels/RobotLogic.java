package model.robotsModels;

import model.Constants;

import java.awt.*;
import java.awt.geom.Point2D;

public class RobotLogic {
    protected volatile double robotDirection = 0;
    protected  Point2D targetPosition;
    protected volatile Point2D robotPosition;

    public RobotLogic() {
        robotPosition = new Point(0, 0);
        targetPosition = new Point(0, 0);
    }

    public int getPositionY() {
        return (int) robotPosition.getY();
    }

    public int getPositionX() {
        return (int) robotPosition.getX();
    }

    public static int round(double value) {
        return (int) (value + 0.5);
    }

    public double getRobotDirection() {
        return robotDirection;
    }

    public Point2D getRobotPosition() {
        return robotPosition;
    }

    protected static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    protected static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    protected static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    protected double getAngularVelocity() {
        double angularVelocity = 0;
        double angleToTarget = angleTo(robotPosition.getX(), robotPosition.getY(), targetPosition.getX(), targetPosition.getY());
        if (angleToTarget > robotDirection) {
            angularVelocity = Constants.Robot.MAX_ANGULAR_VELOCITY;
        }
        if (angleToTarget < robotDirection) {
            angularVelocity = -Constants.Robot.MAX_ANGULAR_VELOCITY;
        }
        return angularVelocity;
    }
}
