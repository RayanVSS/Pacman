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
        //set the gridState to true if it does not have a dot
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
                gridState[i][j] = !config.getCell(new IntCoordinates(j, i)).hasDot();
            }
        }
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
                    for (var n : curNeighbours) {
                        if (config.getCell(n).northWall()) {
                            nextPos = curPos.floorY();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                }
                case EAST -> {
                    for (var n : curNeighbours) {
                        if (config.getCell(n).eastWall()) {
                            nextPos = curPos.ceilX();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                }
                case SOUTH -> {
                    for (var n : curNeighbours) {
                        if (config.getCell(n).southWall()) {
                            nextPos = curPos.ceilY();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                }
                case WEST -> {
                    for (var n : curNeighbours) {
                        if (config.getCell(n).westWall()) {
                            nextPos = curPos.floorX();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                }
                default -> {
                    critter.setDirection(Direction.NONE);
                    break;
                }
            }
        }

            if(critter instanceof PacMan){
                if(config.getCell(critter.getPos().round()).canMoveInDirection(PacmanController.nextDirection)  /* && isPerfect(PacmanController.currentDirection, PacmanController.nextDirection, critter.getPos()) */){
                    switch(PacmanController.nextDirection){
                        case NORTH, SOUTH -> {
                            if(PacmanController.currentDirection == Direction.EAST){
                                int w = critter.getPos().round().x();
                                if(critter.getPos().x() <= w || true){
                                    critter.setPos(new RealCoordinates(w, critter.getPos().y()));
                                }
                                System.out.println("NS : EAST");
                            }
                            else if(PacmanController.currentDirection == Direction.WEST){
                                int e = critter.getPos().round().x();
                                if(critter.getPos().x() <= e || true){
                                    critter.setPos(new RealCoordinates(e, critter.getPos().y()));
                                }
                                System.out.println("NS : WEST");
                            }
                            else{
                                critter.setPos(nextPos.warp(width, height));
                            }
                            critter.setDirection(PacmanController.nextDirection);
                            PacmanController.currentDirection = PacmanController.nextDirection;
                        }

                        case EAST, WEST -> {
                            if(PacmanController.currentDirection == Direction.SOUTH){
                                int s = critter.getPos().round().y();
                                if(critter.getPos().y() <= s || true){
                                    critter.setPos(new RealCoordinates(critter.getPos().x(), s));
                                }
                                System.out.println("EW : SOUTH");
                            }
                            else if(PacmanController.currentDirection == Direction.NORTH){
                                int n = critter.getPos().round().y();
                                if(critter.getPos().y() <= n || true){
                                    critter.setPos(new RealCoordinates(critter.getPos().x(), n));
                                }
                                System.out.println("EW : NORTH");
                            }
                            else{
                                critter.setPos(nextPos.warp(width, height));
                            }
                            critter.setDirection(PacmanController.nextDirection);
                            PacmanController.currentDirection = PacmanController.nextDirection;
                        }
                    }
                }
                else{
                    critter.setPos(nextPos.warp(width, height));
                }
            }
        else{
        critter.setPos(nextPos.warp(width, height));
        }
    }

    public void update(Long deltaTns) {
        for (var critter : critters) {
            handleWallCollisions(critter, deltaTns);
        }
        PacMan.INSTANCE.handlePacManPoints(this);
        PacMan.INSTANCE.handleCollisionsWithGhosts(this);
        gameisWon();
    }

    public void gameisWon(){
        for(int i =0; i<gridState.length; i++){
            for(int j =0; j<gridState[i].length; j++){
                if(!gridState[i][j]){
                    return;
                }
            }
        }
        System.out.println("Game won!");
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
        lives--;
        PlayingState.getInstance().life_graphics_update(lives);
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

    public int getLives() {
        return lives;
    }

    public void setLives(int l) {
        lives = l;
        PlayingState.getInstance().life_graphics_update(l);
    }

    public static void resetScore() {
        score = 0;
    }
}
