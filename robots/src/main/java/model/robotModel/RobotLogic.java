package model.robotModel;

import lombok.Getter;

import java.awt.*;

public class RobotLogic {


    @Getter
    private volatile double robotDirection = 0;

    private final Point position = new Point(100, 100);
    private final Point seePoint = new Point(100, 100);
    private final Point predPosition = new Point(100, 100);

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

    public void onModelUpdateEvent(int width, int height) {
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
}
