package model;

import config.MazeConfig;
import geometry.RealCoordinates;
import java.util.List;

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

    public static boolean fin_sortie(Ghost g) {
        RealCoordinates currentPos = g.getPos();
        RealCoordinates targetPos = new RealCoordinates(7.0, 4.0);

        if (currentPos.x() >= 6.9 && currentPos.x() <= 7.1 && currentPos.y() >= 3.9 && currentPos.y() <= 4.1) {
            return true;
        } else {
            double speed = g.getSpeed();
            double deltaX = targetPos.x() - currentPos.x();
            double deltaY = targetPos.y() - currentPos.y();

            if (!(deltaX >= -0.1 && deltaX <= 0.1)) {
                // Déplacement horizontal
                double newX = currentPos.x() + (deltaX > 0.0 ? speed : -speed);
                g.setPos(new RealCoordinates(newX, currentPos.y()));
            } else if (!(deltaY >= -0.1 && deltaY <= 0.1)) {
                // Déplacement vertical
                double newY = currentPos.y() + (deltaY > 0.0 ? speed : -speed);
                g.setPos(new RealCoordinates(currentPos.x(), newY));
            }

            return false;
        }
    }

    @Override
    public RealCoordinates nextPos(long deltaTNanoSeconds, MazeConfig config) {
        if (!sortie) {
            if (this == PINKY && deltaTNanoSeconds - temps > 2E7) {
                sortie = fin_sortie(PINKY);
            } else if (this == CLYDE && deltaTNanoSeconds - temps > 4E7) {
                sortie = fin_sortie(CLYDE);
            } else if (this == INKY && deltaTNanoSeconds - temps > 3E7) {
                sortie = fin_sortie(INKY);
            } else if (this == BLINKY && deltaTNanoSeconds - temps > 1E7) {
                sortie = fin_sortie(BLINKY);
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
                directions = Blinky.nextDirection(BLINKY, PacMan.INSTANCE);
            }

            return outil.nextPos(directions, this, config);
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
        if (this == BLINKY) {
            return 0.04;
        }
        if (this == PINKY) {
            return 0.04;
        }
        return 0.02;
    }
}