package geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class TriangleSymmetries implements Symmetries<EqTriangle>{

    //Find centroid
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

    //Move points by certain amount
    private List<Point> movePoints(List<Point> vertices, double[] moveAmount){
       List<Point> newPoints = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            newPoints.add(new Point((vertices.get(i).getX() + moveAmount[0]), (vertices.get(i).getY() + moveAmount[1])));
        }

        return newPoints;
    }

    //Moves both shapes to the same center. If the points are not shared, they are not symmetrical.
    @Override
    public boolean areSymmetric(EqTriangle s1, EqTriangle s2) {

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

                if(s1_x.equals(s2_x) && s1_y.equals(s2_y)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Find all symmetries of triangle
    @Override
    public Collection<EqTriangle> symmetriesOf(EqTriangle eqTriangle) {
        ArrayList<EqTriangle> symmetries = new ArrayList<>();

        //Rotations
        for(int i = 0; i < 360; i+=1){
            try{
                Point[] points = eqTriangle.rotateBy(i).vertices().toArray(new Point[0]);
                EqTriangle rotation = new EqTriangle(points);
                if(areSymmetric(eqTriangle, rotation))
                    symmetries.add(rotation);
            }catch(IllegalArgumentException iae){
            }
        }



        //Reflections about the perpendicular bisectors
        for(int j,i = 0; i < 3; i++){
            List<Point> vertices = eqTriangle.vertices();
            Point vertex_one = vertices.get(i);
            if(i == 2)
                j = 0;
            else
                j = i+1;
            Point vertex_two = vertices.get(j);
            vertices.set(i, vertex_two);
            vertices.set(j, vertex_one);
            try{
                EqTriangle reflection = new EqTriangle(vertices.toArray(new Point[0]));
                if(areSymmetric(eqTriangle, reflection))
                    symmetries.add(reflection);
            }catch(IllegalArgumentException iae){}
        }

        return symmetries;
    }
}
