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
import java.time.LocalTime;

public final class CritterGraphicsFactory {
    private final double scale;

    private Image image_mort;
    private Image image_scared;
    private Image image_anim;
    private Image ZONF, ZOND;   
    private Map<Critter, Image> images_D,images_G,images_H,images_B;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
        image_mort = new Image("ghost_dead.gif", scale * 0.7, scale * 0.7, true, true);
        image_scared = new Image("ghost_scared_haut.gif", scale * 0.7, scale * 0.7, true, true);
        image_anim = new Image("ghost_rainbow_haut.gif", scale * 0.7, scale * 0.7, true, true);
        ZONF = new Image("ghost_zon.gif", scale * 0.7, scale * 0.7, true, true);
        ZOND = new Image("ghost_zon.png", scale * 0.7, scale * 0.7, true, true);
        images_D = Map.of(
                Ghost.BLINKY, new Image("ghost_red_droite.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.CLYDE, new Image("ghost_yellow_droite.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.INKY, new Image("ghost_blue_droite.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.PINKY, new Image("ghost_purple_droite.gif", scale * 0.7, scale * 0.7, true, true));

        images_G = Map.of(
                Ghost.BLINKY, new Image("ghost_red_gauche.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.CLYDE, new Image("ghost_yellow_gauche.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.INKY, new Image("ghost_blue_gauche.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.PINKY, new Image("ghost_purple_gauche.gif", scale * 0.7, scale * 0.7, true, true));
        
        images_H = Map.of(
                Ghost.BLINKY, new Image("ghost_red_haut.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.CLYDE, new Image("ghost_yellow_haut.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.INKY, new Image("ghost_blue_haut.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.PINKY, new Image("ghost_purple_haut.gif", scale * 0.7, scale * 0.7, true, true));


        images_B = Map.of(
                Ghost.BLINKY, new Image("ghost_red_bas.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.CLYDE, new Image("ghost_yellow_bas.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.INKY, new Image("ghost_blue_bas.gif", scale * 0.7, scale * 0.7, true, true),
                Ghost.PINKY, new Image("ghost_purple_bas.gif", scale * 0.7, scale * 0.7, true, true));
    }
    

    public GraphicsUpdater makeGraphics(Critter critter) {
        var size = 0.7;
        var url = "pac-man-fortnite.gif";
        if (critter instanceof Ghost) {
            url = switch ((Ghost) critter) {
                case BLINKY -> "ghost_red_droite.gif";
                case CLYDE -> "ghost_yellow_droite.gif";
                case INKY -> "ghost_blue_droite.gif";
                case PINKY -> "ghost_purple_droite.gif";
            };

        }
        var image = new ImageView(new Image(url, scale * size, scale * size, true, true));
        Rotate rotation = new Rotate(0, scale * size / 2, scale * size / 2); // Initial rotation angle set to 0
        image.getTransforms().add(rotation);

        return new GraphicsUpdater() {
            @Override
            public void changeColor(javafx.scene.paint.Color color){
            }

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
                            && !((Ghost) critter).isMort() && !PacMan.INSTANCE.getzhonya()) {
                        LocalTime temps = LocalTime.now();
                        if (PacMan.INSTANCE.getTemps() - PacMan.INSTANCE.getTempsCourant() <= 2 ) {
                            image.setImage(image_anim);
                        } else {
                            image.setImage(image_scared);
                        }

                    } else if (((Ghost) critter).isMort()) {
                        image.setImage(image_mort);
                    }else if (PacMan.INSTANCE.getzhonya()) {
                        if (PacMan.INSTANCE.getTempsZhonya() - PacMan.INSTANCE.getTempsCourantZhonya() <= 1.5 ) {
                            image.setImage(ZONF);
                        } else {
                            image.setImage(ZOND);
                        }
                    } else {
                    switch (((Ghost) critter).getDirection()) {
                        case NORTH:
                        image.setImage(images_H.get(critter));
                            break;
                        case EAST:
                        image.setImage(images_D.get(critter));                        
                            break;
                        case WEST:
                        image.setImage(images_G.get(critter));
                            break;
                        case SOUTH:
                        image.setImage(images_B.get(critter));
                            break;
                    }

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