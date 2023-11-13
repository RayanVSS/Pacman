package gui.AppStateMachine;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import config.MazeConfig;
import gui.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import gui.GameView;
import gui.Controller.PacmanController;
import model.MazeState;
import lib.FontLoader;
import lib.State;

public class PlayingState extends App implements State {
    private String state_name = "Playing State";

    public StackPane game = new StackPane();
    public BorderPane game_screen = new BorderPane();
    public BorderPane game_root = new BorderPane();

    public static Label score_graphics = new Label("" + 0);

    public static HBox life_graphics = new HBox();

    public boolean hasPaused = false;

    private final double MAX_FONT_SIZE = 20.0;
    private Font pixel_font = FontLoader.getPixelFont(MAX_FONT_SIZE);

    private static final PlayingState instance = new PlayingState();

    public static MazeState maze;
    public static GameView gameView;

    public static int bestScore = 0;

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
        score_graphics.setFont(pixel_font);
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
        if (hasPaused) {
            gameView.animate();
            hasPaused = false;
        } else {
            BorderPane root = new BorderPane();

            game_screen.setStyle("-fx-background-color: black;");

            maze = new MazeState(MazeConfig.makeExample1());
            maze.resetScore();
            maze.setLives(3);

            score_graphics = createScoreGraphics();
            life_graphics_update(maze.getLives());

            var pacmanController = new PacmanController();
            App.screen.setOnKeyPressed(pacmanController::keyPressedHandler);
            App.screen.setOnKeyReleased(pacmanController::keyReleasedHandler);

            double scale = 0;
            // Calculate the scale according to screen resolution and by making sure that
            // all the maze will be visible
            if (maze.getWidth() > maze.getHeight()) {
                scale = (App.screen.getWidth() - 100) / maze.getWidth();
            } else {
                scale = (App.screen.getHeight() - 100) / maze.getHeight();
            }
            gameView = new GameView(maze, root, scale);

            game_root.setCenter(gameView.getGameRoot());

            game_screen.setCenter(game_root);

            // Ajoutez life_graphics en bas du BorderPane
            game_screen.setBottom(life_graphics);

            // Ajoutez score_graphics en haut du BorderPane
            game_screen.setTop(score_graphics);

            gameView.animate();

            game.getChildren().add(game_screen);
        }

        App.screen.setRoot(game);
    }

    public void process(long deltaT) {

    }

    public void exit() {
        gameView.stop();
        if(!hasPaused){
            game_screen.getChildren().clear();
        }
    }
}
