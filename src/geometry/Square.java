package geometry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The class impementing squares.
 * Note: you can add more methods if you want, but additional methods must be <code>private</code> or <code>protected</code>.
 *
 * @author {add your full name here}
 */
public class Square implements Shape {
    private Point[] vertices = new Point[4];
    /**
     * The constructor accepts an array of <code>Point</code>s to form the vertices of the square. If more than four
     * points are provided, only the first four are considered by this constructor. If less than four points are
     * provided, or if the points do not form a valid square, the constructor throws <code>java.lang.IllegalArgumentException</code>.
     *
     * @param vertices the array of vertices (i.e., <code>Point</code> instances) provided to the constructor.
     */
    public Square(Point... vertices) {
        if(vertices.length < 4)
            throw new IllegalArgumentException();

        //if()
    }
    
    /**
     * Checks if the series of <code>Point</code> instances form a valid square if the first four form the vertices of
     * the square. This method considers the points in a counterclockwise manner starting with the vertex with the least
     * x-value. If multiple vertices have the same x-value, the counterclockwise ordering starts at the vertex with the
     * least y-value amongst them.
     *
     * @param vertices the list of specified vertices.
     * @return <code>true</code> if the first four vertices in the argument form a valid square, and <code>false</code>
     * otherwise.
     */
    @Override
    public boolean isMember(List<Point> vertices) {
        return false; // TODO
    }
    
    @Override
    public int numberOfSides() {
        return 4;
    }
    
    @Override
    public List<Point> vertices() {
        List<Point> vertexList = Arrays.asList(vertices);
        Collections.sort(vertexList, new Counterclockwise());
        return vertexList;
    }
    
    @Override
    public Square rotateBy(int degrees) {
        return null; // TODO
    }
    
    @Override
    public String toString() {
        return null; // TODO
    }
}
