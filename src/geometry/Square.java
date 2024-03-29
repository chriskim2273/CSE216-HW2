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
 * @author {Christopher Kim}
 */
public class Square implements Shape {
    private Point[] vertices = new Point[4];
    private double[] center;
    /**
     * The constructor accepts an array of <code>Point</code>s to form the vertices of the square. If more than four
     * points are provided, only the first four are considered by this constructor. If less than four points are
     * provided, or if the points do not form a valid square, the constructor throws <code>java.lang.IllegalArgumentException</code>.
     *
     * @param vertices the array of vertices (i.e., <code>Point</code> instances) provided to the constructor.
     */
    public Square(Point... vertices) {
        //Check if there are less than 4 points
        if(vertices.length < 4)
            throw new IllegalArgumentException();

        List<Point> verticesList = Arrays.asList(vertices);

        //Check if the points form a proper square.
        if(!isMember(verticesList))
            throw new IllegalArgumentException();

        //Only consider first 4 points in input.
        for(int i = 0; i < 4; i++)
            this.vertices[i] = vertices[i];

        //Finds centroid of points
        this.center = findCenter(vertices());
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

        This code was meant to test if the given square is in counter-clockwise order, starting with the lowest x and y point.
        However, instructions were unclear and I was unsure if I could sort in isMember and etc etc. The test class would
        also not compile if this is done. Pretty misleading. Piazza gives mixed signals as well.

        List<Point> counterClockwiseList = new ArrayList<>(vertices);
        List<Point> vertexList = new ArrayList<>(vertices);
        Collections.sort(counterClockwiseList, new Counterclockwise(vertices.get(0)));
        Collections.reverse(counterClockwiseList);

        for(int i = 0; i < counterClockwiseList.size(); i++){
            if(!counterClockwiseList.get(i).equals(vertices.get(i)))
                return false;
        }
        */

        // Again, unsure if this is allowed, Piazza has mixed signals. Confusing.
        vertices = sortCounterClockwise(vertices);

        BigDecimal[] distances = new BigDecimal[4];

        //The method to check if it is a square implies that the points are in counter-clockwise or clockwise order.
        //Checks if all the sides are equal and the diagonals are of equal size.
        for(int j,i = 0; i < 4; i++){
            Point vertex_one = vertices.get(i);
            if(i == 3)
                j = 0;
            else
                j = i+1;
            Point vertex_two = vertices.get(j);
            //Calculate distance
            distances[i] = BigDecimal.valueOf(Math.sqrt(Math.pow((vertex_two.getY() - vertex_one.getY()), 2d) + (Math.pow((vertex_two.getX() - vertex_one.getX()), 2d))));
        }
        for(int j,i = 0; i < 4; i++){
            if(i == 3)
                j = 0;
            else
                j = i+1;
            distances[i] = distances[i].setScale(3, RoundingMode.HALF_UP);
            distances[j] = distances[j].setScale(3, RoundingMode.HALF_UP);
            if(!distances[i].equals(distances[j]))
                return false;
        }

        //Check diagonals
        distances = new BigDecimal[2];
        for(int i = 0; i < 2; i++){
            Point vertex_one = vertices.get(i);
            Point vertex_two = vertices.get(i+2);
            //Calculate the distance
            distances[i] = BigDecimal.valueOf(Math.sqrt(Math.pow((vertex_two.getY() - vertex_one.getY()), 2d) + (Math.pow((vertex_two.getX() - vertex_one.getX()), 2d))));
        }
        distances[0] = distances[0].setScale(3, RoundingMode.HALF_UP);
        distances[1] = distances[1].setScale(3, RoundingMode.HALF_UP);
        if(!distances[0].equals(distances[1]))
            return false;

        return true;
    }
    
    @Override
    public int numberOfSides() {
        return 4;
    }
    
    @Override
    public List<Point> vertices() {
        //Simply returns vertices array as List.
        return Arrays.asList(vertices);
    }

    private double[] findCenter(List<Point> vertices){
        //Finds the centroid of the shape.
        double[] center = new double[2];
        for(int i = 0; i < 4; i++){
            center[0] += vertices.get(i).getX();
            center[1] += vertices.get(i).getY();
        }

        center[0] /= 4;
        center[1] /= 4;

        return center;
    }

    //Checks if the points are centered around the origin.
    private boolean isCentered(List<Point> vertices){
        double[] center = findCenter(vertices);

        return !(center[0] != 0 || center[1] != 0);
    }

    //Moves the points to be centered around the origin for proper rotations or moves back to original position depending on moveToOrigin boolean
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

    //Rotates the square
    @Override
    public Square rotateBy(int degrees) {
        Point[] points = vertices.clone();

        for(int i = 0; i < points.length; i++){
            points[i] = new Point(points[i].getX(),points[i].getY());
        }

        double radians = Math.toRadians(degrees);
        //If the points are not centered around the origin, move them to the origin.
        if(!isCentered(Arrays.asList(points))) {
            points = centerPoints(Arrays.asList(points), true);
        }
        //Apply rotation math to all points
        for(int i = 0; i < 4; i++){
            double x = points[i].getX();
            double y = points[i].getY();
            double rotated_x = (x*Math.cos(radians)) - (y*Math.sin(radians));
            double rotated_y = (x*Math.sin(radians)) + (y*Math.cos(radians));
            points[i] = new Point(rotated_x,rotated_y);
        }

        //Return the shape back to original center position.
        points = centerPoints(Arrays.asList(points), false);

        return new Square(points);
    }

    //Sorts the points into counter clockwise order starting with the point with the smallest x and y values.
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
        //The list is sorted clockwise order, so the list is reversed.
        Collections.reverse(verticesList);
        return verticesList;
    }
    
    @Override
    public String toString() {
        String string = "Square: ";
        BigDecimal x;
        BigDecimal y;


        List<Point> verticesList = sortCounterClockwise(vertices());

        for(Point p: verticesList){
            String printable_x;
            String printable_y;
            x = BigDecimal.valueOf(p.getX()).setScale(3, RoundingMode.HALF_UP);
            y = BigDecimal.valueOf(p.getY()).setScale(3, RoundingMode.HALF_UP);

            //Checks if the double value is an integer. This segment is used to remove the decimal and cleanly print Integer values properly.
            if(x.doubleValue() % 1 == 0)
                printable_x = String.valueOf(x.intValue());
            else
                printable_x = x.toString();
            if(y.doubleValue() % 1 == 0)
                printable_y = String.valueOf(y.intValue());
            else
                printable_y = y.toString();

            string += "(" + printable_x + ", " + printable_y + "), ";
        }
        return string.substring(0, string.length()-2);
    }
}
