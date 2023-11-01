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
        double distanceToPacMan = outil.distance(clydePos, pacManPos);
        if (distanceToPacMan < 5) {
            double[] tabDistance = new double[4];
            int i = 0;
            for (Direction dir : tab) {
                RealCoordinates newPos = clydePos
                        .plus(outil.DirectionToRealCoordinates(dir).times(CLYDE.getSpeed()));

                double newDistance = outil.distance(newPos, pacManPos);

                tabDistance[i] = newDistance;
            }
            return outil.estpossible(outil.tri(tab, tabDistance), CLYDE, config);
        } else {
            return outil.estpossible(trialeatoire(tab), CLYDE, config);
        }
    }

    public static Direction[] trialeatoire(Direction[] directions) {
        Random rand = new Random();

        for (int i = directions.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);

            // Échange les directions aux positions i et index
            Direction temp = directions[i];
            directions[i] = directions[index];
            directions[index] = temp;
        }

        return directions;
    }

}
