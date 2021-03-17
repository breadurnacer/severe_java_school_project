package problem;

import javax.media.opengl.GL2;
import java.util.ArrayList;

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

    public Problem(){

        CirclesList.add(new Circle(new Point(5, 3), new Point(6, 4)));
        CirclesList.add(new Circle(new Point(0, -6), new Point(0, -1)));
        CirclesList.add(new Circle(new Point(-11, -11), new Point(-11, -8)));
        CirclesList.add(new Circle(new Point(10, 3), new Point(0, 4)));

        QuadsList.add(new Quad(new Point(3, 4), new Point(5, -2), new Point(9, 3)));
        QuadsList.add(new Quad(new Point(-1, -3), new Point(-3, -4), new Point(2, 7)));
        QuadsList.add(new Quad(new Point(-10, -11), new Point(-5, 12), new Point(-11, 6)));
    }

    //результативные значение и объекты
    double maxArea = 0;
    Circle resultCircle = null;
    Quad resultQuad = null;


    //список всех точек на заданной плоскости (сетка)
    public static ArrayList<Point> GRID = new ArrayList<Point>();

    //i - сверху вниз
    //q - слева направо
    //accuracy - точность от 0 до 1. 1 - наихудшая точность. -> 0 - наилушая. больше 1 - ещё хуже (не рекомендуется)
    public static double accuracy = 0.5;

    public static void fill_GRID() {
        for (double i = dSize; i >= -dSize; i -= accuracy) {

            for (double q = -dSize; q <= dSize; q += accuracy) {
                // System.out.println(i+" "+q);
                GRID.add(new Point(q, i));
            }

        }
        System.out.println("GRID SIZE " + GRID.size() + " points");
    }

    //Решить задачу
    public void solve() {
        if(GRID.size()==0)
        fill_GRID();

        for (Circle c : CirclesList) {
            c.fill_listInside();
        }

        for (Quad q : QuadsList) {
            q.fill_listInside();
        }


        for (Quad q : QuadsList) {
            for (Circle c : CirclesList) {
                double nowArea = c.intersectionArea(q);
                if (nowArea > maxArea) {
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
        CirclesList.clear();
        QuadsList.clear();
        maxArea = 0;
        resultCircle = null;
        resultQuad = null;
    }

    public void render(GL2 gl) {

        //отрисовать прямоугольники
        for (Quad q : QuadsList) {
            q.render(gl);
        }
        //отрисовать окружности
        for (Circle c : CirclesList) {
            c.render(gl);
        }

        //белый цвет
        Color white = new Color(255, 255, 255);

        //отрисовать оси абцисс и ординат
        Figures.renderDecart(gl);

        //отрисовать результативные круг и прямоугольник жёлтым цветом, если они есть
        if (resultCircle != null && resultQuad != null) {
            resultCircle.render(gl, new Color(255, 255, 0), false);
            resultQuad.render(gl, new Color(255, 255, 0), false);
        }

    }
}
