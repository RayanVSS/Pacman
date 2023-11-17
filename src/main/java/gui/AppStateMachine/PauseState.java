package gui.AppStateMachine;

import lib.FontLoader;
import gui.App;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import lib.State;
import model.MazeState;

public class PauseState implements State{

    private String state_name = "Pause State";

    private static final int MAX_FONT_SIZE = 100;
    private Font pixel_font = FontLoader.getPixelFont(MAX_FONT_SIZE);
    private Font text_font = FontLoader.getPixelFont(30);

    private static final PauseState instance = new PauseState();
    private boolean relaunch = false;

    private final String IDLE_BUTTON_STYLE = "-fx-text-fill: white;";
    private final String HOVERED_BUTTON_STYLE = "-fx-text-fill: red;";

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
        // texte pause
        Label pause_label = new Label("Pause");
        pause_label.setFont(pixel_font);
        pause_label.setStyle("-fx-text-fill: white;");
        PlayingState.getInstance().game.getChildren().add(pause_label);
        // bouton reprendre le jeu 
        Button resume_button = new Button("Reprendre");
        resume_button.setFont(text_font);
        resume_button.setStyle(IDLE_BUTTON_STYLE);
        resume_button.setTranslateY(150);
        resume_button.setTranslateX(-200);
        resume_button.setOnMouseEntered(e -> resume_button.setStyle(HOVERED_BUTTON_STYLE));
        resume_button.setOnMouseExited(e -> resume_button.setStyle(IDLE_BUTTON_STYLE));
        resume_button.setOnAction(e -> {
            App.app_state.changeState(PlayingState.getInstance());
            System.out.println("resume");
        });
        PlayingState.getInstance().game.getChildren().add(resume_button);
        // bouton quitter recommencer
        Button restart_button = new Button("Recommencer");
        restart_button.setFont(text_font);
        restart_button.setStyle(IDLE_BUTTON_STYLE);
        restart_button.setTranslateY(150);
        restart_button.setTranslateX(200);
        restart_button.setOnMouseEntered(e -> restart_button.setStyle(HOVERED_BUTTON_STYLE));
        restart_button.setOnMouseExited(e -> restart_button.setStyle(IDLE_BUTTON_STYLE));
        restart_button.setOnAction(e -> {
            relaunch = true;
            App.app_state.changeState(PlayingState.getInstance());
            System.out.println("restart");
        });
        PlayingState.getInstance().game.getChildren().add(restart_button);
        // bouton quitter
        Button quit_button = new Button("Quitter");
        quit_button.setFont(text_font);
        quit_button.setStyle("-fx-text-fill: red;");
        quit_button.setTranslateY(250);
        quit_button.setTranslateX(0);
        quit_button.setOnAction(e -> {
            System.exit(0);
        });
        PlayingState.getInstance().game.getChildren().add(quit_button);
    }

    public void exit() {
        for(int i = 0; i < 4; i++)
        PlayingState.getInstance().game.getChildren().remove(PlayingState.getInstance().game.getChildren().size() -1);
    }

    public void transitionTo(State s) {
        if(s instanceof PlayingState && relaunch){
            PlayingState.getInstance().initializeMaze();
            relaunch = false;
            return;
        }
        if(s instanceof PlayingState){
            PlayingState.getInstance().gameView.animate();
        }
    }
}
