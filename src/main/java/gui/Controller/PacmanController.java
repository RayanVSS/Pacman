package gui.Controller;

import model.Direction;
import model.MazeState;
import model.PacMan;
import gui.AppStateMachine.PlayingState;
import javafx.scene.input.KeyEvent;
import gui.App;
import gui.AppStateMachine.PauseState;

public class PacmanController {
    public static Direction currentDirection = Direction.NONE;
    public static Direction nextDirection = Direction.NONE;
    private MazeState config = PlayingState.maze;

    public void keyPressedHandler(KeyEvent event) {
        // if is echap
        if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE && PlayingState.getInstance().canPause) {
            // System.out.println("Vous avez appuyé sur ECHAP");
            // if we are not in pause
            if (PlayingState.getInstance() == App.app_state.getState()) {
                PlayingState.getInstance().hasPaused = true;
                App.app_state.changeState(PauseState.getInstance());
            }
            // if we are in pause
            else {
                App.app_state.changeState(PlayingState.getInstance());
            }
        }
        else {
            Direction temp = getDirectionFromKeyEvent(event);
            if(temp != nextDirection){
                nextDirection = temp;
            }
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
