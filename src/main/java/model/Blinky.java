package model;

import geometry.RealCoordinates;


public final class Blinky implements Critter {

    private RealCoordinates pos;
    private Direction direction;
    private double speed;

    public Blinky(RealCoordinates initialPos, double initialSpeed) {
        pos = initialPos;
        direction = Direction.NONE; // Initialiser la direction à NONE
        speed = initialSpeed;
    }

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;
    }

    @Override
    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    // Méthode pour mettre à jour la position de Blinky
    @Override
    public RealCoordinates nextPos(long deltaTNanoSeconds,MazeConfig config) {
        return getPos().plus((switch (getDirection()) {
            case NONE -> RealCoordinates.ZERO;
            case NORTH -> RealCoordinates.NORTH_UNIT;
            case EAST -> RealCoordinates.EAST_UNIT;
            case SOUTH -> RealCoordinates.SOUTH_UNIT;
            case WEST -> RealCoordinates.WEST_UNIT;
        }).times(getSpeed()*deltaTNanoSeconds * 1E-9));
    }
    
}







