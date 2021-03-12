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
    public ArrayList<Circle> CirclesList = new ArrayList<Circle>();

    //результативные значение и объекты
    double maxArea = 0;
    Circle resultCircle = null;
    Quad resultQuad = null;


    //список всех точек на заданной плоскости (сетка)
    public static ArrayList<Point> GRID = new ArrayList<Point>();

    //i - сверху вниз
    //q - слева направо
    //accuracy - точность от 0 до 1. 1 - наихудшая точность. -> 0 - наилушая
    public static double accuracy = 0.5;
    public static void fill_GRID() {
        for (double i = dSize; i >= -dSize; i-=accuracy) {

            for (double q = -dSize; q <= dSize; q+=accuracy) {
               // System.out.println(i+" "+q);
                GRID.add(new Point(q, i));
            }

        }
    }

    //Решить задачу
    public void solve() {
        fill_GRID();

        CirclesList.add(new Circle(new Point(5, 3), new Point(6, 4)));
      //  CirclesList.add(new Circle(new Point(0, -6), new Point(0, -1)));
       CirclesList.add(new Circle(new Point(-11, -11), new Point(-11, -8)));
        CirclesList.add(new Circle(new Point(10, 3), new Point(0, 4)));

       // QuadsList.add(new Quad(new Point(3, 4), new Point(5, -2), new Point(9, 3)));
      //  QuadsList.add(new Quad(new Point(-1, -3), new Point(-3, -4), new Point(2, 7)));
        QuadsList.add(new Quad(new Point(-10, -11), new Point(-5, 12), new Point(-11, 6)));



        for (Circle c: CirclesList) {
            c.fill_listInside();
            System.out.println(c.pointsInside.size());
        }
        System.out.println();
        for (Quad q: QuadsList) {
            q.fill_listInside();
            System.out.println(q.pointsInside.size());
        }
        System.out.println();
        for (Circle c: CirclesList) {
            for (Quad q: QuadsList) {
                System.out.println(c.IntersectionArea(q));
            }
        }


        for(Quad q : QuadsList){
            for(Circle c : CirclesList){
                double nowArea = c.IntersectionArea(q);
                if(nowArea>maxArea){
                    resultCircle = c;
                    resultQuad = q;
                    maxArea = nowArea;
                }
            }
        }
        System.out.println("MAX AREA " + maxArea);



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





        for (Quad q: QuadsList) {
            q.render(gl);
       }


        for (Circle c: CirclesList) {
            c.render(gl);
        }

        //данные для дебага
        Point p = new Point(3.1, 4);
        Point p1 = new Point(6, 3);

        Color white = new Color(255, 255, 255);
        Figures.renderDecart(gl);

        fill_GRID();
       for (Point pg: GRID) {
           pg.render(gl);
        }

        if(resultCircle!=null && resultQuad!=null){
            resultCircle.render(gl, new Color(255,255,0), false);
            resultQuad.render(gl, new Color(255,255, 0), false);
        }

    }
}
