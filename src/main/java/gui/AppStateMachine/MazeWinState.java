package gui.AppStateMachine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;

import animatefx.animation.Flash;
import gui.App;
import lib.State;

public class MazeWinState implements State {
    private String state_name = "Maze Win State";
    private static final MazeWinState instance = new MazeWinState();

    private final double MAX_FONT_SIZE = 20.0;
    private Font pixel_font;
    {
        try {
            File f = new File(getClass().getResource("/Font/pixel_font.ttf").toURI());
            pixel_font = Font.loadFont(f.toURI().toURL().toString(), MAX_FONT_SIZE);
        } catch (Exception e) {
            //Pour MacOS jsp pk ça marche pas
            pixel_font = Font.loadFont(getClass().getResourceAsStream("/Font/pixel_font.ttf"), MAX_FONT_SIZE);
        }
    }
    BorderPane win_menu = new BorderPane();

    Flash flashWin = new Flash();
    Timeline waitTime = new Timeline();

    private MazeWinState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static MazeWinState getInstance() {
        return instance;
    }

    public String showState() {
        return state_name;
    }

    public void enter() {
        Text winText = new Text("Vous avez gagne !");
        winText.setFont(pixel_font);
        winText.setFill(javafx.scene.paint.Color.WHITE);
        win_menu.setCenter(winText);
        
        flashWin.setNode(winText);
        flashWin.setSpeed(0.10);
        flashWin.setCycleCount(Flash.INDEFINITE);
        flashWin.play();

        win_menu.setStyle("-fx-background-color: black;");

        waitTime = new Timeline(new KeyFrame(Duration.seconds(5.0), event -> {
            App.app_state.changeState(HomeScreenState.getInstance());
        }));
        waitTime.setCycleCount(1);
        waitTime.play();
        App.screen.setRoot(win_menu);
    }

    public void process(long deltaT) {

    }

    public void exit() {
        waitTime.stop();
        flashWin.stop();
    }
}
