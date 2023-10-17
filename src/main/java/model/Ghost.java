package model;

import config.MazeConfig;
import geometry.RealCoordinates;

public enum Ghost implements Critter {

    // TODO: implement a different AI for each ghost, according to the description
    // in Wikipedia's page
    BLINKY, INKY, PINKY, CLYDE;

    private RealCoordinates pos;
    private Direction direction = Direction.NONE;

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
            Direction d = Pinky.nextDirection(PINKY, PacMan.INSTANCE, config);
            setDirection(d);
            setPos(getPos().plus(outilghost.DirectionToRealCoordinates(d).times(getSpeed())));
            return pos;
        } else if (this == CLYDE) {
            Direction d = Clyde.nextDirection(config, CLYDE, PacMan.INSTANCE);
            setDirection(d);
            setPos(getPos().plus(outilghost.DirectionToRealCoordinates(d).times(getSpeed())));
            return pos;
        } else if (this == INKY) {
            Direction d = Inky.nextDirection(BLINKY, INKY, PacMan.INSTANCE, config);
            setDirection(d);
            setPos(getPos().plus(outilghost.DirectionToRealCoordinates(d).times(getSpeed())));
            return pos;
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

    @Override
    public double getSpeed() {
        if (this == PINKY) {
            return 3E-2;
        }
        if (this == CLYDE) {
            return 2E-2;
        }
        if (this == INKY) {
            return 4E-2;
        }
        return 0;

    }

}