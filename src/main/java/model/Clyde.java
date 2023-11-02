package model;

import java.util.Random;

import geometry.RealCoordinates;

public class Clyde {

    public static Direction[] nextDirection(Ghost clyde, Critter pacMan) {
        RealCoordinates clydePos = clyde.getPos();
        RealCoordinates pacManPos = pacMan.getPos();
        Direction[] tab = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST
        };
        double distanceToPacMan = outil.distance(clydePos, pacManPos);

        if (distanceToPacMan < 5) {
            double[] tabDistance = new double[4];
            int i = 0;

            for (Direction dir : tab) {
                RealCoordinates newPos = clydePos.plus(outil.DirectionToRealCoordinates(dir).times(clyde.getSpeed()));
                double newDistance = outil.distance(newPos, pacManPos);
                tabDistance[i] = newDistance;
                i++;
            }

            return outil.tri(tab, tabDistance);
        } else {
            return triAleatoire(tab);
        }
    }

    public static Direction[] triAleatoire(Direction[] directions) {
        Random rand = new Random();

        for (int i = directions.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            Direction temp = directions[i];
            directions[i] = directions[index];
            directions[index] = temp;
        }

        return directions;
    }

}
