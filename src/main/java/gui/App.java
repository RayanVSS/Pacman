package gui;

import gui.AppStateMachine.AppState;
import gui.AppStateMachine.HomeScreenState;
import gui.AppStateMachine.StartingLogosState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    public static Stage pStage;
    private static Pane root = new Pane();
    public static Scene screen = new Scene(root);

    public static AppState app_state = AppState.STARTING_LOGOS;
 
    @Override
    public void start(Stage primaryStage) {
        pStage = primaryStage;

        //Définit la taille de la fenêtre
        double screen_width = Screen.getPrimary().getBounds().getWidth();
        double screen_height = Screen.getPrimary().getBounds().getHeight();
        double min_screen_size = Math.min(screen_width, screen_height) - 100;
        pStage.setWidth(min_screen_size);
        pStage.setHeight(min_screen_size);

        pStage.setResizable(false);

        System.out.println(app_state.showState());

        // Etat intitial de l'application
        app_state.changeState(StartingLogosState.getInstance());

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
        pStage.setFullScreenExitHint(""); // Retire le message d'indication pour quitter le plein écran
        // pStage.setFullScreen(true);
        pStage.show();  
    }
}
