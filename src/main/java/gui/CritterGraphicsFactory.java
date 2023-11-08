package gui;

import static model.Ghost.BLINKY;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import model.Critter;
import model.Ghost;
import model.PacMan;
import java.util.Map;

public final class CritterGraphicsFactory {
    private final double scale;

    private Image image_mort;
    private Image image_scared;
    private Map<Critter, Image> images;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
        image_mort = new Image("ghost_dead.png", scale * 0.7, scale * 0.7, true, true);
        image_scared = new Image("ghost_scared.png", scale * 0.7, scale * 0.7, true, true);
        images = Map.of(
                Ghost.BLINKY, new Image("ghost_blinky.png", scale * 0.7, scale * 0.7, true, true),
                Ghost.CLYDE, new Image("ghost_clyde.png", scale * 0.7, scale * 0.7, true, true),
                Ghost.INKY, new Image("ghost_inky.png", scale * 0.7, scale * 0.7, true, true),
                Ghost.PINKY, new Image("ghost_pinky.png", scale * 0.7, scale * 0.7, true, true));
    }

    public GraphicsUpdater makeGraphics(Critter critter) {
        var size = 0.7;
        var url = "pac-man-fortnite.gif";
        if (critter instanceof Ghost) {
            url = switch ((Ghost) critter) {
                case BLINKY -> "ghost_blinky.png";
                case CLYDE -> "ghost_clyde.png";
                case INKY -> "ghost_inky.png";
                case PINKY -> "ghost_pinky.png";
            };

        }
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
                if (critter instanceof Ghost) {

                    if (PacMan.INSTANCE.isEnergized() && !((Ghost) critter).getDisableEnergizer()
                            && !((Ghost) critter).isMort()) {
                        image.setImage(image_scared);
                    } else if (((Ghost) critter).isMort()) {
                        image.setImage(image_mort);
                    } else {
                        image.setImage(images.get(critter));

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