package geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An unmodifiable point in the standard two-dimensional Euclidean space. The coordinates of such a point is given by
 * exactly two doubles specifying its <code>x</code> and <code>y</code> values.
 *
 * @author Ritwik Banerjee
 */
public class Point {
    
    private final double x, y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }

    @Override
    public String toString(){
        String printable_x = BigDecimal.valueOf(x).setScale(3, RoundingMode.HALF_UP).toPlainString();
        String printable_y = BigDecimal.valueOf(y).setScale(3, RoundingMode.HALF_UP).toPlainString();
        return ("("+printable_x+", "+printable_y+")");
    }
}