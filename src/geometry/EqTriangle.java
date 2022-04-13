package geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * The class implementing equilateral triangles, i.e., triangles in which all three sides have the same length.
 * Note: you can add more methods if you want, but additional methods must be <code>private</code> or <code>protected</code>.
 *
 * @author {Christopher Kim}
 */
public class EqTriangle implements Shape {
    private Point[] vertices;

    private double[] center;
    /**
     * The constructor accepts an array of <code>Point</code>s to form the vertices of the equilateral triangle. If more
     * than three points are provided, only the first three are considered by this constructor. If less than three
     * points are provided, or if the points do not form a valid equilateral triangle, the constructor throws
     * <code>java.lang.IllegalArgumentException</code>.
     *
     * @param vertices the array of vertices (i.e., <code>Point</code> instances) provided to the constructor.
     */
    public EqTriangle(Point... vertices) {
/*
        for(int i = 0; i < 3; i++) {
            System.out.println(vertices[i]);
        }

 */
        if(vertices.length < 3)
            throw new IllegalArgumentException();
        if(!isMember(Arrays.asList(vertices)))
            throw new IllegalArgumentException();

        this.vertices = vertices;
        // Sets the center
        this.center = findCenter(Arrays.asList(this.vertices));
    }
    
    /**
     * Checks if the series of <code>Point</code> instances form a valid equilateral triangle if first three form the
     * vertices of the triangle.
     *
     * @param vertices the list of specified vertices.
     * @return <code>true</code> if the first three vertices in the argument form a valid equilateral triangle, and
     * <code>false</code> otherwise.
     */
    @Override
    public boolean isMember(List<Point> vertices) {
        BigDecimal[] distances = new BigDecimal[3];
        for(int j,i = 0; i < 3; i++){
            Point vertex_one = vertices.get(i);
            if(i == 2)
                j = 0;
            else
                j = i+1;
            Point vertex_two = vertices.get(j);
            distances[i] = BigDecimal.valueOf(Math.sqrt(Math.pow((vertex_two.getY() - vertex_one.getY()), 2d) + (Math.pow((vertex_two.getX() - vertex_one.getX()), 2d))));
        }
        for(int j,i = 0; i < 3; i++){
            if(i == 2)
                j = 0;
            else
                j = i+1;
            distances[i] = distances[i].setScale(3, RoundingMode.HALF_UP);
            distances[j] = distances[j].setScale(3, RoundingMode.HALF_UP);

            //System.out.println(distances[i] + " " + distances[j]);
            if(!distances[i].equals(distances[j]))
                return false;
        }
        return true;
    }
    
    @Override
    public int numberOfSides() {
        return 3;
    }

    @Override
    public List<Point> vertices() {
        return Arrays.asList(this.vertices);
    }

    private double[] findCenter(List<Point> vertices){
        double[] center = new double[2];
        for(int i = 0; i < 3; i++){
            center[0] += vertices.get(i).getX();
            center[1] += vertices.get(i).getY();
        }

        center[0] /= 3;
        center[1] /= 3;

        return center;
    }

    private boolean isCentered(List<Point> vertices){
        double[] center = findCenter(vertices);

        return !(center[0] != 0 || center[1] != 0);
    }

    private Point[] centerPoints(List<Point> vertices, boolean moveToOrigin){
        Point[] newPoints = new Point[3];

        int negation = 1;
        if(!moveToOrigin)
            negation = -1;

        for(int i = 0; i < 3; i++){
            newPoints[i] = new Point((vertices.get(i).getX() - (center[0]*negation)), (vertices.get(i).getY() - (center[1]*negation)));
        }

        return newPoints;
    }
    
    @Override
    public EqTriangle rotateBy(int degrees) {
        Point[] points = vertices.clone();

        for(int i = 0; i < points.length; i++){
            points[i] = new Point(points[i].getX(),points[i].getY());
        }

        double radians = Math.toRadians(degrees);
        if(!isCentered(Arrays.asList(this.vertices))) {
            points = centerPoints(Arrays.asList(points), true);
        }
        for(int i = 0; i < 3; i++){
            double x = points[i].getX();
            double y = points[i].getY();
            double rotated_x = (x*Math.cos(radians)) - (y*Math.sin(radians));
            double rotated_y = (x*Math.sin(radians)) + (y*Math.cos(radians));
            points[i] = new Point(rotated_x,rotated_y);
        }

        points = centerPoints(Arrays.asList(points), false);

        return new EqTriangle(points);
    }

    @Override
    public String toString(){
        String string = "EqTriangle: ";
        BigDecimal x;
        BigDecimal y;

        for(Point p: vertices()) {
            String printable_x;
            String printable_y;
            x = BigDecimal.valueOf(p.getX()).setScale(3, RoundingMode.HALF_UP);
            y = BigDecimal.valueOf(p.getY()).setScale(3, RoundingMode.HALF_UP);
            if (x.doubleValue() % 1 == 0)
                printable_x = String.valueOf(x.intValue());
            else
                printable_x = x.toString();
            if (y.doubleValue() % 1 == 0)
                printable_y = String.valueOf(y.intValue());
            else
                printable_y = y.toString();

            string += "(" + printable_x + ", " + printable_y + "), ";
        }
        return string.substring(0, string.length()-2);
    }
}
