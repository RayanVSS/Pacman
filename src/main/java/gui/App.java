package gui;

import gui.AppStateMachine.AppState;
import gui.AppStateMachine.HomeScreenState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    public static Stage pStage;
    private static Pane root = new Pane();
    public static Scene screen = new Scene(root);

    public static AppState app_state = AppState.HOME_SCREEN;
    // Définit les paramètres du texte de l'application
    static final double MAX_FONT_SIZE = 20.0; // Définit la taille du texte pour le score
    public static Font text_graphics = new Font(STYLESHEET_CASPIAN, MAX_FONT_SIZE); // TODO : modifier la police d'écriture

    @Override
    public void start(Stage primaryStage) {
        pStage = primaryStage;

        System.out.println(app_state.showState());

        // Etat intitial de l'application
        app_state.changeState(HomeScreenState.getInstance());

        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                var deltaT = now - last;
                app_state.process(deltaT);
                last = now;
            }
        }.start();

        pStage.setScene(screen);
        // pStage.setFullScreen(true);
        pStage.setFullScreenExitHint(""); // Retire le message d'indication pour quitter le plein écran
        pStage.show();
    }
}
