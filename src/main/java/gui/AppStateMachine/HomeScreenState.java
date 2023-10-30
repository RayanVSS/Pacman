package gui.AppStateMachine;

import gui.App;
import gui.Controller.HomeScreenController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import lib.State;

public class HomeScreenState implements State {
    private String state_name = "Home Screen State";
    private static final HomeScreenState instance = new HomeScreenState();

    private final double MAX_FONT_SIZE = 20.0; // Définit la taille du texte pour le score
    private Font pixel_font = Font.loadFont(getClass().getResourceAsStream("/Font/pixel_font.ttf"), MAX_FONT_SIZE);
   
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

    public Pane createStartButton(){
        BorderPane start_button = new BorderPane();
        start_button.setMaxHeight(Screen.getPrimary().getBounds().getHeight() / 2);
        start_button.setMaxWidth(Screen.getPrimary().getBounds().getWidth());
        
        start_button.setStyle("-fx-background-color: black");
        Label start_button_text = new Label("Appuyer sur Entree !");
        start_button_text.setTextAlignment(TextAlignment.CENTER);

        Image img = new Image(getClass().getResourceAsStream("/start_button_temporaire.png"));
        ImageView view = new ImageView(img);

        view.setFitHeight(100);
        view.setFitWidth(200);
        view.setPreserveRatio(true);

        start_button_text.setFont(pixel_font);
        start_button_text.setTextFill(javafx.scene.paint.Color.WHITE);

        start_button.setCenter(view);
        start_button.setBottom(start_button_text);
        BorderPane.setAlignment(start_button_text, Pos.BOTTOM_CENTER);

        return start_button;
    }

    public void enter() {
        Pane start_button = createStartButton();
        
        start_menu.setStyle("-fx-background-color: black");

        start_menu.setCenter(start_button);

        var homeScreenController = new HomeScreenController();
        App.screen.setOnKeyPressed(homeScreenController::keyPressedHandler);

        App.screen.setRoot(start_menu);
    }

    public void process(long deltaT) {
        
    }

    public void exit() {

    }
}
