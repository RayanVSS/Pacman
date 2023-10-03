package gui.Controller;

import gui.App;
import gui.AppStateMachine.PlayingState;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HomeScreenController {

    public void keyPressedHandler(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            App.app_state.changeState(PlayingState.getInstance());
            System.out.println("Vous avez appuyé sur ENTER"); 
        }
        else{

        }
    }

    public void keyReleasedHandler(KeyEvent event) {
        // Rien
    }
}
