package gui.AppStateMachine;

import lib.State;

//Implémentation d'une machine à état en utilisant un singleton

public enum AppState {
    HOME_SCREEN(HomeScreenState.getInstance()),
    PLAYING(PlayingState.getInstance()),
    GAMEOVER(GameOverState.getInstance());

    private State current_state;

    private AppState(State initial_state) {
        current_state = initial_state;
    }

    public State getState() {
        return current_state;
    }

    public String showState(){
        return current_state.showState();
    }

    public void changeState(State s) {
        current_state.exit();
        s.enter();
        current_state = s;
    }

    public void process(long deltaT) {
        current_state.process(deltaT);
    }

}