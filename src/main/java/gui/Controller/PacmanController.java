package gui.Controller;

import model.Direction;
import model.MazeState;
import model.PacMan;
import gui.AppStateMachine.PlayingState;
import javafx.scene.input.KeyEvent;

public class PacmanController {
    public static Direction currentDirection = Direction.NONE;
    public static Direction nextDirection = Direction.NONE;
    private MazeState config = PlayingState.maze;

    public void keyPressedHandler(KeyEvent event) {
        Direction temp = getDirectionFromKeyEvent(event);
        if(temp != nextDirection){
            nextDirection = temp;
        }
    }
    
    public void keyReleasedHandler(KeyEvent event) {
        // Nothing to do?
    }

    public Direction getDirectionFromKeyEvent(KeyEvent event){
        var keyCode = event.getCode();
        switch(keyCode){
            case LEFT -> {return Direction.WEST;}
            case RIGHT -> {return Direction.EAST;}
            case UP -> {return Direction.NORTH;}
            case DOWN -> {return Direction.SOUTH;}
            default -> {return PacMan.INSTANCE.getDirection();}
        }
    }
}
