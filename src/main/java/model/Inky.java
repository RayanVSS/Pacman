package model;

import config.MazeConfig;
import geometry.RealCoordinates;

public class Inky {
    public static Direction nextDirection(Ghost INKY, Ghost BLINKY, Critter PacMan, MazeConfig config) {
        RealCoordinates pacManPos = PacMan.getPos();
        RealCoordinates inkyPos = INKY.getPos();
        RealCoordinates blinkyPos = BLINKY.getPos();

        RealCoordinates targetPos = new RealCoordinates(
                pacManPos.x() + 2 * (pacManPos.x() - blinkyPos.x()),
                pacManPos.y() + 2 * (pacManPos.y() - blinkyPos.y()));

        Direction[] possibleDirections = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST
        };
        double[] tabDistance = new double[4];
        int i = 0;
        for (Direction dir : possibleDirections) {
            RealCoordinates newPos = inkyPos
                    .plus(outil.DirectionToRealCoordinates(dir).times(INKY.getSpeed()));

            double distance = outil.distance(newPos, targetPos);
            tabDistance[i] = distance;
            i += 1;
        }
        return outil.estpossible(outil.tri(possibleDirections, tabDistance), INKY, config);
    }
}
