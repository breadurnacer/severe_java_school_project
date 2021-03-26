package problem;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Problem {

    public static double dSize = 20;

    public static final String PROBLEM_TEXT = "ПОСТАНОВКА ЗАДАЧИ:\n" +
            "Задано множество окружностей и множество прямоугольников.\n" +
            "Найти такую пару прямоугольник-окружность, что фигура, \n" +
            "находящаяся внутри прямоугольника и окружности, \n" +
            "имеет максимальную площадь. Выделить найденные \n" +
            "прямоугольник и окружность, контур фигуры и \n" +
            "внутреннее пространство фигуры.\n";


    public static final String PROBLEM_CAPTION = "ИТОГОВЫЙ ПРОЕКТ УЧЕНИКА 10-7 БОРИСЕНКО НИКОЛАЯ";

    private static final String FILE_NAME = "points.txt";

    //отрисововать ли оси
    public boolean axis = true;

    //список заданных прямоугольников
    public ArrayList<Quad> QuadsList = new ArrayList<Quad>();

    //список заданных окружностей
    public ArrayList<Circle> CirclesList = new ArrayList<Circle>();

    //результативные значение и объекты
    double maxArea = 0;
    public Circle resultCircle = null;
    public Quad resultQuad = null;
    ArrayList<Point> resultPointIntersection = new ArrayList<Point>();

    public Problem() {
        //чисто тестовые начальные окружности и прямоугольники
        CirclesList.add(new Circle(new Point(5, 3), new Point(6, 4)));
        CirclesList.add(new Circle(new Point(0, -6), new Point(0, -1)));
        CirclesList.add(new Circle(new Point(-11, -11), new Point(-11, -8)));
        CirclesList.add(new Circle(new Point(10, 3), new Point(0, 4)));

        QuadsList.add(new Quad(new Point(3, 4), new Point(5, -2), new Point(9, 3)));
        QuadsList.add(new Quad(new Point(-1, -3), new Point(-3, -4), new Point(2, 7)));
        QuadsList.add(new Quad(new Point(-10, -11), new Point(-5, 12), new Point(-11, 6)));
    }

    //i - сверху вниз
    //q - слева направо
    //accuracy - точность от 0 до 1. 1 - наихудшая точность. -> 0 - наилушая. больше 1 - ещё хуже (не рекомендуется)
    //np - удобнее и понятнее для пользователя 1 - средняя точность. ниже - хуже. выше - лучше (увеличивать можно сколь угодно много)
    //по умолчанию np = 3

    //список всех точек на заданной плоскости (сетка)
    public static ArrayList<Point> GRID = new ArrayList<Point>();

    public static double np = 3;
    public static double accuracy = 1 / np;

    public static void fill_GRID() {
        GRID.clear();
        accuracy = 1 / np;
        for (double i = dSize; i >= -dSize; i -= accuracy) {

            for (double q = -dSize; q <= dSize; q += accuracy) {
                // System.out.println(i+" "+q);
                GRID.add(new Point(q, i));
            }

        }
        System.out.println("GRID SIZE " + GRID.size() + " points " + "np = " + np);
    }

    //Решить задачу
    public void solve() {
        maxArea = 0;
        resultCircle = null;
        resultQuad = null;
        resultPointIntersection.clear();

        if (GRID.size() == 0)
            fill_GRID();

        for (Circle c : CirclesList) {
            c.pointsInside.clear();
            c.fill_listInside();
        }

        for (Quad q : QuadsList) {
            q.pointsInside.clear();
            q.fill_listInside();
        }


        for (Quad q : QuadsList) {
            for (Circle c : CirclesList) {
                ArrayList<Point> nowPoints = c.intersectionArea(q);
                double nowArea = nowPoints.size();
                if (nowArea > maxArea) {
                    resultCircle = c;
                    resultQuad = q;
                    maxArea = nowArea;
                    resultPointIntersection = nowPoints;
                }
            }
        }
        System.out.println("MAX AREA " + maxArea);
    }

    //добавить прямоугольник
    public void addQuad(Point p1, Point p2, Point pP) {
        Quad newestQuad = new Quad(p1, p2, pP);
        QuadsList.add(newestQuad);
    }

    //добавить окружность
    public void addCircle(Point pC, Point pIn) {
        Circle newestCircle = new Circle(pC, pIn);
        CirclesList.add(newestCircle);
    }

    Random random = new Random();

    //получить случайный прямоугольник
    public void getRandomQuad(int cntQ) {
        for (int i = 0; i < cntQ; i++) {
            Point
                    point1 = new Point(random.nextDouble() * 2 * dSize - dSize, random.nextDouble() * 2 * dSize - dSize),
                    point2 = new Point(random.nextDouble() * 2 * dSize - dSize, random.nextDouble() * 2 * dSize - dSize),
                    pointP = new Point(random.nextDouble() * 2 * dSize - dSize, random.nextDouble() * 2 * dSize - dSize);
            addQuad(point1, point2, pointP);
        }
    }

    //получить случайную окружность
    //окружность генерируется обязательно (!) внутри выделенного куска декартовой системы
    public void getRandomCircle(int cntC) {
        for (int i = 0; i < cntC; i++) {
            double rndCx = random.nextDouble() * 2 * dSize - dSize,
                    rndCy = random.nextDouble() * 2 * dSize - dSize;

            ArrayList<Double> dists = new ArrayList<Double>();
            dists.add(dSize - rndCx);
            dists.add(dSize + rndCy);
            dists.add(dSize + rndCx);
            dists.add(dSize - rndCy);
            double min = dSize;

            for (Double d : dists) {
                if (d < min) {
                    min = d;
                }
            }

            double rndPx = rndCx,
                    rndPy = random.nextDouble() * min + rndCy;

            Point
                    pointC = new Point(rndCx, rndCy),
                    pointP = new Point(rndPx, rndPy);
            addCircle(pointC, pointP);
        }
    }

    //Загрузить задачу из файла
    public void loadFromFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            QuadsList.clear();
            CirclesList.clear();
            while ((line = reader.readLine()) != null) {
                workLine(line);
            }

            reader.close();
        } catch (Exception ex) {
            System.out.println("couldn't read the file");
            ex.printStackTrace();
        }
    }

    private static boolean qBool = false;
    private static boolean cBool = false;
    private static int typeRead;
    private static double dSRead;

    private void workLine(String lineToParse) {
        String[] s = lineToParse.split(" ");
        //если тип
        if (s[0].equals("type")) {
            //какой тип
            if (s[1].equals("1")) {
                typeRead = 1;
            } else if (s[1].equals("2")) {
                typeRead = 2;
            }
        } else //если размер квадрата то какой
            if (s[0].equals("dSize")) {
                dSRead = Double.parseDouble(s[1]);
                dSize = dSRead;
            } else if (s[0].equals("QuadsList")) {
                qBool = true;
                cBool = false;
            } else if (s[0].equals("CirclesList")) {
                qBool = false;
                cBool = true;
            } else {
                if (typeRead == 1) {
                    //если читается прямоугольник
                    if (qBool == true) {
                        Point[] pQ = new Point[4];
                        s = lineToParse.split(", ");
                        for (int i = 0; i < 4; i++) {
                            String pS = s[i].replace("(", "");
                            pS = pS.replace(")", "");
                            pS = pS.replace(",", ".");
                            String[] xyS = pS.split("; ");
                            pQ[i] = new Point(Double.parseDouble(xyS[0]), Double.parseDouble(xyS[1]));
                        }
                        QuadsList.add(new Quad(pQ[0], pQ[1], pQ[2], pQ[3]));
                    }

                    //если читается круг
                    if (cBool == true) {
                        Point pC;
                        double rC;
                        String lineS = lineToParse;
                        lineS = lineS.replaceAll(" R = ", "/");
                        s = lineS.split("/");

                        String pS = s[0].replace("(", "");
                        pS = pS.replace(")", "");
                        pS = pS.replace(",", ".");
                        String[] xyS = pS.split("; ");
                        pC = new Point(Double.parseDouble(xyS[0]), Double.parseDouble(xyS[1]));
                        rC = Double.parseDouble(s[1].replace(",","."));

                        CirclesList.add(new Circle(pC, rC));
                    }
                }

                if (typeRead == 2) {
                    //если читается прямоугольник
                    if (qBool == true) {
                        Point[] pQ = new Point[3];
                        s = lineToParse.split(", ");
                        for (int i = 0; i < 3; i++) {
                            String pS = s[i].replace("(", "");
                            pS = pS.replace(")", "");
                            pS = pS.replace(",", ".");
                            String[] xyS = pS.split("; ");
                            pQ[i] = new Point(Double.parseDouble(xyS[0]), Double.parseDouble(xyS[1]));
                        }
                        QuadsList.add(new Quad(pQ[0], pQ[1], pQ[2]));
                    }

                    //если читается круг
                    if (cBool == true) {
                        Point[] pC = new Point[2];
                        s = lineToParse.split(", ");
                        for (int i = 0; i < 2; i++) {
                            String pS = s[i].replace("(", "");
                            pS = pS.replace(")", "");
                            pS = pS.replace(",", ".");
                            String[] xyS = pS.split("; ");
                            pC[i] = new Point(Double.parseDouble(xyS[0]), Double.parseDouble(xyS[1]));
                        }
                        CirclesList.add(new Circle(pC[0], pC[1]));
                    }
                }
            }

    }


    //Сохранить задачу в файл
    public void saveToFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String s = "";
            s = "type 1" +
                    "\ndSize " + dSize + " " +
                    "\nQuadsList";
            for (Quad q : QuadsList) {
                s += ("\n" + q);
            }
            s += "\nCirclesList";
            for (Circle c : CirclesList) {
                s += ("\n" + c);
            }

            writer.write(s);
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Очистить задачу
    public void clear() {
        CirclesList.clear();
        QuadsList.clear();
        maxArea = 0;
        resultCircle = null;
        resultQuad = null;
        resultPointIntersection.clear();
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
        if (axis)
            Figures.renderDecart(gl);

        //отрисовать результативные круг и прямоугольник жёлтым цветом, если они есть
        if (resultCircle != null && resultQuad != null) {
            resultCircle.render(gl, new Color(255, 255, 0), false);
            resultQuad.render(gl, new Color(255, 255, 0), false);
            for (Point pInRes : resultPointIntersection) {
                pInRes.render(gl, 3, new Color(100, 100, 255));
            }
        }

    }
}
