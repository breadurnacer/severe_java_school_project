package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.Objects;

/**
 * Класс точки
 */
public class Point implements Comparable<Line> {
    //x - координата точки
    double x;
    //y - координата точки
    double y;

    /**
     * Конструктор точки
     *
     * @param x координата х
     * @param y координата y
     */

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point point) {
        double d;
        d = Math.sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
        return d;
    }

    //Рисование точки через класс Figures
    void render(GL2 gl, double W, Color color) {
        Figures.renderPoint(gl, this, W, color);
    }

    //облегчённый метод рисования
    void render(GL2 gl) {
        render(gl, 2, new Color(255, 255, 255));
    }

    //Получить строковое представление точки
    @Override
    public String toString() {
        return "Точка с координатами: {" + x + "," + y + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    //1 - точка выше прямой
    //0 - точка на прямой, принадлежит
    //-1 - точка ниже прямой
    @Override
    public int compareTo(Line line) {
        if (y > line.k * x + line.b) return 1;
        if (y == line.k * x + line.b) return 0;
        if (y < line.k * x + line.b) return -1;
        return 0;
    }
}
