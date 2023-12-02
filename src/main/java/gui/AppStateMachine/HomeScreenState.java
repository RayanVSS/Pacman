package gui.AppStateMachine;

import gui.App;
import gui.Controller.HomeScreenController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import lib.FontLoader;

import animatefx.animation.Bounce;
import lib.State;
import lib.ElementScaler;

public class HomeScreenState implements State {
    private String state_name = "Home Screen State";
    private static final HomeScreenState instance = new HomeScreenState();
    private String musicFileName = "/ost/Carl-Orff-O-Fortuna-_-Carmina-Burana.wav";
    private Media media = new Media(getClass().getResource(musicFileName).toString());
    public MediaPlayer mediaPlayer = new MediaPlayer(media);
    private String userName = "Player";

    private double MAX_FONT_SIZE = 20.0;
    private Font pixel_font = FontLoader.getPixelFont(MAX_FONT_SIZE);
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
        start_button.setMaxHeight(App.screen.getHeight() / 2);
        start_button.setMaxWidth(App.screen.getWidth());
        
        start_button.setStyle("-fx-background-color: black");
        Label start_button_text = new Label("Appuyer sur Entree !");
        start_button_text.setTextAlignment(TextAlignment.CENTER);

        Image img = new Image(getClass().getResourceAsStream("/start.gif"));
        ImageView view = new ImageView(img);

        view.setFitHeight(ElementScaler.scale(300));
        view.setFitWidth(ElementScaler.scale(400));
        view.setPreserveRatio(true);

        start_button_text.setFont(pixel_font);
        start_button_text.setTextFill(javafx.scene.paint.Color.WHITE);

        start_button.setCenter(view);
        start_button.setBottom(start_button_text);
        BorderPane.setAlignment(start_button_text, Pos.BOTTOM_CENTER);

        return start_button;
    }

    public void enter() {
        start_menu.getChildren().clear();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setStartTime(javafx.util.Duration.seconds(25));
        mediaPlayer.setStopTime(javafx.util.Duration.seconds(2 * 60 + 32));
        mediaPlayer.play();
        
        Pane start_button = createStartButton();
        new Bounce(start_button).play();

        start_menu.setStyle("-fx-background-color: black");

        start_menu.setCenter(start_button);

        // We want to add at the top of the screen a field to type our name
        Label name_label = new Label("Entrez votre nom : ");
        name_label.setFont(pixel_font);
        name_label.setTextFill(javafx.scene.paint.Color.WHITE);
        name_label.setTextAlignment(TextAlignment.CENTER);
        start_menu.setTop(name_label);
        BorderPane.setAlignment(name_label, Pos.TOP_CENTER);
        TextField name_field = new TextField();
        name_field.setStyle("-fx-text-alignment: center; -fx-text-fill: white;");
        name_field.setAlignment(Pos.CENTER);
        name_field.textProperty().addListener((observable, oldValue, newValue) -> {
            //We want to limit the size of the name to 8 characters max and to not allow special characters and if the character is uppercase we want to put it in lowercase
            if(newValue.length() > 8 || newValue.matches(".*[^a-zA-Z].*")){
                name_field.setText(oldValue);
                //We want to display a message to the user to tell him that he can't use special characters
                name_label.setText("Entrez votre nom : " + "\n" + userName + "\n" + "Caractere speciaux interdits");
            }
            else{
                if(newValue.matches(
                    ".*[A-Z].*"
                )){
                    newValue = newValue.toLowerCase();
                }
                System.out.println(newValue);   
                userName = newValue;
                name_field.setText(userName);
                name_label.setText("Entrez votre nom : " + "\n" + userName);
            }
        });
        start_menu.getChildren().add(name_field);

        // text echap to exit
        Text echap_text = new Text("ECHAP pour quitter");
        echap_text.setFont(pixel_font);
        echap_text.setTextAlignment(TextAlignment.LEFT);
        echap_text.setFill(javafx.scene.paint.Color.DARKRED);
        start_menu.setBottom(echap_text);
        BorderPane.setAlignment(echap_text, Pos.BOTTOM_LEFT);


        var homeScreenController = new HomeScreenController();
        App.screen.setOnKeyPressed(homeScreenController::keyPressedHandler);
        App.screen.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (event.getEventType().equals(javafx.scene.input.MouseEvent.MOUSE_CLICKED)) {
                    App.app_state.changeState(PlayingState.getInstance());
                }
            }
        });

        App.screen.setRoot(start_menu);
    }

    public void exit() {
        App.screen.setOnMouseClicked(null);
        mediaPlayer.stop();
    }

    public void transitionTo(State s) {
        if(s instanceof PlayingState){
            PlayingState.getInstance().initializeMaze();
        }
    }

    public String getUserName(){
        return userName;
    }
}
