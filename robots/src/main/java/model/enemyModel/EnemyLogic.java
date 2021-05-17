package model.enemyModel;


import java.awt.*;
import java.awt.geom.Point2D;

public class EnemyLogic {
    private volatile double robotDirection = 0;
    private final Point2D robotPosition = new Point2D.Double(0, 0);
    private final Point2D targetPosition = new Point2D.Double(0, 0);

    public double getRobotDirection() {
        return robotDirection;
    }

    public Point2D getTargetPosition() {
        return targetPosition;
    }

    public Point2D getRobotPosition() {
        return robotPosition;
    }


    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private void moveRobot(double angularVelocity, int width, int height) {
        var newDirection = robotDirection + angularVelocity * Constants.Robot.DEFAULT_DURATION;

        double newX = robotPositionX + Constants.Robot.MAX_VELOCITY / angularVelocity * (Math.sin(newDirection) - Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPositionX + Constants.Robot.MAX_VELOCITY * Constants.Robot.DEFAULT_DURATION * Math.cos(robotDirection);
        }

        double newY = robotPositionY - Constants.Robot.MAX_VELOCITY / angularVelocity * (Math.cos(newDirection) - Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPositionY + Constants.Robot.MAX_VELOCITY * Constants.Robot.DEFAULT_DURATION * Math.sin(robotDirection);
        }

        robotPositionX = newX;
        robotPositionY = newY;
        robotDirection = asNormalizedRadians(correctDirection(newDirection, width, height));
    }

    private double correctDirection(double direction, int width, int height) {
        if ((robotPositionX <= 0) || (robotPositionY >= height))
            return direction + Math.PI;
        else if ((robotPositionX >= width) || (robotPositionY <= 0))
            return direction - Math.PI;
        return direction;
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    public static int round(double value) {
        return (int) (value + 0.5);
    }

    public void setTargetPosition(Point point) {
        targetPositionX = point.x;
        targetPositionY = point.y;
    }

    private double getAngularVelocity() {
        double angularVelocity = 0;
        double angleToTarget = angleTo(robotPositionX, robotPositionY, targetPositionX, targetPositionY);
        if (angleToTarget > robotDirection) {
            angularVelocity = Constants.Robot.MAX_ANGULAR_VELOCITY;
        }
        if (angleToTarget < robotDirection) {
            angularVelocity = -Constants.Robot.MAX_ANGULAR_VELOCITY;
        }
        return angularVelocity;
    }

    private void keepTargetInRectangle(int width, int height) {
        if (targetPositionX > width)
            targetPositionX = width;
        else if (targetPositionX < 0)
            targetPositionX = 0;
        else if (targetPositionY < 0)
            targetPositionY = 0;
        else if (targetPositionY > height)
            targetPositionY = height;
    }

    public void onModelUpdateEvent(int width, int height) {
        keepTargetInRectangle(width, height);

        double distance = distance(targetPositionX, targetPositionY, robotPositionX, robotPositionY);
        if (distance < 0.5)
            return;
        if (robotPositionX > width)
            robotPositionX = width;
        else if (robotPositionX < 0)
            robotPositionX = 0;
        else if (robotPositionY < 0)
            robotPositionY = 0;
        else if (robotPositionY > height)
            robotPositionY = height;
        else
            moveRobot(getAngularVelocity(), width, height);
    }


}
