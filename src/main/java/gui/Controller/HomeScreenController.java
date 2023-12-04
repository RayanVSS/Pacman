package gui.Controller;

import gui.App;
import gui.AppStateMachine.EnterNameState;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HomeScreenController {

    public void keyPressedHandler(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            App.app_state.changeState(EnterNameState.getInstance());
            System.out.println("Vous avez appuyé sur ENTER"); 
        }
        if(event.getCode().equals(KeyCode.ESCAPE)){
            System.exit(0);
        }
        else{

        }
    }

    public void keyReleasedHandler(KeyEvent event) {
        // Rien
    }
}
