package model;

import config.MazeConfig;
import geometry.RealCoordinates;
import gui.Controller.PacmanController;

import java.util.List;

public enum Ghost implements Critter {

    // TODO: implement a different AI for each ghost, according to the description
    // in Wikipedia's page
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
        if (this == BLINKY) {
            return 0.04;
        }
        if (this == PINKY) {
            return 0.04;
        }
        return 0.02;
    }

    @Override
    public RealCoordinates nextPos(long deltaTNanoSeconds, MazeConfig config) {
        if (mort) {
            outil.animation_mort(this, initialPos, config, deltaTNanoSeconds);
        } else if (!sortie) {
            if (this == PINKY && deltaTNanoSeconds - temps > 2E7) {
                sortie = outil.animation_sortie(PINKY);
            } else if (this == CLYDE && deltaTNanoSeconds - temps > 4E7) {
                sortie = outil.animation_sortie(CLYDE);
            } else if (this == INKY && deltaTNanoSeconds - temps > 3E7) {
                sortie = outil.animation_sortie(INKY);
            } else if (this == BLINKY && deltaTNanoSeconds - temps > 1E7) {
                sortie = outil.animation_sortie(BLINKY);
                if (sortie) {
                    temps = deltaTNanoSeconds;
                }
            }
        } else {
            Direction[] directions;

            if (this == PINKY) {
                directions = Pinky.nextDirection(PINKY, PacMan.INSTANCE);
            } else if (this == CLYDE) {
                directions = Clyde.nextDirection(CLYDE, PacMan.INSTANCE);
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
}