package model;

import config.MazeConfig;
import geometry.RealCoordinates;

public class Pinky {

    public static Direction nexDirectionInky(Ghost PINKY, Critter PacMan, MazeConfig config) {
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
                    .plus(DirectionToRealCoordinates(dir).times(PINKY.getSpeed()))
                    .warp(config.getWidth(), config.getHeight());

            double distance = distance(newPos, PacManPos);
            tabDistance[i] = distance;
            i += 1;
        }
        return tri(possibleDirections, tabDistance)[0];
    }

    public static Direction mDirection(Direction[] tab, MazeConfig config, Ghost PINKY) {
        for (Direction dir : tab) {
            RealCoordinates newPos = PINKY.getPos()
                    .plus(DirectionToRealCoordinates(dir).times(PINKY.getSpeed()))
                    .warp(config.getWidth(), config.getHeight());

        }
        return Direction.NONE;
    }

    public static Direction[] tri(Direction[] directions, double[] distances) {
        int n = distances.length;
        Direction[] tab = new Direction[n];
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

    public static double distance(RealCoordinates point1, RealCoordinates point2) {
        double deltaX = point1.x() - point2.x();
        double deltaY = point1.y() - point2.y();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    // Méthode utilitaire pour convertir une direction en coordonnées
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
