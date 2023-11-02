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

    public static RealCoordinates nextPos(Direction[] d, Ghost g, MazeConfig config) {
        for (Direction dir : d) {
            if (config.getCell(g.getPos().round()).canMoveInDirection(dir) && dir != inverse(g.getDirection())) {
                switch (dir) {
                    case NORTH, SOUTH -> {
                        if (g.getDirection() == Direction.EAST || g.getDirection() == Direction.WEST) {
                            int w = g.getPos().round().x();
                            if (g.getPos().x() <= w || true) {
                                g.setDirection(dir);
                                return new RealCoordinates(w, g.getPos().y());
                            }
                        } else {
                            g.setDirection(dir);
                            return g.getPos().plus(DirectionToRealCoordinates(dir).times(g.getSpeed()));
                        }
                    }

                    case EAST, WEST -> {
                        if (g.getDirection() == Direction.SOUTH || g.getDirection() == Direction.NORTH) {
                            int s = g.getPos().round().y();
                            if (g.getPos().y() <= s || true) {
                                g.setDirection(dir);
                                return new RealCoordinates(g.getPos().x(), s);

                            }
                        } else {
                            g.setDirection(dir);
                            return g.getPos().plus(DirectionToRealCoordinates(dir).times(g.getSpeed()));
                        }
                    }
                }
            }
        }
        return g.getPos().plus(DirectionToRealCoordinates(g.getDirection()).times(g.getSpeed()));
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
