package gui.AppStateMachine;

import gui.App;
import gui.Controller.HomeScreenController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lib.State;

public class HomeScreenState implements State {
    private String state_name = "Home Screen State";
    private static final HomeScreenState instance = new HomeScreenState();
   
    BorderPane start_menu = new BorderPane();

    private HomeScreenState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static HomeScreenState getInstance() {
        return instance;
    }

    public String showState() {
        return state_name;
    }

    public Label createStartButton(){
        Label start_button = new Label("Appuyer sur Entrer !");
        Image img = new Image(getClass().getResourceAsStream("/start_button_temporaire.png"));
        ImageView view = new ImageView(img);

        view.setFitHeight(100);
        view.setFitWidth(200);
        view.setPreserveRatio(true);

        start_button.setFont(App.text_graphics);
        start_button.setTextFill(javafx.scene.paint.Color.WHITE);

        view.setPreserveRatio(true);
        start_button.setGraphic(view);

        return start_button;
    }

    public void enter() {
        start_menu.setStyle("-fx-background-color: black"); // Définir la couleur de fond du menu
        start_menu.setPrefSize(App.screen.getWidth(), App.screen.getHeight() );

        Label start_button = createStartButton();

        StackPane centerPane = new StackPane(start_button);
        centerPane.setAlignment(Pos.CENTER);

        // Ajoute le label avec l'image au StackPane
        start_menu.setCenter(centerPane);

        var homeScreenController = new HomeScreenController();
        App.screen.setOnKeyPressed(homeScreenController::keyPressedHandler);

        App.screen.setRoot(start_menu);
    }

    public void process(long deltaT) {
        
    }

    public void exit() {

    }
}
