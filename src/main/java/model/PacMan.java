package model;

import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;

import config.Cell;
import geometry.RealCoordinates;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether
 * singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;
    private Timeline temps = new Timeline(new KeyFrame(Duration.seconds(7)));
    private boolean iszhonya = false;
    private Timeline temps_zhonya = new Timeline(new KeyFrame(Duration.seconds(5)));
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
        return isEnergized() ? 6 *deltaTNanoSeconds * 1E-9 : 4 * deltaTNanoSeconds * 1E-9;
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
    /**
     *
     * @return whether Pac-Man just ate an energizer
     */
    public boolean isEnergized() {
        // TODO handle timeout!

        return energized;
    }

    public void setEnergized(boolean energized) {
        this.energized = energized;
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
            if(isEnergized()){
                temps.playFrom(Duration.seconds(temps.getCurrentTime().toSeconds()));
            }
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
                maze.setGridState(true, pos.round().y(), pos.round().x());
                iszhonya = true;
                temps_zhonya.play();
                if(isEnergized()){
                    temps.pause();
                }

            }
           
        }
    }

    public void handleCollisionsWithGhosts(MazeState maze) {
        var pacPos = PacMan.INSTANCE.getPos().round();
        for (var critter : maze.getCritters()) {
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos) && !((Ghost) critter).isMort() && !iszhonya) {
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