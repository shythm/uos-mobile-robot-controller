import java.awt.Point;

interface IMobileRobotModel {
    // turn the robot 90 degrees clockwise
    int turn();
    
    // return the direction the robot is facing
    int getDirection();
    
    // move the robot one unit in the direction it is facing
    Point move();
    
    // return the current position of the robot
    Point getPosition();
    
    // return true if the robot facing a hazard on its direction of movement
    boolean senseHazard();

    // return true if there is a color blob in every direction around one unit of the robot
    boolean senseColorBlob();
}