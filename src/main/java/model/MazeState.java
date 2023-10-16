package model;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.App;
import gui.AppStateMachine.PlayingState;
import gui.Controller.PacmanController;
import gui.AppStateMachine.GameOverState;

import java.util.List;
import java.util.Map;

import static model.Ghost.*;

public final class MazeState {

    private final MazeConfig config;
    private final int height;
    private final int width;

    private final boolean[][] gridState;

    private final List<Critter> critters;
    private static int score;

    private final Map<Critter, RealCoordinates> initialPos;
    private int lives = 3;

    public MazeState(MazeConfig config) {
        this.config = config;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, Ghost.CLYDE, BLINKY, INKY, PINKY);
        gridState = new boolean[height][width];
        initialPos = Map.of(
                PacMan.INSTANCE, config.getPacManPos().toRealCoordinates(1.0),
                BLINKY, config.getBlinkyPos().toRealCoordinates(1.0),
                INKY, config.getInkyPos().toRealCoordinates(1.0),
                CLYDE, config.getClydePos().toRealCoordinates(1.0),
                PINKY, config.getPinkyPos().toRealCoordinates(1.0));
        resetCritters();
    }

    public List<Critter> getCritters() {
        return critters;
    }

    public double getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean canMoveInDirection(Direction direction, IntCoordinates pos) {
        if (config.getCell(pos).canMoveInDirection(direction))
            return true;
        return false;
    }

    private void handleWallCollisions(Critter critter, long deltaTns) {
        var curPos = critter.getPos();
        var nextPos = critter.nextPos(deltaTns, config);
        var curNeighbours = curPos.intNeighbours();
        var nextNeighbours = nextPos.intNeighbours();
        if (!curNeighbours.containsAll(nextNeighbours)) { // the critter would overlap new cells. Do we allow it?
            switch (critter.getDirection()) {
                case NORTH -> {
                    for (var n : curNeighbours)
                        if (config.getCell(n).northWall()) {
                            if (critter instanceof PacMan)
                                System.out.println("mur du nord");
                            nextPos = curPos.floorY();
                            if (critter instanceof PacMan) {
                                critter.setDirection(PacmanController.nextDirection);
                                PacmanController.currentDirection = PacmanController.nextDirection;
                                PacmanController.nextDirection = Direction.NONE;
                            } else
                                critter.setDirection(Direction.NONE);
                            break;
                        }
                }
                case EAST -> {
                    for (var n : curNeighbours)
                        if (config.getCell(n).eastWall()) {
                            if (critter instanceof PacMan) {
                                System.out.println("mur de l'est");
                                System.out.println(PacmanController.nextDirection);
                            }
                            nextPos = curPos.ceilX();
                            if (critter instanceof PacMan) {
                                critter.setDirection(PacmanController.nextDirection);
                                PacmanController.currentDirection = PacmanController.nextDirection;
                                PacmanController.nextDirection = Direction.NONE;
                            } else
                                critter.setDirection(Direction.NONE);
                            break;
                        }
                }
                case SOUTH -> {
                    for (var n : curNeighbours)
                        if (config.getCell(n).southWall()) {
                            if (critter instanceof PacMan)
                                System.out.println("mur du sud");
                            nextPos = curPos.ceilY();
                            if (critter instanceof PacMan) {
                                critter.setDirection(PacmanController.nextDirection);
                                PacmanController.currentDirection = PacmanController.nextDirection;
                                PacmanController.nextDirection = Direction.NONE;
                            } else
                                critter.setDirection(Direction.NONE);
                            break;
                        }
                }
                case WEST -> {
                    for (var n : curNeighbours)
                        if (config.getCell(n).westWall()) {
                            if (critter instanceof PacMan)
                                System.out.println("mur de l'ouest");
                            nextPos = curPos.floorX();
                            if (critter instanceof PacMan) {
                                critter.setDirection(PacmanController.nextDirection);
                                PacmanController.currentDirection = PacmanController.nextDirection;
                                PacmanController.nextDirection = Direction.NONE;
                            } else
                                critter.setDirection(Direction.NONE);
                            break;
                        }
                }
            }

        }

        critter.setPos(nextPos.warp(width, height));
    }

    public void update(long deltaTns) {
        for (var critter : critters) {
            handleWallCollisions(critter, deltaTns);
        }
        PacMan.INSTANCE.handlePacManPoints(this);
        PacMan.INSTANCE.handleCollisionsWithGhosts(this);
    }

    public void addScore(int increment) {
        score += increment * 10;
        PlayingState.score_graphics.setText("" + score);
        displayScore();
    }

    private void displayScore() {
        System.out.println("Score: " + score);
    }

    public void playerLost() {
        // FIXME: this should be displayed in the JavaFX view, not in the console. A
        // game over screen would be nice too.
        lives--;
        PlayingState.life_graphics.setText("" + lives);
        if (lives == 0) {
            System.out.println("Game over!");
            App.app_state.changeState(GameOverState.getInstance());
        }
        System.out.println("Lives: " + lives);
        resetCritters();
    }

    public void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
    }

    private void resetCritters() {
        for (var critter : critters)
            resetCritter(critter);
    }

    public MazeConfig getConfig() {
        return config;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }

    public static int getScore() {
        return score;
    }

    public void setGridState(boolean b, int y, int x) {
        gridState[y][x] = b;
    }
}
