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
    private Point[] vertices;
    private double[] center;
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

        List<Point> verticesList = Arrays.asList(vertices);

        if(!isMember(verticesList))
            throw new IllegalArgumentException();

        this.vertices = vertices;

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

        /*
        List<Point> counterClockwiseList = new ArrayList<>(vertices);
        Collections.sort(counterClockwiseList, new Counterclockwise(vertices.get(0)));
        Collections.reverse(counterClockwiseList);

        for(int i = 0; i < counterClockwiseList.size(); i++){
            if(!counterClockwiseList.get(i).equals(vertices.get(i)))
                return false;
        }
        */

        BigDecimal[] distances = new BigDecimal[4];

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
            //System.out.println(distances[i] + " " + distances[j]);
            if(!distances[i].equals(distances[j]))
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
        Collections.sort(vertexList, new Counterclockwise(vertexList.get(0)));
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

        return !(center[0] != 0 || center[1] != 0);
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

    private List<Point> sortCounterClockwise(List<Point> vertices){
        List<Point> verticesList = new ArrayList<>(vertices);

        Point first = verticesList.get(0);
        int index = 0;
        for(int i = 0; i < verticesList.size(); i++){
            Point current = verticesList.get(i);
            if(current.getX() < first.getX()) {
                first = current;
                index = i;
            }
            else if(current.getX() == first.getX())
                if(current.getY() < first.getY()) {
                    first = current;
                    index = i;
                }
        }

        Point temp = verticesList.get(0);
        verticesList.set(0, first);
        verticesList.set(index, temp);
        Collections.sort(verticesList, new Counterclockwise(first));
        Collections.reverse(verticesList);
        return verticesList;
    }
    
    @Override
    public String toString() {
        String string = "Square: ";
        String printable_x;
        String printable_y;


        List<Point> verticesList = sortCounterClockwise(vertices());


        for(Point p: verticesList){
            printable_x = BigDecimal.valueOf(p.getX()).setScale(3, RoundingMode.HALF_UP).toPlainString();
            printable_y = BigDecimal.valueOf(p.getY()).setScale(3, RoundingMode.HALF_UP).toPlainString();
            string += "(" + printable_x + ", " + printable_y + "), ";
        }
        return string.substring(0, string.length()-2);
    }
}
