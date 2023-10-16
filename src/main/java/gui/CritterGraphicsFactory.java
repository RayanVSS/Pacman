package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import model.Critter;
import model.Ghost;
import model.PacMan;


public final class CritterGraphicsFactory {
    private final double scale;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(Critter critter) {
        var size = 0.7;
        var url = (critter instanceof PacMan) ? "pac-man-fortnite.gif" :
                switch ((Ghost) critter) {
                    case BLINKY -> "ghost_blinky.png";
                    case CLYDE -> "ghost_clyde.png";
                    case INKY -> "ghost_inky.png";
                    case PINKY -> "ghost_pinky.png";
                };
        var image = new ImageView(new Image(url, scale * size, scale * size, true, true));
        Rotate rotation = new Rotate(0, scale * size / 2, scale * size / 2); // Initial rotation angle set to 0
        image.getTransforms().add(rotation);

        return new GraphicsUpdater() {
            @Override
            
            public void update() {
                image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);
                // Debug.out("sprite updated");
                if (critter instanceof PacMan) {
                    switch (((PacMan) critter).getDirection()) {
                        case NORTH:
                            rotation.setAngle(90);                         
                            break;
                        case EAST:
                            rotation.setAngle(180); 
                            break;
                        case WEST:
                            rotation.setAngle(0); 
                            break;
                        case SOUTH:
                            rotation.setAngle(270); 
                            break;
                    }
                }
            

            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }
}
