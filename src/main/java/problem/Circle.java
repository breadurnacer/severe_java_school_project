package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;

import static problem.Problem.GRID;
import static problem.Problem.dSize;

public class Circle {
    //точка центра
    Point p0;

    //радиус
    double R;

    //список точек внутри
    ArrayList<Point> pointsInside = new ArrayList<Point>();

    Circle(Point p0, Point pIn) {
        this.p0 = p0;
        this.R = p0.distanceTo(pIn);

    }

    public boolean isInside(Point p){
        double x = p.x, y = p.y;
        double x0 = p0.x, y0=p0.y;
        if((x-x0)*(x-x0) + (y-y0)*(y-y0)<=R*R) return true;
        else return false;
    }

    public void fill_listInside(){
        for (Point pg : GRID) {
            if(isInside(pg)){
                pointsInside.add(pg);
            }
        }
    }

    //метод пересечения списков точек через retainAll
    public int IntersectionArea(Quad quad){
        int result;
        ArrayList<Point> points = this.pointsInside;
        points.retainAll(quad.pointsInside);
        result = points.size();
        return result;
    }

    //Рисование точки через класс Figures
    void render(GL2 gl, Color color, boolean filled) {
        Figures.renderCircle(gl,p0,R,color,filled);
    }

    //облегчённый метод рисования
    void render(GL2 gl){render(gl,new Color(255,255,255), false);}
}



