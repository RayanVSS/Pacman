package gui.AppStateMachine;

import gui.App;
import gui.Controller.GameOverController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import lib.State;

public class GameOverState implements State {
    private String state_name = "Game Over";
    private static final GameOverState instance = new GameOverState();
    StackPane restart_menu = new StackPane();

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
        view.setFitHeight(500);
        view.setFitWidth(500);

        restart_button.setFont(App.text_graphics);
        restart_button.setTextFill(javafx.scene.paint.Color.WHITE);

        view.setPreserveRatio(true);
        restart_button.setGraphic(view);

        restart_menu.setPrefSize(400, 300);
        restart_menu.setStyle("-fx-background-color: black;"); // Définir la couleur de fond du StackPane

        // Ajoute le label avec l'image au StackPane
        restart_menu.getChildren().add(restart_button);

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