package geometry;

import java.util.Comparator;

public class Counterclockwise implements Comparator<Point> {
    @Override
    public int compare(Point o1, Point o2) {
        double angle_one = -1 * Math.atan2(o1.getX(), -1 * o1.getY());
        double angle_two = -1 * Math.atan2(o1.getX(), -1 * o1.getY());
        return (int) (angle_two - angle_one);
    }
}
