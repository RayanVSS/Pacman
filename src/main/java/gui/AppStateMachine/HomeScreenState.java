package gui.AppStateMachine;

import gui.App;
import gui.Controller.HomeScreenController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import lib.State;

public class HomeScreenState implements State {
    private String state_name = "Home Screen State";
    private static final HomeScreenState instance = new HomeScreenState();
   
    StackPane start_menu = new StackPane(); // Utilise StackPane pour centrer l'image

    private HomeScreenState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static HomeScreenState getInstance() {
        return instance;
    }

    public String showState() {
        return state_name;
    }

    public void enter() {
        Label start_button = new Label("Appuyer sur Entrer !");
        Image img = new Image(getClass().getResourceAsStream("/start_button_temporaire.png"));
        ImageView view = new ImageView(img);

        start_button.setFont(App.text_graphics);
        start_button.setTextFill(javafx.scene.paint.Color.WHITE);

        view.setPreserveRatio(true);
        start_button.setGraphic(view);

        start_menu.setPrefSize(400, 300);
        start_menu.setStyle("-fx-background-color: black;"); // Définir la couleur de fond du StackPane

        // Ajoute le label avec l'image au StackPane
        start_menu.getChildren().add(start_button);

        var homeScreenController = new HomeScreenController();
        start_menu.setOnKeyPressed(homeScreenController::keyPressedHandler);

        App.screen.setRoot(start_menu);

        start_menu.requestFocus();

        App.pStage.show();
    }

    public void process(long deltaT) {
        System.out.println("Dans le HomeScreen");
    }

    public void exit() {

    }
}
