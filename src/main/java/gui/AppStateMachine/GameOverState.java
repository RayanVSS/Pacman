package gui.AppStateMachine;

import gui.App;
import gui.Controller.GameOverController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import lib.State;
import model.MazeState;
import javafx.scene.text.TextAlignment;;

public class GameOverState implements State {
    private String state_name = "Game Over";
    private static final GameOverState instance = new GameOverState();
    BorderPane restart_menu = new BorderPane();

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
        Label restart_button = new Label("Appuyer sur Entrer !");
        Image img = new Image(getClass().getResourceAsStream("/restart_button_temporaire.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(800);
        view.setFitWidth(800);

        restart_button.setFont(App.text_graphics);
        restart_button.setTextFill(javafx.scene.paint.Color.WHITE);
        restart_button.setTextAlignment(TextAlignment.LEFT);

        view.setPreserveRatio(true);
        restart_button.setGraphic(view);

        restart_menu.setPrefSize(500, 500);
        restart_menu.setStyle("-fx-background-color: black;"); // Définir la couleur de fond du BorderPane

        // Ajoute le label avec l'image au BorderPane
        restart_menu.setCenter(restart_button);

        var GameOverController = new GameOverController();
        restart_menu.setOnKeyPressed(GameOverController::keyPressedHandler);

        App.screen.setRoot(restart_menu);

        restart_menu.requestFocus();

    }
    
    public void process(long deltaT){

    }
    
    public void exit(){
        
    }
}