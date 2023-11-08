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
    private final boolean disableGhost = false;
    private boolean disableEnergizer = false;

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
    public double getSpeed() {
        if (disableGhost) {
            return 0;
        } else if (mort) {
            return 0.15;
        } else if (!sortie) {
            return 0.05;
        } else {
            return 0.04;
        }
    }

    @Override
    public RealCoordinates nextPos(long deltaTNanoSeconds, MazeConfig config) {
        if (mort) {
            outil.animation_mort(this, initialPos, config, deltaTNanoSeconds);
        } else if (!sortie) {
            if (deltaTNanoSeconds - temps > 1E7) {
                sortie = outil.animation_sortie(this, config);
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
            if (PacMan.INSTANCE.isEnergized() && !disableEnergizer) {
                directions = outil.inverse(directions);
            }
            return outil.nextPos(directions, this, config);
        }

        return pos;
    }

}