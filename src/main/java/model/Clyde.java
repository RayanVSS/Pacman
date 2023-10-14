package model;

import java.util.Random;

import config.MazeConfig;
import geometry.RealCoordinates;

public class Clyde {

    public static Direction nextDirection(MazeConfig config, Ghost CLYDE, Critter PacMan) {
        RealCoordinates clydePos = CLYDE.getPos();
        RealCoordinates pacManPos = PacMan.getPos();
        Direction[] tab = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST };
        double distanceToPacMan = outilghost.distance(clydePos, pacManPos);
        if (distanceToPacMan < 5) {
            Direction bestDirection = null;
            double minDistance = Double.MAX_VALUE;
            for (Direction dir : tab) {
                RealCoordinates newPos = clydePos
                        .plus(outilghost.DirectionToRealCoordinates(dir).times(CLYDE.getSpeed()));

                double newDistance = outilghost.distance(newPos, pacManPos);

                if (newDistance < minDistance) {
                    bestDirection = dir;
                    minDistance = newDistance;
                }
            }
            return bestDirection;
        } else {
            Random rand = new Random();
            Direction randomDirection = tab[rand.nextInt(tab.length)];
            return randomDirection;
        }
    }

}
