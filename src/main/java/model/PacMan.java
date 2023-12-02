package model;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.animation.KeyFrame;

import config.Cell;
import geometry.RealCoordinates;
import gui.AppStateMachine.PlayingState;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether
 * singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;
    private Timeline temps = new Timeline(new KeyFrame(Duration.seconds(5)));
    private boolean iszhonya = false;
    private boolean isvitesseP = false;
    private boolean isvitesseM = false;
    private Timeline temps_zhonya = new Timeline(new KeyFrame(Duration.seconds(3)));
    private Timeline temps_vitesseP = new Timeline(new KeyFrame(Duration.seconds(3)));
    private Timeline temps_vitesseM = new Timeline(new KeyFrame(Duration.seconds(3)));
    public boolean isDead = false;


    private PacMan() {
        temps.setCycleCount(1);
    }

    public static final PacMan INSTANCE = new PacMan();

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public double getSpeed(long deltaTNanoSeconds) {
        return isvitesseP ? 7 *deltaTNanoSeconds * 1E-9 : (isEnergized() ? 6 *deltaTNanoSeconds * 1E-9 : 4 * deltaTNanoSeconds * 1E-9);
    }
    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void setPos(RealCoordinates pos) {
        this.pos = pos;
    }

    public boolean getzhonya(){
        return iszhonya;
    }

    public double getTemps(){
        return temps.getCycleDuration().toSeconds();
    }

    public double getTempsCourant(){
        return temps.getCurrentTime().toSeconds();
    }

    public Double getTempsZhonya(){
        return temps_zhonya.getCycleDuration().toSeconds();
    }

    public Double getTempsCourantZhonya(){
        return temps_zhonya.getCurrentTime().toSeconds();
    }
    /**
     *
     * @return whether Pac-Man just ate an energizer
     */
    public boolean isEnergized() {
        return energized;
    }
    public boolean isvitesseM() {
        return isvitesseM;
    }

    public void setEnergized(boolean energized) {
        this.energized = energized;
    }

    public void resetZhonya() {
        if(iszhonya){
            temps_zhonya.stop();
            iszhonya = false;
        }
    }

    public void resetVitesseP() {
        if(isvitesseP){
            temps_vitesseP.stop();
            isvitesseP = false;
        }
    }

    public void resetVitesseM() {
        if(isvitesseM){
            temps_vitesseM.stop();
            isvitesseM = false;
        }
    }

    public boolean verif_fin() {
        return temps.getStatus() == Timeline.Status.STOPPED;
    }

    public void fin_energizer(MazeState maze) {
        if (isEnergized() && verif_fin()) {
            setEnergized(false);
            for (var critter : maze.getCritters()) {
                if (critter instanceof Ghost) {
                    ((Ghost) critter).setDisableEnergizer(false);
                }
            }
        }
    }

    public void fin_zhonya(MazeState maze) {
        if (iszhonya && temps_zhonya.getStatus() == Timeline.Status.STOPPED) {
            iszhonya = false;
            Platform.runLater(() -> {
                PlayingState.getInstance().changeWallToBlue();
            });
            if(isEnergized()){
                temps.playFrom(Duration.seconds(temps.getCurrentTime().toSeconds()));
            }
        }
    }

    public void fin_vitesseP(MazeState maze) {
        if (isvitesseP && temps_vitesseP.getStatus() == Timeline.Status.STOPPED) {
            isvitesseP = false;
            Platform.runLater(() -> {
                PlayingState.getInstance().changeWallToBlue();
            });
        }
    }

    public void fin_vitesseM(MazeState maze) {
        if (isvitesseM && temps_vitesseM.getStatus() == Timeline.Status.STOPPED) {
            isvitesseM = false;
            Platform.runLater(() -> {
                PlayingState.getInstance().changeWallToBlue();
            });
        }
    }

    public void handlePacManPoints(MazeState maze) {
        if (!maze.getGridState(pos.round())) {
            if (maze.getConfig().getCell(pos.round()).getContent() == Cell.Content.ENERGIZER ) {
                maze.addScore(10);
                maze.setGridState(true, pos.round().y(), pos.round().x());
                if(isEnergized()){
                    temps.playFrom(Duration.seconds(0));
                }
                else{
                    setEnergized(true);
                    temps.play();
                }            
                for (var critter : maze.getCritters()) {
                    if (critter instanceof Ghost) {
                        ((Ghost) critter).setDisableEnergizer(false);
                    }
                }
            } else if (maze.getConfig().getCell(pos.round()).getContent() == Cell.Content.DOT) {
                maze.addScore(1);
                maze.setGridState(true, pos.round().y(), pos.round().x());
            }
            else if(maze.getConfig().getCell(pos.round()).getContent() == Cell.Content.ZHONYA){
                maze.addScore(50);
                Platform.runLater(() -> {
                    PlayingState.getInstance().changeWallToKhaki();
                });
                maze.setGridState(true, pos.round().y(), pos.round().x());
                iszhonya = true;
                temps_zhonya.play();
                if(isEnergized()){
                    temps.pause();
                }

        } else if(maze.getConfig().getCell(pos.round()).getContent() == Cell.Content.vitesseP){
            maze.addScore(50);
            Platform.runLater(() -> {
                PlayingState.getInstance().changeWallToRoyalBlue();
            });
            maze.setGridState(true, pos.round().y(), pos.round().x());
            isvitesseP = true;
            temps_vitesseP.play();

            }
            else if(maze.getConfig().getCell(pos.round()).getContent() == Cell.Content.vitesseM){
                maze.addScore(50);
                Platform.runLater(() -> {
                    PlayingState.getInstance().changeWallToRed();
                });
                maze.setGridState(true, pos.round().y(), pos.round().x());
                isvitesseM = true;
                temps_vitesseM.play();
                }
        else if(maze.getConfig().getCell(pos.round()).getContent() == Cell.Content.HEAL){
            maze.addScore(50);
            maze.setGridState(true, pos.round().y(), pos.round().x());
            maze.setLives(maze.getLives()+1);
            }
        }
    }

    public void handleCollisionsWithGhosts(MazeState maze) {
        var pacPos = PacMan.INSTANCE.getPos().round();
        for (var critter : maze.getCritters()) {
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos) && !((Ghost) critter).isMort() && !iszhonya) {
                Platform.runLater(() -> {
                    PlayingState.getInstance().changeWallToBlue();
                });
                maze.setGridState(true, pos.round().y(), pos.round().x());
                handleGhostCollision(maze, (Ghost) critter);
            }
        }
    }

    private void handleGhostCollision(MazeState maze, Ghost ghost) {
        if (isEnergized() && !ghost.getDisableEnergizer()) {
            maze.addScore(10);
            ghost.setMort(true);
            maze.resetGhost(ghost);
        } else {

            if (!isDead)
                maze.playerLost();
        }
    }

    public void playDeathAnimation() {
        System.out.println("PacMan is dead");
        isDead = true;
        // Now we need to play the death animation by changing the sprite

    }

}