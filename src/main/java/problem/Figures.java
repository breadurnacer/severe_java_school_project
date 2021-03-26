package problem;

import javax.media.opengl.GL2;

import static javax.media.opengl.GL.*;

public class Figures {

    public static void renderPoint(GL2 gl, Point p, double W, Color color) {
        double
                r = color.R / 255,
                g = color.G / 255,
                b = color.B / 255;
        gl.glColor3d(r, g, b); //делаем цвет

        double x = p.x / Problem.dSize, y = p.y / Problem.dSize; //делаем нормальные координаты


        gl.glPointSize((float) W);
        gl.glBegin(GL_POINTS);
        gl.glVertex2d(x, y);
        gl.glEnd();
    }

    public static void renderLine(GL2 gl, Point p1, Point p2, double W, Color color) {
        double
                r = color.R / 255,
                g = color.G / 255,
                b = color.B / 255;
        gl.glColor3d(r, g, b); //делаем цвет

        double x1 = p1.x / Problem.dSize, y1 = p1.y / Problem.dSize;
        double x2 = p2.x / Problem.dSize, y2 = p2.y / Problem.dSize;
        //делаем нормальные координаты


        gl.glLineWidth((float) W);
        gl.glBegin(GL_LINES);
        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glEnd();
    }

    public static void renderTriangle(GL2 gl, Point p1, Point p2, Point p3, Color color, boolean filled) {
        double
                r = color.R / 255,
                g = color.G / 255,
                b = color.B / 255;
        gl.glColor3d(r, g, b); //делаем цвет

        double x1 = p1.x / Problem.dSize, y1 = p1.y / Problem.dSize;
        double x2 = p2.x / Problem.dSize, y2 = p2.y / Problem.dSize;
        double x3 = p3.x / Problem.dSize, y3 = p3.y / Problem.dSize;
        //делаем нормальные координаты
        if (!filled) {
            gl.glBegin(GL_LINE_STRIP);
            gl.glVertex2d(x1, y1);
            gl.glVertex2d(x2, y2);
            gl.glVertex2d(x3, y3);
            gl.glVertex2d(x1, y1);
            gl.glEnd();
        } else {
            gl.glBegin(GL_TRIANGLES);
            gl.glVertex2d(x1, y1);
            gl.glVertex2d(x2, y2);
            gl.glVertex2d(x3, y3);
            gl.glEnd();
        }
    }

    public static void renderQuad(GL2 gl, Point p1, Point p2, Point p3, Point p4, Color color, boolean filled) {
        double
                r = color.R / 255,
                g = color.G / 255,
                b = color.B / 255;
        gl.glColor3d(r, g, b); //делаем цвет

        double x1 = p1.x / Problem.dSize, y1 = p1.y / Problem.dSize;
        double x2 = p2.x / Problem.dSize, y2 = p2.y / Problem.dSize;
        double x3 = p3.x / Problem.dSize, y3 = p3.y / Problem.dSize;
        double x4 = p4.x / Problem.dSize, y4 = p4.y / Problem.dSize;
        //делаем нормальные координаты
        if (!filled) {
            gl.glBegin(GL_LINE_STRIP);
            gl.glVertex2d(x1, y1);
            gl.glVertex2d(x2, y2);
            gl.glVertex2d(x3, y3);
            gl.glVertex2d(x4, y4);
            gl.glVertex2d(x1, y1);
            gl.glEnd();
        } else {
            gl.glBegin(GL_TRIANGLE_FAN);
            gl.glVertex2d(x1, y1);
            gl.glVertex2d(x2, y2);
            gl.glVertex2d(x3, y3);
            gl.glVertex2d(x4, y4);
            gl.glEnd();
        }
    }

    public static void renderCircle(GL2 gl, Point p, double R, Color color, boolean filled) {
        double
                r = color.R / 255,
                g = color.G / 255,
                b = color.B / 255;
        gl.glColor3d(r, g, b); //делаем цвет

        double radius = R / Problem.dSize; //выровняем радиус

        double x0 = p.x / Problem.dSize, y0 = p.y / Problem.dSize;
        //делаем нормальные координаты

        double delta = 50; //шаг прорисовки окружности
        if (!filled) {
            gl.glBegin(GL_LINE_STRIP);


            for (int i = 0; i <= delta; i++) {
                double alpha = Math.toRadians(i * (360 / delta));

                double x = x0 + radius * Math.cos(alpha);
                double y = y0 + radius * Math.sin(alpha);
                gl.glVertex2d(x, y);
            }
            gl.glEnd();
        } else {
            gl.glBegin(GL_TRIANGLE_FAN);

            gl.glVertex2d(x0, y0);
            for (int i = 0; i <= delta; i++) {
                double alpha = Math.toRadians(i * (360 / delta));

                double x = x0 + radius * Math.cos(alpha);
                double y = y0 + radius * Math.sin(alpha);
                gl.glVertex2d(x, y);
            }
            gl.glEnd();
        }


    }

    public static void renderDecart(GL2 gl) {
        //нарисовать координатные оси
        Point LEFTX = new Point(-Problem.dSize, 0),
                RIGHTX = new Point(Problem.dSize, 0),
                UPY = new Point(0, Problem.dSize),
                DOWNY = new Point(0, -Problem.dSize);
        Figures.renderLine(gl, LEFTX, RIGHTX, 2, new Color(255, 255, 255));
        Figures.renderLine(gl, UPY, DOWNY, 2, new Color(255, 255, 255));
        Figures.renderTriangle(gl, new Point(Problem.dSize - 1, 1), new Point(Problem.dSize, 0), new Point(Problem.dSize - 1, -1), new Color(255, 255, 255), true);
        Figures.renderTriangle(gl, new Point(1, Problem.dSize - 1), new Point(0, Problem.dSize), new Point(-1, Problem.dSize - 1), new Color(255, 255, 255), true);
        //нарисовать координатные оси
    }

}