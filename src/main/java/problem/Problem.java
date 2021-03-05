package problem;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Problem {

    public static final double dSize = 20;

    public static final String PROBLEM_TEXT = "ПОСТАНОВКА ЗАДАЧИ:\n" +
            "Задано множество окружностей и множество прямоугольников.\n" +
            "Найти такую пару прямоугольник-окружность, что фигура, \n" +
            "находящаяся внутри прямоугольника и окружности, \n" +
            "имеет максимальную площадь. Выделить найденные \n" +
            "прямоугольник и окружность, контур фигуры и \n" +
            "внутреннее пространство фигуры.\n";


    public static final String PROBLEM_CAPTION = "ИТОГОВЫЙ ПРОЕКТ УЧЕНИКА 10-7 БОРИСЕНКО НИКОЛАЯ";

    private static final String FILE_NAME = "points.txt";

    //список заданных прямоугольников
    public ArrayList<Quad> QuadsList = new ArrayList<Quad>();
    //список заданных окружностей
    public ArrayList<Circle> CriclesList = new ArrayList<Circle>();


    //список всех точек на заданной плоскости (сетка)
    public static ArrayList<Point> GRID = new ArrayList<Point>();

    //i - сверху вниз
    //q - слева направо

    public static void fill_GRID() {
        for (double i = dSize; i >= -dSize; i--) {

            for (double q = -dSize; q <= dSize; q++) {
                GRID.add(new Point(q, i));
            }

        }
    }

    //Решить задачу
    public void solve() {
        fill_GRID();

    }

    //Загрузить задачу из файла
    public void loadFromFile() {

    }


    //Сохранить задачу в файл
    public void saveToFile() {

    }

    //Очистить задачу
    public void clear() {
    }

    public void render(GL2 gl) {

        //данные для дебага
        Point p = new Point(3.1, 4);
        Point p1 = new Point(6, 3);

        Color white = new Color(255, 255, 255);
        //нарисовать координатные оси
        Point LEFTX = new Point(-20, 0),
                RIGHTX = new Point(20, 0),
                UPY = new Point(0, 20),
                DOWNY = new Point(0, -20);
        Figures.renderLine(gl, LEFTX, RIGHTX, 2, new Color(255, 255, 255));
        Figures.renderLine(gl, UPY, DOWNY, 2, new Color(255, 255, 255));
        Figures.renderTriangle(gl, new Point(19, 1), new Point(20, 0), new Point(19, -1), new Color(255, 255, 255), true);
        Figures.renderTriangle(gl, new Point(1, 19), new Point(0, 20), new Point(-1, 19), new Color(255, 255, 255), true);
        //нарисовать координатные оси

        fill_GRID();
        for (int i = 0; i < 4*dSize*dSize + 4*dSize + 1; i++) {
            GRID.get(i).render(gl);
        }

        Quad q = new Quad(new Point(3, 4), new Point(5, -2), new Point(9, 7));
        q.render(gl);

        Circle c = new Circle(new Point(5, 3), new Point(6, 4));
        c.render(gl);

        p1.render(gl);
        p.render(gl);

        c.fill_listInside();
        int size = c.pointsInside.size();
        System.out.println(size);
    }
}
