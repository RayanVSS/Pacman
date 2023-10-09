package gui.AppStateMachine;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import config.MazeConfig;
import gui.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import gui.GameView;
import gui.Controller.PacmanController;
import model.MazeState;

import lib.State;

public class PlayingState extends App implements State {
    private String state_name = "Playing State";

    Pane game_screen = new Pane();
    
    public static Label score_graphics = new Label("" + 0);

    public static Label life_graphics = new Label("" + 3);

    private static final PlayingState instance = new PlayingState();

    public static MazeState maze = new MazeState(MazeConfig.makeExample1());

    private PlayingState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static PlayingState getInstance() {
        return instance;
    }

    public String showState(){
        return state_name;
    }

    public void enter() {

        score_graphics.setTextAlignment(TextAlignment.LEFT);
        score_graphics.setFont(App.text_graphics);
        score_graphics.setTextFill(javafx.scene.paint.Color.WHITE);

        Image img = new Image(getClass().getResourceAsStream("/pixel_heart.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitHeight(50);
        view.setFitWidth(50);
        life_graphics.setGraphic(view);

        life_graphics.setTextAlignment(TextAlignment.LEFT);
        life_graphics.setFont(App.text_graphics);
        life_graphics.setTextFill(javafx.scene.paint.Color.RED);
        life_graphics.setTranslateX(532);
        life_graphics.setTranslateY(-1);

        game_screen.getChildren().add(score_graphics);
        game_screen.getChildren().add(life_graphics);

        App.screen.setRoot(game_screen);

        var pacmanController = new PacmanController();
        App.screen.setOnKeyPressed(pacmanController::keyPressedHandler);
        App.screen.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var gameView = new GameView(maze, game_screen, 50.0);

        score_graphics.toFront(); //Met le score par dessus le reste des éléments
        life_graphics.toFront();

        gameView.animate();
    }

    public void process(long deltaT) {
       
    }

    public void exit() {

    }
}
