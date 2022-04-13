package geometry;

import java.util.Comparator;
import java.util.List;

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
/*
        double cross_product = crossProduct(o1.getX(),o1.getY(),o2.getX(),o2.getY());
        if(cross_product > 0)
            return 1;
        return -1;
 */
    }

}
