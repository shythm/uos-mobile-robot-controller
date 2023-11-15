package uos.teamkernel.model;

import uos.teamkernel.common.Point;
import uos.teamkernel.common.Direction;

public interface MobileRobotModel extends ModelObservable {

    /**
     * Turn the robot 90 degrees clockwise. This mobile robot can only turn 90
     * 
     * @return the direction the robot is facing after turning
     */
    Direction turn();

    /**
     * Get the direction the robot is facing
     * 
     * @return the direction
     */
    Direction getDirection();

    /**
     * Move the robot one unit in the direction it is facing. This mobile robot can
     * only move one unit.
     * 
     * @return the position the robot is in after moving
     */
    Point move();

    /**
     * Get the position of the robot
     * 
     * @return the position
     */
    Point getPosition();

    /**
     * sense whether if the robot facing a hazard on its direction of movement
     * 
     * @return true if there is a hazard
     */
    boolean senseHazard();

    /**
     * sense is there a color blob in every direction around one unit of the robot
     * 
     * @return true if there is a color blob
     */
    Direction senseColorBlob();
}
