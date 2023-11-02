package model;

import geometry.RealCoordinates;

public class Pinky {

    public static Direction[] nextDirection(Ghost pinky, Critter pacman) {
        RealCoordinates pacManPos = pacman.getPos();
        RealCoordinates pinkyPos = pinky.getPos();
        Direction[] possibleDirections = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST
        };
        RealCoordinates targetPos = pacManPos
                .plus(outil.DirectionToRealCoordinates(pacman.getDirection()).times(2 * pacman.getSpeed()));

        double[] tabDistance = new double[4];
        int i = 0;

        for (Direction dir : possibleDirections) {
            RealCoordinates newPos = pinkyPos.plus(outil.DirectionToRealCoordinates(dir).times(pinky.getSpeed()));
            double distance = outil.distance(newPos, targetPos);
            tabDistance[i] = distance;
            i++;
        }

        return outil.tri(possibleDirections, tabDistance);
    }

}
