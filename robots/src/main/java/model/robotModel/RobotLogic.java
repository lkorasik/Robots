package model.robotModel;

import model.Constants;

import java.awt.*;

import lombok.Getter;

public class RobotLogic {

    @Getter
    private volatile double m_robotPositionX = 100;
    @Getter
    private volatile double m_robotPositionY = 100;
    @Getter
    private volatile double m_robotDirection = 0;

    @Getter
    private volatile int m_targetPositionX = 150;
    @Getter
    private volatile int m_targetPositionY = 100;

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


    private void moveRobot(double velocity, double angularVelocity, double duration) {
        //Сохрани поворот
        //чтоб не мог исчезнуть за границей
        m_robotDirection = getDirectionToTarget(m_targetPositionX, m_targetPositionY, m_robotPositionX, m_robotPositionY);
        velocity = applyLimits(velocity, 0, Constants.Robot.MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -Constants.Robot.MAX_ANGULAR_VELOCITY, Constants.Robot.MAX_ANGULAR_VELOCITY);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);

        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);

        m_robotPositionX = newX;
        m_robotPositionY = newY;

    }

    private double getDirectionToTarget(double targetPositionX, double targetPositionY, double positionX, double positionY) {
        double diffX = targetPositionX - positionX;
        double diffY = targetPositionY - positionY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
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

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public void setTargetPosition(Point p) {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }

    public void onModelUpdateEvent() {
        double distance = distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5) {
            return;
        }
        double velocity = Constants.Robot.MAX_VELOCITY;
        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        double angularVelocity = 0;
        if (angleToTarget > m_robotDirection) {
            angularVelocity = Constants.Robot.MAX_ANGULAR_VELOCITY;
        }
        if (angleToTarget < m_robotDirection) {
            angularVelocity = -Constants.Robot.MAX_ANGULAR_VELOCITY;
        }

        moveRobot(velocity, angularVelocity, 10);
    }
}
