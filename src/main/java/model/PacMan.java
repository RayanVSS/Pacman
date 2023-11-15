package model;

import lib.Vector2D;

import java.time.LocalTime;

import config.Cell;
import geometry.RealCoordinates;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether
 * singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private Vector2D velocity = new Vector2D(0, 0);
    private RealCoordinates pos;
    private boolean energized;
    private boolean canCollide = true;
    private LocalTime temps;

    private PacMan() {
    }

    public static final PacMan INSTANCE = new PacMan();

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    public void disableCollision() {
        canCollide = false;
    }

    public boolean canCollide() {
        return canCollide;
    }

    public void enableCollision() {
        canCollide = true;
    }

    @Override
    public double getSpeed() {
        return isEnergized() ? 6 : 4;
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

    public void setTemps(LocalTime tp) {
        this.temps = tp;
    }

    public LocalTime getTemps() {
        return temps;
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

    public void setVelocity() {
        switch (direction) {
            case NONE:
                velocity.set(0, 0);
            case NORTH:
                velocity.set(0, getSpeed());
            case SOUTH:
                velocity.set(0, -getSpeed());
            case WEST:
                velocity.set(-getSpeed(), 0);
            case EAST:
                velocity.set(getSpeed(), 0);
        }
    }

    public boolean verif_fin() {
        return LocalTime.now().getSecond() >= temps.getSecond();
    }

    public void fin_energizer(MazeState maze, long deltaTNanoSeconds) {
        if (isEnergized() && verif_fin()) {
            setEnergized(false);
            for (var critter : maze.getCritters()) {
                if (critter instanceof Ghost) {
                    ((Ghost) critter).setDisableEnergizer(true);
                }
            }
        }
    }

    public void handlePacManPoints(MazeState maze, long deltaTNanoSeconds) {
        if (!maze.getGridState(pos.round())) {
            if (maze.getConfig().getCell(pos.round()).getContent() == Cell.Content.ENERGIZER) {
                maze.addScore(10);
                maze.setGridState(true, pos.round().y(), pos.round().x());
                setEnergized(true);
                setTemps(LocalTime.now().plusSeconds(10));
                for (var critter : maze.getCritters()) {
                    if (critter instanceof Ghost) {
                        ((Ghost) critter).setDisableEnergizer(false);
                    }
                }
            } else if (maze.getConfig().getCell(pos.round()).getContent() == Cell.Content.DOT) {
                maze.addScore(1);
                maze.setGridState(true, pos.round().y(), pos.round().x());
            }
        }
    }

    public void handleCollisionsWithGhosts(MazeState maze) {
        var pacPos = PacMan.INSTANCE.getPos().round();
        for (var critter : maze.getCritters()) {
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos) && !((Ghost) critter).isMort()) {
                handleGhostCollision(maze, (Ghost) critter);
            }
        }
    }

    private void handleGhostCollision(MazeState maze, Ghost ghost) {
        if (isEnergized() && !ghost.getDisableEnergizer()) {
            maze.addScore(10);
            ghost.setMort(true);
            maze.resetCritter(ghost);
        } else {
            maze.playerLost();
        }
    }

}
