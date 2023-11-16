package gui.AppStateMachine;

import gui.App;
import gui.Controller.GameOverController;
import lib.ElementScaler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lib.FontLoader;
import lib.State;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GameOverState implements State {
    private String state_name = "Game Over";
    private static final GameOverState instance = new GameOverState();
    BorderPane restart_menu = new BorderPane();

    private double MAX_FONT_SIZE = 20; // Définit la taille du texte pour le score
    private Font pixel_font = FontLoader.getPixelFont(MAX_FONT_SIZE);

    private GameOverState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static GameOverState getInstance() {
        return instance;
    }

    public String showState() {
        return state_name;
    }

    public Pane createRestartButton() {
        BorderPane restart_button = new BorderPane();
        restart_button.setMaxHeight(App.screen.getHeight() / 2);
        restart_button.setMaxWidth(App.screen.getWidth());

        restart_button.setStyle("-fx-background-color: black");
        if(PlayingState.getInstance().maze.getScore() > PlayingState.getInstance().bestScore){
            PlayingState.getInstance().bestScore = PlayingState.getInstance().maze.getScore();
        }
        Label restart_button_text = new Label("Appuyer sur Entree !\n" +
                "Best Score :" + PlayingState.getInstance().bestScore + "\n" +
                "Score : "+ PlayingState.getInstance().maze.getScore());
        restart_button_text.setTextAlignment(TextAlignment.CENTER);

        Image img = new Image(getClass().getResourceAsStream("/restart_button_temporaire.png"));
        ImageView view = new ImageView(img);

        view.setFitHeight(ElementScaler.scale(300));
        view.setFitWidth(ElementScaler.scale(400));
        view.setPreserveRatio(true);

        restart_button_text.setFont(pixel_font);
        restart_button_text.setTextFill(javafx.scene.paint.Color.WHITE);

        restart_button.setCenter(view);
        restart_button.setBottom(restart_button_text);
        BorderPane.setAlignment(restart_button_text, Pos.BOTTOM_CENTER);

        return restart_button;
    }

    public void enter() {
        MAX_FONT_SIZE = ElementScaler.scale(MAX_FONT_SIZE);
        Pane restart_button = createRestartButton();

        restart_menu.setStyle("-fx-background-color: black;");

        restart_menu.setCenter(restart_button);

        var GameOverController = new GameOverController();
        App.screen.setOnKeyPressed(GameOverController::keyPressedHandler);

        App.screen.setRoot(restart_menu);
    }

    public void exit() {

    }

    public void transitionTo(State s) {

    }
}
