package model;

import java.util.List;

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
        if (this == INKY) {
            Direction d = Inky.nexDirectionInky(INKY, PacMan.INSTANCE, config);
            setDirection(d);
            setPos(getPos().plus(Inky.DirectionToRealCoordinates(d).times(getSpeed())));
            return pos;
        }
        return getPos().plus((switch (getDirection()) {
            case NONE -> RealCoordinates.ZERO;
            case NORTH -> RealCoordinates.NORTH_UNIT;
            case EAST -> RealCoordinates.EAST_UNIT;
            case SOUTH -> RealCoordinates.SOUTH_UNIT;
            case WEST -> RealCoordinates.WEST_UNIT;
        }).times(getSpeed() * deltaTNanoSeconds * 1E-9));
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
        if (this == INKY) {
            return 3E-2;
        }
        return 0;

    }

}