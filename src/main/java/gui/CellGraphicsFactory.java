package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import geometry.IntCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.MazeState;

import static config.Cell.Content.DOT;
import static config.Cell.Content.ZHONYA;
import static config.Cell.Content.vitesseP;
import static config.Cell.Content.vitesseM;

import java.util.ArrayList;
import java.util.List;

public class CellGraphicsFactory {
    private final double scale;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        var group = new Group();
        group.setTranslateX(pos.x() * scale);
        group.setTranslateY(pos.y() * scale);
        var cell = state.getConfig().getCell(pos);
        var dot = new Circle();
        group.getChildren().add(dot);
        ImageView zhonya = new ImageView("zon.gif");
        ImageView vitessePImage = new ImageView("boots.gif");
        ImageView vitesseMImage = new ImageView("bootsM.gif");
        if (cell.getContent() == ZHONYA) {
            zhonya.setX(scale / 4);
            zhonya.setY(scale / 4);
            zhonya.setFitWidth(scale / 2);
            zhonya.setFitHeight(scale / 2);
            // Ajoutez l'ImageView à votre groupe
            group.getChildren().add(zhonya);
        } else if(cell.getContent() == vitesseP){
            vitessePImage.setX(scale / 4);
            vitessePImage.setY(scale / 4);
            vitessePImage.setFitWidth(scale /1.5);
            vitessePImage.setFitHeight(scale /1.5);
            // Ajoutez l'ImageView à votre groupe
            group.getChildren().add(vitessePImage);   
        } else if(cell.getContent() == vitesseM){
            vitesseMImage.setX(scale / 4);
            vitesseMImage.setY(scale / 4);
            vitesseMImage.setFitWidth(scale /1.5);
            vitesseMImage.setFitHeight(scale /1.5);
            // Ajoutez l'ImageView à votre groupe
            group.getChildren().add(vitesseMImage);  
        } else {
            dot.setRadius(switch (cell.initialContent()) {
                case DOT -> scale / 15;
                case ENERGIZER -> scale / 5;
                case NOTHING -> 0;
                case GHOST_DOOR -> 0;
                case ZHONYA -> 0;
                case vitesseP -> 0;
                case vitesseM -> 0;
            });
            dot.setCenterX(scale / 2);
            dot.setCenterY(scale / 2);
            dot.setFill(Color.YELLOW);
        }
        if (cell.northWall()) {
            var nWall = new Rectangle();
            // We want to not have an outline of the rectangle with the same color
            // as the background, so we need to make the rectangle a bit bigger
            // than the cell
            nWall.setHeight(scale / 10 + scale / 100);
            nWall.setWidth(scale + scale / 100);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.eastWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale + scale / 100);
            nWall.setWidth(scale / 10 + scale / 100);
            nWall.setY(0);
            nWall.setX(9 * scale / 10);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.southWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale / 10 + scale / 100);
            nWall.setWidth(scale + scale / 100);
            nWall.setY(9 * scale / 10);
            nWall.setX(0);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.westWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale + scale / 100);
            nWall.setWidth(scale / 10 + scale / 100);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.isGhostDoor()) {
            var ghostDoor = new Rectangle();
            ghostDoor.setHeight(scale / 10 + scale / 100);
            ghostDoor.setWidth(scale + scale / 100);
            ghostDoor.setY(-1.5);
            ghostDoor.setX(0);
            ghostDoor.setFill(Color.WHITE);
            group.getChildren().add(ghostDoor);
        }
        return new GraphicsUpdater() {
            @Override
            public void update() {
                dot.setVisible(!state.getGridState(pos));
                zhonya.setVisible(!state.getGridState(pos));
                vitessePImage.setVisible(!state.getGridState(pos));
                vitesseMImage.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }

            @Override
            public void changeColor(Color color) {
                for (Node node : group.getChildren()) {
                    if (node instanceof Rectangle) {
                        Rectangle originalWall = (Rectangle) node;
                        originalWall.setFill(color);
                    }
                }
            }
        };
    }
}