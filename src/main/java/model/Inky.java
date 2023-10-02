package model;

import geometry.RealCoordinates;
import java.util.List;

public class Inky implements Critter {

    private RealCoordinates pos;
    private Direction direction;
    private double speed;
    private MazeState mazeState;

    public Inky(RealCoordinates initialPos, double initialSpeed, MazeState mazeState) {
        //Cette methode initialise Inky
        pos = initialPos;
        direction = Direction.NONE;
        speed = initialSpeed;
        this.mazeState = mazeState;
    }

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;
    }

    @Override
    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public RealCoordinates nextPos(long deltaTNanoSeconds) {
        //Cette methode permet de donner la prochaine position de Inky
        RealCoordinates PacManPos = PacMan.INSTANCE.getPos();
        Direction newDirection = bestDirection(PacManPos);
        setDirection(newDirection);
        RealCoordinates nextPos = getPos().plus(DirectionToRealCoordinates(newDirection)).times(getSpeed() * deltaTNanoSeconds * 1E-9));
        return nextPos;
    }

    private Direction bestDirection(RealCoordinates pacManPos) {
        //Cette méthode permet de donne une direction d'une manière intelligente (méthode du backtracking)
        // Elle comparera tous les chemins possible pour que Inky accede le plus rapidement à l'endroit ou ce trouve le pacman
        RealCoordinates inkyPos = getPos();
        List<Direction> possibleDirections = List.of(
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST);
        Direction mDirection = Direction.NONE;
        double minDistance = Double.MAX_VALUE;

        for (Direction dir : possibleDirections) {
            RealCoordinates newPos = inkyPos.plus(DirectionToRealCoordinates(mDirection).times(getSpeed() * 1E-9));

            double distance = distance(newPos, pacManPos);

            if (distance < minDistance) {
                minDistance = distance;
                mDirection = dir;
            }
        }

        return mDirection;
    }

    public static double distance(RealCoordinates point1, RealCoordinates point2) {
        //Cette méthode permet de donner la distance entre deux coordonnées
        double deltaX = point1.x() - point2.x();
        double deltaY = point1.y() - point2.y();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    

    private RealCoordinates DirectionToRealCoordinates(Direction dir) {
        //Cette méthode permet de transformer une direction en coordonnées
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
