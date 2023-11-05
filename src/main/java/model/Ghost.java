package model;

import config.MazeConfig;
import geometry.RealCoordinates;

public enum Ghost implements Critter {
    
    BLINKY, INKY, PINKY, CLYDE;

    private long temps = 0;
    private RealCoordinates initialPos;
    private RealCoordinates pos;
    private Direction direction = Direction.NONE;
    private boolean sortie = false;
    private boolean mort = false;

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

    public void setTemps(long tp) {
        this.temps = tp;
    }

    @Override
    public double getSpeed() {
        return 0.04;
    }

    @Override
    public RealCoordinates nextPos(long deltaTNanoSeconds, MazeConfig config) {
        if (mort) {
            outil.animation_mort(this, initialPos, config, deltaTNanoSeconds);
        } else if (!sortie) {
            if (this == BLINKY && deltaTNanoSeconds - temps > 1E7) {
                sortie = outil.animation_sortie(BLINKY);
            } else if (this == PINKY && deltaTNanoSeconds - temps > 1E7) {
                sortie = outil.animation_sortie(PINKY);
            } else if (this == INKY && deltaTNanoSeconds - temps > 1E7) {
                sortie = outil.animation_sortie(INKY);
            } else if (this == CLYDE && deltaTNanoSeconds - temps > 1E7) {
                sortie = outil.animation_sortie(CLYDE);
            }
        } else {
            Direction[] directions;

            if (this == PINKY) {
                directions = Pinky.nextDirection(PINKY, PacMan.INSTANCE);
            } else if (this == CLYDE) {
                directions = Clyde.nextDirection(CLYDE, PacMan.INSTANCE, config);
            } else if (this == INKY) {
                directions = Inky.nextDirection(BLINKY, INKY, PacMan.INSTANCE);
            } else {
                directions = Blinky.nextDirection(BLINKY, PacMan.INSTANCE.getPos());
            }
            if (PacMan.INSTANCE.isEnergized()) {
                directions = outil.inverse(directions);
            }
            return outil.nextPos(directions, this, config);
        }

        return pos;
    }

    public boolean isMort() {
        return mort;
    }
}