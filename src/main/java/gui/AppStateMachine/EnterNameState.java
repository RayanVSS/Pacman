package gui.AppStateMachine;

import javafx.scene.text.Font;

import gui.App;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Text;
import gui.Controller.EnterNameController;
import lib.State;
import lib.ElementScaler;
import lib.FontLoader;

public class EnterNameState implements State {
    private String state_name = "EnterName State";

    private static final EnterNameState instance = new EnterNameState();

    private BorderPane enter_name_menu = new BorderPane();
    private Font pixel_font = FontLoader.getPixelFont(20);
    private String userName = "Player";

    private EnterNameState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static EnterNameState getInstance() {
        return instance;
    }

    public String showState() {
        return state_name;
    }

    private Pane createStartButton() {
        BorderPane start_button = new BorderPane();
        start_button.setMaxHeight(App.screen.getHeight() / 2);
        start_button.setMaxWidth(App.screen.getWidth());

        start_button.setStyle("-fx-background-color: black");
        Label start_button_text = new Label("Appuyer sur Entree pour lancer une partie"  + "\n"  + "apres avoir tapper votre nom !");
        start_button_text.setTextAlignment(TextAlignment.CENTER);

        Image img = new Image(getClass().getResourceAsStream("/start.gif"));
        ImageView view = new ImageView(img);

        view.setFitHeight(ElementScaler.scale(500));
        view.setFitWidth(ElementScaler.scale(600));
        view.setPreserveRatio(true);

        start_button_text.setFont(pixel_font);
        start_button_text.setTextFill(javafx.scene.paint.Color.WHITE);

        start_button.setCenter(view);
        start_button.setBottom(start_button_text);
        BorderPane.setAlignment(start_button_text, Pos.BOTTOM_CENTER);

        return start_button;
    }

    public void enter() {
        App.screen.setOnKeyPressed(null);
        enter_name_menu.setStyle("-fx-background-color: black;");
        // We want to add at the top of the screen a field to type our name
        Label name_label = new Label("Entrez votre nom : ");
        name_label.setFont(pixel_font);
        name_label.setTextFill(javafx.scene.paint.Color.WHITE);
        name_label.setTextAlignment(TextAlignment.CENTER);
        enter_name_menu.setTop(name_label);
        BorderPane.setAlignment(name_label, Pos.TOP_CENTER);
        TextField name_field = new TextField();
        name_field.setStyle("-fx-text-alignment: center; -fx-text-fill: white;");
        name_field.setAlignment(Pos.CENTER);
        name_field.textProperty().addListener((observable, oldValue, newValue) -> {
            // We want to limit the size of the name to 8 characters max and to not allow
            // special characters and if the character is uppercase we want to put it in
            // lowercase
            if (newValue.length() > 8 || newValue.matches(".*[^a-zA-Z].*")) {
                name_field.setText(oldValue);
                // We want to display a message to the user to tell him that he can't use
                // special characters
                name_label.setText("Entrez votre nom : " + "\n" + userName + "\n" + "Caractere speciaux interdits");
            } else {
                if (newValue.matches(
                        ".*[A-Z].*")) {
                    newValue = newValue.toLowerCase();
                }
                System.out.println(newValue);
                userName = newValue;
                name_field.setText(userName);
                name_label.setText("Entrez votre nom : " + "\n" + userName);
            }
        });
        enter_name_menu.getChildren().add(name_field);

        Text cancel_text = new Text("ECHAP pour annuler");
        cancel_text.setFont(pixel_font);
        cancel_text.setFill(javafx.scene.paint.Color.WHITE);
        cancel_text.setTextAlignment(TextAlignment.LEFT);
        enter_name_menu.setBottom(cancel_text);

        enter_name_menu.setCenter(createStartButton());

        EnterNameController controller = new EnterNameController();
        App.screen.setOnKeyPressed(controller::keyPressedHandler);
        App.screen.setRoot(enter_name_menu);
    }

    public void exit() {
        enter_name_menu.getChildren().clear();
        App.screen.setOnKeyPressed(null);
        HomeScreenState.getInstance().setUserName(userName);
    }

    public void transitionTo(State s) {
        if (s instanceof PlayingState) {
            HomeScreenState.getInstance().mediaPlayer.stop();
            PlayingState.getInstance().initializeMaze();
        }
    }
}
