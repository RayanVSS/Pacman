package model;

import config.MazeConfig;
import geometry.RealCoordinates;

public class Pinky {

    public static Direction nextDirection(Ghost PINKY, Critter PacMan, MazeConfig config) {
        RealCoordinates PacManPos = PacMan.getPos();
        RealCoordinates pinkyPos = PINKY.getPos();
        Direction[] possibleDirections = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST };
        double[] tabDistance = new double[4];
        int i = 0;
        for (Direction dir : possibleDirections) {
            RealCoordinates newPos = pinkyPos
                    .plus(outil.DirectionToRealCoordinates(dir).times(PINKY.getSpeed()));

            double distance = outil.distance(newPos, PacManPos);
            tabDistance[i] = distance;
            i += 1;
        }
        return outil.estpossible(outil.tri(possibleDirections, tabDistance), PINKY, config);
    }

}
