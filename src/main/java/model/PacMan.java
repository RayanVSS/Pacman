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
    private LocalTime temps;
    public boolean isDead = false;

    private PacMan() {
    }

    public static final PacMan INSTANCE = new PacMan();

    @Override
    public RealCoordinates getPos() {
        return pos;
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

    public void handlePacManPoints(MazeState maze) {
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
