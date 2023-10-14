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
                    .plus(outilghost.DirectionToRealCoordinates(dir).times(PINKY.getSpeed()));

            double distance = outilghost.distance(newPos, PacManPos);
            tabDistance[i] = distance;
            i += 1;
        }
        return mDirection(outilghost.tri(possibleDirections, tabDistance), config, PINKY);
    }

    public static Direction mDirection(Direction[] tab, MazeConfig config, Ghost PINKY) {
        for (Direction dir : tab) {
            RealCoordinates newPos = PINKY.getPos()
                    .plus(outilghost.DirectionToRealCoordinates(dir).times(PINKY.getSpeed()));
            if (newPos.x() == PINKY.getPos().x() || newPos.y() == PINKY.getPos().y()) {
                return dir;
            }

        }
        return PINKY.getDirection();
    }

}
