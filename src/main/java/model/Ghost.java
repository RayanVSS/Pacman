package model;

import config.MazeConfig;
import geometry.RealCoordinates;

public enum Ghost implements Critter {

    // TODO: implement a different AI for each ghost, according to the description
    // in Wikipedia's page
    BLINKY, INKY, PINKY, CLYDE;

    private long temps = 0;
    private RealCoordinates pos;
    private Direction direction = Direction.NONE;
    private boolean sortie = false;

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;
    }

    @Override
    public RealCoordinates nextPos(long deltaTNanoSeconds, MazeConfig config) {
        if (this == PINKY) {
            if (!sortie && deltaTNanoSeconds > 1E7) {
                setPos(new RealCoordinates(7, 4));
                sortie = true;
            } else if (sortie) {
                Direction d = Pinky.nextDirection(PINKY, PacMan.INSTANCE, config);
                setDirection(d);
                setPos(getPos().plus(outil.DirectionToRealCoordinates(d).times(getSpeed())));
                return pos;
            }
        } else if (this == CLYDE) {
            if (!sortie && deltaTNanoSeconds > 1E7) {
                setPos(new RealCoordinates(7, 4));
                sortie = true;
            } else if (sortie) {
                Direction d = Clyde.nextDirection(config, CLYDE, PacMan.INSTANCE);
                setDirection(d);
                setPos(getPos().plus(outil.DirectionToRealCoordinates(d).times(getSpeed())));
                return pos;
            }
        } else if (this == INKY) {
            if (!sortie && deltaTNanoSeconds > 1E7) {
                setPos(new RealCoordinates(7, 4));
                sortie = true;
            } else if (sortie) {
                Direction d = Inky.nextDirection(BLINKY, INKY, PacMan.INSTANCE, config);
                setDirection(d);
                setPos(getPos().plus(outil.DirectionToRealCoordinates(d).times(getSpeed())));
                return pos;
            }

        } else {
            /*
             * Direction d = Blinky.nextDirection(BLINKY, PacMan.INSTANCE, config);
             * setDirection(d);
             * setPos(getPos().plus(outilghost.DirectionToRealCoordinates(d).times(getSpeed(
             * ))));
             * return pos;
             */
        }
        return pos;
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
        if (this == PINKY) {
            return 0.02;
        }
        if (this == CLYDE) {
            return 0.01;
        }
        if (this == INKY) {
            return 0.01;
        }
        return 0;

    }

}