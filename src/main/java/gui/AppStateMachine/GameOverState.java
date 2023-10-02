package gui.AppStateMachine;

import lib.State;

public class GameOverState implements State {
    private String state_name = "Game Over";
    private static final GameOverState instance = new GameOverState();

    private GameOverState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static GameOverState getInstance() {
        return instance;
    }

    public String showState(){
        return state_name;
    }
    
    public void enter(){

    }
    
    public void process(long deltaT){

    }
    
    public void exit(){
        
    }
}