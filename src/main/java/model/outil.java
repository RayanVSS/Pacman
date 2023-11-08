package model;

import config.MazeConfig;
import geometry.RealCoordinates;

public class outil {

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

    public static Direction[] inverse(Direction[] d) {
        Direction[] res = new Direction[d.length];
        for (int i = 0; i < d.length; i++) {
            res[i] = inverse(d[i]);
        }
        return res;
    }

    public static RealCoordinates nextPos(Direction[] d, Ghost g, MazeConfig config) {
        for (Direction dir : d) {
            if (config.getCell(g.getPos().round()).canMoveInDirection(dir) && dir != inverse(g.getDirection())) {
                switch (dir) {
                    case NORTH, SOUTH -> {
                        if (g.getDirection() == Direction.EAST || g.getDirection() == Direction.WEST) {
                            int x = g.getPos().round().x();
                            if (x - 0.1 <= g.getPos().x() && g.getPos().x() <= x) {
                                g.setDirection(dir);
                                return new RealCoordinates(x, g.getPos().y());
                            }
                        } else {
                            g.setDirection(dir);
                            return g.getPos().plus(DirectionToRealCoordinates(dir).times(g.getSpeed()));
                        }
                    }
                    case EAST, WEST -> {
                        if (g.getDirection() == Direction.SOUTH || g.getDirection() == Direction.NORTH) {
                            int y = g.getPos().round().y();
                            if (y - 0.1 <= g.getPos().y() && g.getPos().y() <= y) {
                                g.setDirection(dir);
                                return new RealCoordinates(g.getPos().x(), y);

                            }
                        }

                        else {
                            g.setDirection(dir);
                            return g.getPos().plus(DirectionToRealCoordinates(dir).times(g.getSpeed()));
                        }
                    }
                }
            }
        }
        return g.getPos().plus(DirectionToRealCoordinates(g.getDirection()).times(g.getSpeed()));
    }

    public static boolean animation_sortie(Ghost g, MazeConfig config) {
        RealCoordinates currentPos = g.getPos();
        RealCoordinates targetPos = new RealCoordinates(7.0, 4.0);

        if (currentPos.x() >= 6.9 && currentPos.x() <= 7.1 && currentPos.y() >= 3.9 && currentPos.y() <= 4.1) {
            g.setPos(targetPos);
            return true;
        } else {
            g.setPos(outil.nextPos(Blinky.nextDirection(g, new RealCoordinates(7.0, 4.0)), g, config));
            return false;
        }
    }

    public static void animation_mort(Ghost g, RealCoordinates initialPos, MazeConfig config, long deltaTNanoSeconds) {
        if (g.getPos().x() >= 6.9 && g.getPos().x() <= 7.1
                && g.getPos().y() >= 3.9 && g.getPos().y() <= 4.1) {

            g.setPos(initialPos);
            g.setMort(false);
            g.setTemps(deltaTNanoSeconds);
            return;
        } else {
            g.setPos(outil.nextPos(Blinky.nextDirection(g, new RealCoordinates(7.0, 4.0)), g, config));
            return;
        }
    }
}
