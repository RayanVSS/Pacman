package gui.AppStateMachine;

import javafx.scene.Scene;
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
    public static Label score_graphics = new Label("" + 0);

    public static Label life_graphics = new Label("" + 3);

    private static final PlayingState instance = new PlayingState();

    MazeState maze = new MazeState(MazeConfig.makeExample1());

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
        var root = new Pane();

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

        root.getChildren().add(score_graphics);
        root.getChildren().add(life_graphics);

        var gameScene = new Scene(root);
        var pacmanController = new PacmanController();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var gameView = new GameView(maze, root, 100.0);
        App.pStage.setScene(gameScene);
        App.pStage.show();
        gameView.animate();
        score_graphics.toFront(); //Met le score par dessus le reste des éléments
        life_graphics.toFront();
    }

    public void process(long deltaT) {
       System.out.println(deltaT);
    }

    public void exit() {

    }
}
