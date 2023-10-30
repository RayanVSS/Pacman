package gui.AppStateMachine;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

    BorderPane game_screen = new BorderPane();

    public static Label score_graphics = new Label("" + 0);

    public static HBox life_graphics = new HBox();

    private static final PlayingState instance = new PlayingState();

    public static MazeState maze;
    public static GameView gameView;

    private PlayingState() {
        // Constructeur privé pour empêcher la création d'autres instances
    }

    public static PlayingState getInstance() {
        return instance;
    }

    public String showState() {
        return state_name;
    }

    public Label createScoreGraphics() {
        Label score_graphics = new Label();

        score_graphics.setTextAlignment(TextAlignment.LEFT);
        score_graphics.setFont(App.text_graphics);
        score_graphics.setTextFill(javafx.scene.paint.Color.WHITE);

        return score_graphics;
    }

    public void life_graphics_update(int lives) {
        life_graphics.getChildren().clear(); // Efface les vies précédentes
    
        for (int i = 0; i < lives; i++) {
            Image img = new Image(getClass().getResourceAsStream("/pixel_pacman_icon.png"));
            ImageView view = new ImageView(img);
            view.setPreserveRatio(true);
            view.setFitHeight(50);
            view.setFitWidth(50);
    
            Label lifeLabel = new Label();
            lifeLabel.setGraphic(view);
    
            life_graphics.getChildren().add(lifeLabel);
        }
    }
    
    public void enter() {
        BorderPane root = new BorderPane();

        game_screen.setStyle("-fx-background-color: black;");

        maze = new MazeState(MazeConfig.makeExample1());
        MazeState.resetScore();
        maze.setLives(3);

        score_graphics = createScoreGraphics();
        life_graphics_update(maze.getLives());

        App.screen.setRoot(game_screen);

        var pacmanController = new PacmanController();
        App.screen.setOnKeyPressed(pacmanController::keyPressedHandler);
        App.screen.setOnKeyReleased(pacmanController::keyReleasedHandler);
        gameView = new GameView(maze, root, 30);

        game_screen.setCenter(gameView.getGameRoot());

        // Ajoutez life_graphics en bas du BorderPane
        game_screen.setBottom(life_graphics);

        // Ajoutez score_graphics en haut du BorderPane
        game_screen.setTop(score_graphics);

        gameView.animate();
    }

    public void process(long deltaT) {

    }

    public void exit() {

        gameView.stop();
        game_screen.getChildren().clear();
    }
}
