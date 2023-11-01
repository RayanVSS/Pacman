package model;

import config.MazeConfig;
import geometry.RealCoordinates;
import geometry.IntCoordinates;

public class outil {

    public static Direction[] tri(Direction[] directions, double[] distances) {
        int n = distances.length;
        for (int i = 1; i < n; i++) {
            Direction currentDirection = directions[i];
            double currentDistance = distances[i];
            int j = i - 1;

            while (j >= 0 && distances[j] > currentDistance) {
                directions[j + 1] = directions[j];
                distances[j + 1] = distances[j];
                j--;
            }

            directions[j + 1] = currentDirection;
            distances[j + 1] = currentDistance;
        }
        return directions;
    }

    public static Direction inverse(Direction dir) {
        switch (dir) {
            case NORTH:
                return Direction.SOUTH;
            case EAST:
                return Direction.WEST;
            case SOUTH:
                return Direction.NORTH;
            case WEST:
                return Direction.EAST;
            default:
                return Direction.NONE;
        }
    }

    public static Direction estpossible(Direction[] d, Ghost g, MazeConfig config) {
        for (Direction dir : d) {
            if (dir != inverse(g.getDirection())) {
                if (config.getCell(g.getPos().floor()).canMoveInDirection(dir)) {
                    if (dir == Direction.NONE) {
                        return g.getDirection();
                    }
                    return dir;
                }
            }

        }
        return g.getDirection();

    }

    public static double distance(RealCoordinates point1, RealCoordinates point2) {
        double deltaX = point1.x() - point2.x();
        double deltaY = point1.y() - point2.y();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public static RealCoordinates DirectionToRealCoordinates(Direction dir) {
        switch (dir) {
            case NORTH:
                return RealCoordinates.NORTH_UNIT;
            case EAST:
                return RealCoordinates.EAST_UNIT;
            case SOUTH:
                return RealCoordinates.SOUTH_UNIT;
            case WEST:
                return RealCoordinates.WEST_UNIT;
            default:
                return RealCoordinates.ZERO;
        }
    }
}
