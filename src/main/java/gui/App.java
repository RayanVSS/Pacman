package gui;


import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import config.MazeConfig;
import model.MazeState;

public class App extends Application {

    public static Label score_graphics = new Label("" + 0);

    @Override
    public void start(Stage primaryStage) {
        var root = new Pane();

        score_graphics.setTextAlignment(TextAlignment.LEFT);
        final double MAX_FONT_SIZE = 20.0; // Définis la taille du texte pour le score.
        Font text_graphics = new Font(STYLESHEET_CASPIAN, MAX_FONT_SIZE); // TODO : Modifier la police d'écriture.
        score_graphics.setFont(text_graphics);
        score_graphics.setTextFill(javafx.scene.paint.Color.WHITE);
        root.getChildren().add(score_graphics);

        var gameScene = new Scene(root);

        var pacmanController = new PacmanController();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);

        var maze = new MazeState(MazeConfig.makeExample1());
        var gameView = new GameView(maze, root, 100.0);
        score_graphics.toFront(); //Mets le score par dessus le reste des éléments.

        primaryStage.setScene(gameScene);
        primaryStage.show();

        Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBound.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screenBound.getHeight() - primaryStage.getHeight()) / 2);
        
        gameView.animate();

        primaryStage.setFullScreen(true); // Met le jeu en grand écran. Très important de le mettre dans cet emplacement si nous voulons pas que les étapes précédent soit afficher.
    }
}
