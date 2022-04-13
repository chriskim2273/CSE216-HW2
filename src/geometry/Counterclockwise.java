package geometry;

import java.util.Comparator;

public class Counterclockwise implements Comparator<Point> {
    private double crossProduct(double x1, double y1, double x2, double y2){
        return x1 * y2 - x2 * y1;
    }
    private Point initialPoint;
    public Counterclockwise(Point initial){
        initialPoint = initial;
    }
    @Override
    public int compare(Point o1, Point o2) {
        double cross = crossProduct(o1.getX() - initialPoint.getX(), o1.getY() - initialPoint.getY(), o2.getX() - initialPoint.getX(), o2.getY() - initialPoint.getY());
        if(cross > 0)
            return 1;
        else
            return -1;
    }

}
