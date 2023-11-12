package gui.AppStateMachine;

import lib.State;

public class PauseState implements State{

    private String state_name = "Pause State";

    private static final PauseState instance = new PauseState();

    private PauseState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static PauseState getInstance() {
        return instance;
    }

    public String showState() {
        return state_name;
    }

    public void enter() {
        // TODO Auto-generated method stub
        
    }

    public void exit() {
        // TODO Auto-generated method stub
        
    }

    public void process(long deltaT) {
        // TODO Auto-generated method stub
        
    }
    
}
