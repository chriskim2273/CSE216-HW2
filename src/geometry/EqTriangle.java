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
        if(vertices.length < 3)
            throw new IllegalArgumentException();
        if(!isMember(Arrays.asList(vertices)))
            throw new IllegalArgumentException();
        for(int i = 0; i < 3; i++){
            this.vertices[i] = vertices[i];
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
        for(int i = 0; i < 3; i++){
            Point vertex_one = vertices.get(i);
            if(i == 2)
                i = -1;
            Point vertex_two = vertices.get(i+1);
            distances[i] = Math.sqrt(Math.pow((vertex_two.getY() - vertex_one.getY()),2) + Math.pow((vertex_two.getX() - vertex_one.getX()),2));
        }
        for(int i = 0; i < 2; i++){
            if(distances[i] != distances[i+1])
                return false;
        }
        return true;
    }
    
    @Override
    public int numberOfSides() {
        return 3;
    }

    private boolean isCentered(List<Point> vertices){
        double center_x = 0;
        double center_y = 0;
        for(int i = 0; i < 3; i++){
            center_x += vertices.get(i).getX();
            center_y += vertices.get(i).getY();
        }

        center_x /= 3;
        center_y /= 3;

        if(center_x != 0 || center_y != 0)
            return false;
        else
            return true;
    }

    @Override
    public List<Point> vertices() {
        return Arrays.asList(vertices);
    }
    
    @Override
    public EqTriangle rotateBy(int degrees) {
        double radians = degrees * (Math.PI/180.0);
        if(isCentered(Arrays.asList(vertices))){
            for(int i = 0; i < 3; i++){
                double x = vertices[i].getX();
                double y = vertices[i].getY();
                double rotated_x = (x*Math.cos(radians)) - (y*Math.sin(radians));
                double rotated_y = (x*Math.sin(radians)) - (y*Math.cos(radians));
                vertices[i] = new Point(rotated_x,rotated_y);
            }
        }
        else{
            // TODO
        }
    }
}
