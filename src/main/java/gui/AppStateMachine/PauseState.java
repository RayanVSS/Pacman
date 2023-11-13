package gui.AppStateMachine;

import lib.FontLoader;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import lib.State;

public class PauseState implements State{

    private String state_name = "Pause State";

    private static final int MAX_FONT_SIZE = 100;
    private Font pixel_font = FontLoader.getPixelFont(MAX_FONT_SIZE);

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
        Label pause_label = new Label("Pause");
        pause_label.setFont(pixel_font);
        pause_label.setStyle("-fx-text-fill: white;");
        PlayingState.getInstance().game.getChildren().add(pause_label);
    }

    public void exit() {
        PlayingState.getInstance().game.getChildren().remove(PlayingState.getInstance().game.getChildren().size() -1);
    }

    public void process(long deltaT) {
        // TODO Auto-generated method stub
        
    }
    
}
