package gui.Controller;

import model.Direction;
import model.MazeState;
import model.PacMan;
import gui.AppStateMachine.PlayingState;
import javafx.scene.input.KeyEvent;

public class PacmanController {
    private Direction currentDirection;
    private MazeState config = PlayingState.maze;

    public void keyPressedHandler(KeyEvent event) {
        System.out.println("1 : " + currentDirection);
        Direction newDirection = getDirectionFromKeyEvent(event);
        System.out.println("2 : " + newDirection);
        if( PacMan.INSTANCE.getDirection().equals(Direction.NONE) || currentDirection == Direction.NONE || currentDirection == null || config.canMoveInDirection(newDirection, PacMan.INSTANCE.getPos().round())){
            PacMan.INSTANCE.setDirection(newDirection);
            currentDirection = newDirection;
        }
        else{
            PacMan.INSTANCE.setDirection(currentDirection);
        }
        System.out.println("3 : " + PacMan.INSTANCE.getDirection());
        System.out.println(config.canMoveInDirection(currentDirection, PacMan.INSTANCE.getPos().round()));
        // if(config.canMoveInDirection(currentDirection, PacMan.INSTANCE.getPos().round())){
        //    PacMan.INSTANCE.setDirection(newDirection);
        //    currentDirection = newDirection;
        // }
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
