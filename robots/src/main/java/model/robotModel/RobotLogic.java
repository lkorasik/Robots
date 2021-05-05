package model.robotModel;

import lombok.Getter;
import model.Constants;

import java.awt.*;

public class RobotLogic {
    @Getter
    private volatile double robotPositionX = 100;
    @Getter
    private volatile double robotPositionY = 100;
    @Getter
    private volatile double robotDirection = 0;

    private final Point movePoint = new Point(100, 100);
    private final Point seePoint = new Point(100, 100);

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

    /*
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
    */

    public int getTargetPositionY(){
        return movePoint.y;
    }

    public int getTargetPositionX(){
        return movePoint.x;
    }

    private void moveRobot() {
        robotDirection = angleTo(movePoint.x, movePoint.y, seePoint.x, seePoint.y);
        robotPositionX = movePoint.x;
        robotPositionY = movePoint.y;
        //robotDirection = asNormalizedRadians(correctDirection(angularVelocity, width, height));
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

    public void setMovePosition(Point point) {
        movePoint.x = point.x;
        movePoint.y = point.y;
    }

    public void setSeePosition(Point point){
        seePoint.x = point.x;
        seePoint.y = point.y;
        robotDirection = angleTo(movePoint.x, movePoint.y, seePoint.x, seePoint.y);
    }

    private double getAngularVelocity() {
        double angularVelocity = 0;
        //double angleToTarget = angleTo(robotPositionX, robotPositionY, targetPositionX, targetPositionY);
        double angleToTarget = angleTo(robotPositionX, robotPositionY, movePoint.x, movePoint.y);
        if (angleToTarget > robotDirection) {
            angularVelocity = Constants.Robot.MAX_ANGULAR_VELOCITY;
        }
        if (angleToTarget < robotDirection) {
            angularVelocity = -Constants.Robot.MAX_ANGULAR_VELOCITY;
        }
        return angularVelocity;
    }

    /*
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
    */

    private void keepTargetInRectangle(int width, int height) {
        if (movePoint.x > width)
            movePoint.x = width;
        else if (movePoint.x < 0)
            movePoint.x = 0;
        else if (movePoint.y < 0)
            movePoint.y = 0;
        else if (movePoint.y > height)
            movePoint.y = height;
    }

    public void onModelUpdateEvent(int width, int height) {
        keepTargetInRectangle(width, height);

        //double distance = distance(targetPositionX, targetPositionY, robotPositionX, robotPositionY);
        double distance = distance(movePoint.x, movePoint.y, robotPositionX, robotPositionY);
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
        //else
            //moveRobot(getAngularVelocity(), width, height);
            moveRobot();
    }
}
