package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;

import static problem.Problem.GRID;
import static problem.Problem.dSize;

public class Quad {
    //точки вершин
    Point p1, p2, p3, p4;

    //длины сторон
    double a, b;

    //прямые, задающие прямоугольник
    Line line1, line2, line3, line4;

    //список точек внутри
    ArrayList<Point> pointsInside = new ArrayList<Point>();

    public void renderPointsInside(GL2 gl) {
        for (Point pIn :
                pointsInside) {
            pIn.render(gl, 3, new Color(0, 0, 255));
        }
    }

    Quad(Point p1, Point p2, Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        line1 = new Line(p1, p2);
        line2 = new Line(p2, p3);
        line3 = new Line(p3, p4);
        line4 = new Line(p4, p1);
    }

    Quad(Point p1, Point p2, Point pA) {
        this.p1 = p1;
        this.p2 = p2;
        double d1, d2;
        d1 = pA.distanceTo(p1);
        d2 = pA.distanceTo(p2);
        a = Math.sqrt(d1 * d1 + d2 * d2);

        Line line = new Line(p1, p2);
        Line line_parallel = line.parallelLine(pA);
        Line line_perped1 = line.perpendicularLine(p1);
        Line line_perped2 = line.perpendicularLine(p2);

        p3 = line_parallel.intersection(line_perped2);
        p4 = line_parallel.intersection(line_perped1);

        line1 = new Line(p1, p2);
        line2 = new Line(p2, p3);
        line3 = new Line(p3, p4);
        line4 = new Line(p4, p1);
    }

    public boolean isInside(Point p) {
        Line lineUp1, lineDown1, lineUp2, lineDown2;
        double x = p.x, y = p.y;
        if (line3.b > line1.b) {
            lineUp1 = line3;
            lineDown1 = line1;
        } else {
            lineUp1 = line1;
            lineDown1 = line3;
        }

        if (line4.b > line2.b) {
            lineUp2 = line4;
            lineDown2 = line2;
        } else {
            lineUp2 = line2;
            lineDown2 = line4;
        }

        return p.compareTo(lineDown1) >= 0 && p.compareTo(lineDown2) >= 0 && p.compareTo(lineUp1) <= 0 && p.compareTo(lineUp2) <= 0;
    }

    public void fill_listInside() {
        for (Point pg : GRID) {
            if (isInside(pg)) {
                pointsInside.add(pg);
            }
        }
    }

    //текстовый вывод прямоугольника
    @Override
    public String toString() {
        String s = String.format("(%.1f; %.1f), (%.1f; %.1f), (%.1f; %.1f), (%.1f; %.1f)",
                p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y);
        return s;
    }

    //Рисование точки через класс Figures
    void render(GL2 gl, Color color, boolean filled) {
        Figures.renderQuad(gl, p1, p2, p3, p4, color, filled);
    }

    //облегчённый метод рисования (по умолчанию - белый цвет и не закрашен внутри)
    void render(GL2 gl) {
        render(gl, new Color(255, 255, 255), false);
    }


}
