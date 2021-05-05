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
        else
            moveRobot();
    }
}
