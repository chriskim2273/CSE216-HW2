package geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
    private double[] center = new double[2];
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

        if(!isMember(Arrays.asList(vertices)))
            throw new IllegalArgumentException();

        for(int i = 0; i < 4; i++){
            this.vertices[i] = vertices[i];
        }

        this.center = findCenter(Arrays.asList(this.vertices));
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
        BigDecimal[] distances = new BigDecimal[4];

        Collections.sort(vertices, new Counterclockwise());

        for(int j,i = 0; i < 4; i++){
            Point vertex_one = vertices.get(i);
            if(i == 3)
                j = 0;
            else
                j = i+1;
            Point vertex_two = vertices.get(j);
            distances[i] = BigDecimal.valueOf(Math.sqrt(Math.pow((vertex_two.getY() - vertex_one.getY()), 2d) + (Math.pow((vertex_two.getX() - vertex_one.getX()), 2d))));
        }
        for(int j,i = 0; i < 4; i++){
            if(i == 3)
                j = 0;
            else
                j = i+1;
            distances[i] = distances[i].setScale(3, RoundingMode.HALF_UP);
            distances[j] = distances[j].setScale(3, RoundingMode.HALF_UP);
            System.out.println(distances[i] + " " + distances[j]);
            if(distances[i] == distances[j])
                return false;
        }
        return true;
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

    private double[] findCenter(List<Point> vertices){
        double[] center = new double[2];
        for(int i = 0; i < 4; i++){
            center[0] += vertices.get(i).getX();
            center[1] += vertices.get(i).getY();
        }

        center[0] /= 4;
        center[1] /= 4;

        return center;
    }

    private boolean isCentered(List<Point> vertices){
        double[] center = findCenter(vertices);

        if(center[0] != 0 || center[1] != 0)
            return false;
        else
            return true;
    }

    private Point[] centerPoints(List<Point> vertices, boolean moveToOrigin){
        Point[] newPoints = new Point[4];

        int negation = 1;
        if(!moveToOrigin)
            negation = -1;

        //double[] center = findCenter(vertices);
        for(int i = 0; i < 4; i++){
            newPoints[i] = new Point((vertices.get(i).getX() - (center[0]*negation)), (vertices.get(i).getY() - (center[1]*negation)));
        }

        return newPoints;
    }

    @Override
    public Square rotateBy(int degrees) {
        Point[] points = vertices.clone();

        double radians = Math.toRadians(degrees);
        if(!isCentered(Arrays.asList(this.vertices))) {
            points = centerPoints(Arrays.asList(points), true);
            System.out.println("Centered");
        }
        for(int i = 0; i < 4; i++){
            double x = points[i].getX();
            double y = points[i].getY();
            double rotated_x = (x*Math.cos(radians)) - (y*Math.sin(radians));
            double rotated_y = (x*Math.sin(radians)) + (y*Math.cos(radians));
            points[i] = new Point(rotated_x,rotated_y);
        }

        //Return the shape back
        points = centerPoints(Arrays.asList(points), false);

        return new Square(points);
    }
    
    @Override
    public String toString() {
        String string = new String();
        String printable_x = new String();
        String printable_y = new String();
        for(Point p: vertices()){
            printable_x = BigDecimal.valueOf(p.getX()).setScale(3, RoundingMode.HALF_UP).toPlainString();
            printable_y = BigDecimal.valueOf(p.getY()).setScale(3, RoundingMode.HALF_UP).toPlainString();
            string += "(" + printable_x + ", " + printable_y + "), ";
        }
        return string;
    }
}
