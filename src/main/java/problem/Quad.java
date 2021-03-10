package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;

import static problem.Problem.GRID;
import static problem.Problem.dSize;

public class Quad {
    //точки вершин
    Point p1,p2,p3,p4;

    //длины сторон
    double a,b;

    //прямые, задающие прямоугольник
    Line line1, line2, line3, line4;

    //список точек внутри
    ArrayList<Point> pointsInside = new ArrayList<Point>();


    Quad(Point p1, Point p2, Point pA) {
        this.p1 = p1;
        this.p2 = p2;
        double d1,d2;
        d1 = pA.distanceTo(p1);
        d2 = pA.distanceTo(p2);
        a = Math.sqrt(d1*d1+d2*d2);

        Line line = new Line(p1,p2);
        Line line_parallel = line.parallelLine(pA);
        Line line_perped1 = line.perpendicularLine(p1);
        Line line_perped2 = line.perpendicularLine(p2);

        p3 = line_parallel.intersection(line_perped2);
        p4 = line_parallel.intersection(line_perped1);

        line1 = new Line(p1,p2);
        line2 = new Line(p2,p3);
        line3 = new Line(p3,p4);
        line4 = new Line(p4,p1);
    }

    public boolean isInside(Point p){
        Line lineUp1, lineDown1, lineUp2, lineDown2;
        double x = p.x, y = p.y;
        if(line3.b>line1.b){
            lineUp1 = line3;
            lineDown1 = line1;
        }else{
            lineUp1 = line1;
            lineDown1 = line3;
        }

        if(line4.b>line2.b){
            lineUp2 = line4;
            lineDown2 = line2;
        }else{
            lineUp2 = line2;
            lineDown2 = line4;
        }

        return p.compareTo(lineDown1) >= 0 && p.compareTo(lineDown2) >= 0 && p.compareTo(lineUp1) <= 0 && p.compareTo(lineUp2) <= 0;
    }

    public void fill_listInside(){
        for (int i = 0; i < 4*dSize*dSize + 4*dSize + 1; i++) {
            if(isInside(GRID.get(i))){
                pointsInside.add(GRID.get(i));
            }
        }
    }

    //Рисование точки через класс Figures
    void render(GL2 gl, Color color, boolean filled) {
        Figures.renderQuad(gl,p1,p2,p3,p4,color,filled);
    }

    //облегчённый метод рисования
    void render(GL2 gl){render(gl,new Color(255,255,255), false);}



}
