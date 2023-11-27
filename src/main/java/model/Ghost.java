package model;

import config.MazeConfig;
import geometry.RealCoordinates;

public enum Ghost implements Critter {

    BLINKY, INKY, PINKY, CLYDE;

    private RealCoordinates initialPos;
    private RealCoordinates pos;
    private Direction direction = Direction.NONE;
    private boolean sortie = false;
    private boolean mort = false;
    private final boolean disableGhost = false;
    private boolean disableEnergizer = false;

    private long creationTime = System.nanoTime();
    private int respawnSeconds;

    public void setTemps() {
        creationTime = System.nanoTime();
        if (mort) {
            return;
        } else {
            if (this == BLINKY) {
                respawnSeconds = 1;
            } else if (this == PINKY) {
                respawnSeconds = 3;
            } else if (this == INKY) {
                respawnSeconds = 5;
            } else {
                respawnSeconds = 7;
            }
        }
    }

    public boolean isRespawnTime() {
        long elapsedSeconds = (System.nanoTime() - creationTime) / 1_000_000_000;
        return elapsedSeconds >= respawnSeconds;
    }

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    public boolean isSortie() {
        return sortie;
    }

    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;
    }

    public void setMort(boolean mort) {
        this.mort = mort;
    }

    public void setInitialPos(RealCoordinates initialPos) {
        this.initialPos = initialPos;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public void setSortie(boolean sortie) {
        this.sortie = sortie;
    }

    public void setDisableEnergizer(boolean disableEnergizer) {
        this.disableEnergizer = disableEnergizer;
    }

    public boolean getDisableEnergizer() {
        return disableEnergizer;
    }

    public boolean isMort() {
        return mort;
    }

    @Override
    public double getSpeed(long deltaTNanoSeconds) {
        if (mort) {
            return 4 * deltaTNanoSeconds * 1E-9;
        } else if (disableGhost) {
            return 0;
        } else if (!sortie) {
            return 5 * deltaTNanoSeconds * 1E-9;
        } else {
            return 4 * deltaTNanoSeconds * 1E-9;
        }
    }

    public RealCoordinates nextPos(MazeConfig config, long deltaTNanoSeconds) {
        long elapsedSeconds = (System.nanoTime() - creationTime) / 1_000_000_000;
        System.out.println(elapsedSeconds);
        if (mort) {
            outil.animation_mort(this, initialPos, config, deltaTNanoSeconds);
            if (!mort) {
                mort = true;
                setTemps();
                mort = false;
            }
        } else if (!sortie) {
            if (isRespawnTime()) {
                sortie = outil.animation_sortie(this, config, deltaTNanoSeconds);
            }
        } else {
            Direction[] directions;

            if (this == PINKY) {
                directions = Pinky.nextDirection(PINKY, PacMan.INSTANCE, deltaTNanoSeconds);
            } else if (this == CLYDE) {
                directions = Clyde.nextDirection(CLYDE, PacMan.INSTANCE, config, deltaTNanoSeconds);
            } else if (this == INKY) {
                directions = Inky.nextDirection(BLINKY, INKY, PacMan.INSTANCE, deltaTNanoSeconds);
            } else {
                directions = Blinky.nextDirection(BLINKY, PacMan.INSTANCE.getPos(), deltaTNanoSeconds);
            }
            if (PacMan.INSTANCE.isEnergized() && !disableEnergizer) {
                directions = outil.inverse(directions);
            }
            return outil.nextPos(directions, this, config, deltaTNanoSeconds);
        }

        return pos;
    }

}