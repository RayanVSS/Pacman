package gui;

import gui.AppStateMachine.AppState;
import gui.AppStateMachine.HomeScreenState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    public static Stage pStage;

    // Variables pour le calcul de delta t
    private long lastTime = System.nanoTime();
    private static long deltaTime = 0;

    public static AppState app_state = AppState.HOME_SCREEN;
    //Définit les paramètres du texte de l'application
    static final double MAX_FONT_SIZE = 20.0; //Définit la taille du texte pour le score
    public static Font text_graphics = new Font(STYLESHEET_CASPIAN, MAX_FONT_SIZE); // TODO : modifier la police d'écriture

    public static double getDeltaTime(){
        return deltaTime;
    }

    @Override
    public void start(Stage primaryStage) {
        pStage = primaryStage;
        System.out.println(app_state.showState());
        
        //Etat intitial de l'application
        app_state.changeState(HomeScreenState.getInstance());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                // Calcule delta t
                deltaTime = (long) ((currentTime - lastTime) / 1e9); // en secondes
                lastTime = currentTime;

                // Code à exécuter à chaque frame
                app_state.process(deltaTime);
            }
        };
        
        timer.start();
    }
}
