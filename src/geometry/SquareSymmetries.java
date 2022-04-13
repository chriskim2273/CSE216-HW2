package geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SquareSymmetries implements Symmetries<Square>{

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

    private List<Point> movePoints(List<Point> vertices, double[] moveAmount){
        List<Point> newPoints = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            newPoints.add(new Point((vertices.get(i).getX() + moveAmount[0]), (vertices.get(i).getY() + moveAmount[1])));
        }

        return newPoints;
    }

    @Override
    public boolean areSymmetric(Square s1, Square s2) {
        //Move one point to match other triangle,
        //if other points don't match, they are not symmetrical.
        List<Point> s1_vertices = s1.vertices();
        List<Point> s2_vertices = s2.vertices();

        double[] s1_center = findCenter(s1_vertices);
        double[] s2_center = findCenter(s2_vertices);
        double[] moveAmount = {s2_center[0]-s1_center[0], s2_center[1]-s1_center[1]};

        s1_vertices = movePoints(s1_vertices, moveAmount);

        for(int i = 0; i < s1_vertices.size(); i++){
            for(int j = 0; j < s2_vertices.size(); j++){
                BigDecimal s1_x = BigDecimal.valueOf(s1_vertices.get(i).getX()).setScale(3,RoundingMode.HALF_UP);
                BigDecimal s2_x = BigDecimal.valueOf(s2_vertices.get(j).getX()).setScale(3,RoundingMode.HALF_UP);
                BigDecimal s1_y = BigDecimal.valueOf(s1_vertices.get(i).getY()).setScale(3,RoundingMode.HALF_UP);
                BigDecimal s2_y = BigDecimal.valueOf(s2_vertices.get(j).getY()).setScale(3,RoundingMode.HALF_UP);
/*
                System.out.println(s1_x + " - " + s2_x);
                System.out.println(s1_y + " - " + s2_y);
                System.out.println("\n");

 */
                if(s1_x.equals(s2_x) && s1_y.equals(s2_y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Square swapPoints(Square square, int one, int two){
        List<Point> vertices = square.vertices();
        Point vertex_one = vertices.get(one);
        Point vertex_two = vertices.get(two);
        vertices.set(one, vertex_two);
        vertices.set(two, vertex_one);
        Square newSquare = square;
        try{
            newSquare = new Square(vertices.toArray(new Point[0]));
        }catch(IllegalArgumentException iae){
        }

        return newSquare;
    }

    @Override
    public Collection<Square> symmetriesOf(Square square) {
        Collection<Square> symmetries = new ArrayList<>();

        //Rotations
        for(int i = 0; i <= 270; i+=1){
            try{
                Point[] points = square.rotateBy(i).vertices().toArray(new Point[0]);
                Square rotation = new Square(points);
                if(areSymmetric(square,rotation))
                    symmetries.add(rotation);
            }catch(IllegalArgumentException iae){}
        }


        /*
        1,2 3,4
        1 jump 1 2 . 3 jump 1 4
        1,4 2,3
        1 jump 3 4 . 2 jump 1 3
        1,3
        1 jump 2
        2,4
        2 jump 2
         */


        //Reflections about the perpendicular bisectors
        for(int i = 0; i < 2; i++) {
            Square testSymmetry = swapPoints(square, i, i+2);
            if(testSymmetry != null)
                if(areSymmetric(square,testSymmetry))
                    symmetries.add(testSymmetry);
        }

        Square testSymmetry = new Square(square.vertices().toArray(new Point[0]));
        for(int i = 0; i < 3; i+=2) {
            testSymmetry = swapPoints(testSymmetry, i, i + 1);
        }
        if(testSymmetry != null)
            if(areSymmetric(square,testSymmetry))
                symmetries.add(testSymmetry);

        testSymmetry = new Square(square.vertices().toArray(new Point[0]));
        for(int j = 3,i = 0; i < 2; i++,j-=2) {
            testSymmetry = swapPoints(testSymmetry, i, i + j);
        }
        if(testSymmetry != null)
            if(areSymmetric(square,testSymmetry))
                symmetries.add(testSymmetry);

        return symmetries;
    }
}
