package model;

import geometry.RealCoordinates;

public class Blinky {
    public static Direction[] nextDirection(Ghost Blinky, Critter PacMan) {
        RealCoordinates PacManPos = PacMan.getPos();
        RealCoordinates BlinkyPos = Blinky.getPos();
        Direction[] possibleDirections = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST };
        double[] tabDistance = new double[4];
        int i = 0;
        for (Direction dir : possibleDirections) {
            RealCoordinates newPos = BlinkyPos
                    .plus(outil.DirectionToRealCoordinates(dir).times(Blinky.getSpeed()));

            double distance = outil.distance(newPos, PacManPos);
            tabDistance[i] = distance;
            i += 1;
        }
        return outil.tri(possibleDirections, tabDistance);
    }
}
