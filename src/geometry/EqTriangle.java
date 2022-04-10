package geometry;

import java.util.Arrays;
import java.util.List;

/**
 * The class implementing equilateral triangles, i.e., triangles in which all three sides have the same length.
 * Note: you can add more methods if you want, but additional methods must be <code>private</code> or <code>protected</code>.
 *
 * @author {Christopher Kim}
 */
public class EqTriangle implements Shape {
    private Point[] vertices = new Point[3];
    /**
     * The constructor accepts an array of <code>Point</code>s to form the vertices of the equilateral triangle. If more
     * than three points are provided, only the first three are considered by this constructor. If less than three
     * points are provided, or if the points do not form a valid equilateral triangle, the constructor throws
     * <code>java.lang.IllegalArgumentException</code>.
     *
     * @param vertices the array of vertices (i.e., <code>Point</code> instances) provided to the constructor.
     */
    public EqTriangle(Point... vertices) {

        for(int i = 0; i < 3; i++) {
            System.out.println(vertices[i]);
        }
        if(vertices.length < 3)
            throw new IllegalArgumentException();
        if(!isMember(Arrays.asList(vertices)))
            throw new IllegalArgumentException();
        for(int i = 0; i < 3; i++){
            double x = vertices[i].getX();
            double y = vertices[i].getY();
            System.out.println(vertices[i]);
            if(x % 1 != 0)
                x = (double)Math.round(x * 1000d) / 1000d;
            if(y % 1 != 0)
                y = (double)Math.round(y * 1000d) / 1000d;
            this.vertices[i] = new Point(x,y);
            //this.vertices[i] = vertices[i];
        }
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
        double[] distances = new double[3];
        for(int j,i = 0; i < 3; i++){
            Point vertex_one = vertices.get(i);
            if(i == 2)
                j = 0;
            else
                j = i+1;
            Point vertex_two = vertices.get(j);
            distances[i] = Math.sqrt(Math.pow((vertex_two.getY() - vertex_one.getY()),2d) + (Math.pow((vertex_two.getX() - vertex_one.getX()),2d)));
        }
        for(int i = 0; i < 2; i++){
            System.out.println(distances[i] + " " + distances[i+1]);
            if(Double.compare(distances[i],distances[i+1]) != 0)
                return false;
        }
        return true;
    }
    
    @Override
    public int numberOfSides() {
        return 3;
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

        if(center[0] != 0 || center[1] != 0)
            return false;
        else
            return true;
    }

    private Point[] centerPoints(List<Point> vertices){
        Point[] newPoints = new Point[3];

        double[] center = findCenter(vertices);
        for(int i = 0; i < 3; i++){
            newPoints[i] = new Point((vertices.get(i).getX() - center[0]), (vertices.get(i).getY() - center[1]));
        }

        return newPoints;
    }

    @Override
    public List<Point> vertices() {
        return Arrays.asList(this.vertices);
    }
    
    @Override
    public EqTriangle rotateBy(int degrees) {
        Point[] points = vertices;

        double radians = degrees * (Math.PI/180.0);
        if(!isCentered(Arrays.asList(this.vertices)))
            points = centerPoints(Arrays.asList(points));
        for(int i = 0; i < 3; i++){
            double x = this.vertices[i].getX();
            double y = this.vertices[i].getY();
            double rotated_x = (x*Math.cos(radians)) - (y*Math.sin(radians));
            double rotated_y = (x*Math.sin(radians)) - (y*Math.cos(radians));
            points[i] = new Point(rotated_x,rotated_y);
        }

        return new EqTriangle(points);
    }

    @Override
    public String toString(){
        String string = new String();
        for(Point p: vertices){
            string += "(" + p.getX() + ", " + p.getY() + "); ";
        }
        return string;
    }
}
