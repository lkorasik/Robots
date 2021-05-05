package model.robotModel;

import lombok.Getter;

import java.awt.*;

public class RobotLogic {


    @Getter
    private volatile double robotDirection = 0;

    private final Point position = new Point(100, 100);
    private final Point seePoint = new Point(100, 100);
    private final Point predPosition = new Point(100, 100);

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

    public int getPositionY(){
        return position.y;
    }

    public int getPositionX(){
        return position.x;
    }

    public Point getPositionPoint(){
        return position;
    }

    private void moveRobot() {
        robotDirection = angleTo(position.x, position.y, seePoint.x, seePoint.y);
        position.x = predPosition.x;
        position.y = predPosition.y;
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
        predPosition.x = point.x;
        predPosition.y = point.y;
    }

    public void setSeePosition(Point point){
        seePoint.x = point.x;
        seePoint.y = point.y;
        robotDirection = angleTo(position.x, position.y, seePoint.x, seePoint.y);
    }

    private void keepTargetInRectangle(int width, int height) {
        if (predPosition.x > width)
            predPosition.x = width;
        else if (predPosition.x < 0)
            predPosition.x = 0;
        else if (predPosition.y < 0)
            predPosition.y = 0;
        else if (predPosition.y > height)
            predPosition.y = height;
        else
            moveRobot();
    }

    public void onModelUpdateEvent(int width, int height) {
        keepTargetInRectangle(width, height);

//        if (position.x > width)
//            position.x  = width;
//        else if (position.x  < 0)
//            position.x  = 0;
//        else if (position.y < 0)
//            position.y = 0;
//        else if (position.y > height)
//            position.y = height;

    }
}
