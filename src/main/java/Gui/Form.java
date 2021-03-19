package Gui;

import problem.Point;
import problem.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Класс формы приложения
 */
public class Form extends JFrame {
    /**
     * панель для отображения OpenGL
     */

    //Jпанели
    private JPanel GLPlaceholder;
    private JPanel root;

    //текста и надписи
    private JLabel problemText;
    private JLabel addQuadText;
    private JLabel apex1Text;
    private JLabel apex2Text;
    private JLabel quadPText;
    private JLabel addCircleText;
    private JLabel centerText;
    private JLabel circlePText;
    private JLabel solveText;

    //поля для ввода

    //ввод "прямоугольных" точек
    private JTextField xQuadApex1Field;
    private JTextField xQuadApex2Field;
    private JTextField xQuadPointField;
    private JTextField yQuadApex1Field;
    private JTextField yQuadApex2Field;
    private JTextField yQuadPointField;
    private JTextField quadCountField;
    //ввод "прямоугольных" точек

    //ввод "круговых" точек
    private JTextField xCircleCenterField;
    private JTextField xCirclePointField;
    private JTextField yCircleCenterField;
    private JTextField yCirclePointField;
    private JTextField circleCountField;
    //ввод "круговых" точек


    //КНОПКИ
    //основные
    private JButton loadFromFileBtn;
    private JButton saveToFileBtn;
    private JButton clearBtn;
    private JButton solveBtn;
    //добавительные кнопки
    private JButton randomQuad;
    private JButton randomCircle;
    private JButton addQuad;
    private JButton addCircle;

    /**
     * таймер
     */
    private final Timer timer;
    /**
     * рисовалка OpenGL
     */
    private final RendererGL renderer;

    /**
     * Конструктор формы
     */
    private Form() {
        super(Problem.PROBLEM_CAPTION);
        // инициализируем OpenGL
        renderer = new RendererGL();
        // связываем элемент на форме с рисовалкой OpenGL
        GLPlaceholder.setLayout(new BorderLayout());
        GLPlaceholder.add(renderer.getCanvas());
        // указываем главный элемент формы
        getContentPane().add(root);
//        Dimension d = getPreferredSize();
//        d.height = d.width;
//        // задаём размер формы
//        setSize(d);
        // задаём размер формы



        setSize(getPreferredSize());
        // показываем форму
        setVisible(true);
        // обработчик зарытия формы
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    renderer.close();
                    timer.stop();
                    System.exit(0);
                }).start();
            }
        });
        // тинициализация таймера, срабатывающего раз в 100 мсек
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onTime();
            }
        });
        timer.start();
        renderer.problem.solve();
        initWidgets();
    }

    /**
     * Инициализация виджетов
     */
    private void initWidgets() {
        // задаём текст полю описания задачи
        problemText.setText("<html>" + Problem.PROBLEM_TEXT.replaceAll("\n", "<br>"));

        //задаю текст для остальных текстов
        addQuadText.setText("ЗАДАТЬ ПРЯМОУГОЛЬНИК");
        apex1Text.setText("Первая вершина: ");
        apex2Text.setText("Вторая вершина: ");
        quadPText.setText("Точка на противоположной стороне: ");
        addCircleText.setText("ЗАДАТЬ ОКРУЖНОСТЬ");
        centerText.setText("Центр окружности: ");
        circlePText.setText("Точка на окружности: ");
        solveText.setText("Решение: ");

        addQuad.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double x1 = Double.parseDouble(xQuadApex1Field.getText());
                double y1 = Double.parseDouble(yQuadApex1Field.getText());
                double x2 = Double.parseDouble(xQuadApex2Field.getText());
                double y2 = Double.parseDouble(yQuadApex2Field.getText());
                double xp = Double.parseDouble(xQuadPointField.getText());
                double yp = Double.parseDouble(yQuadPointField.getText());
                Point
                        Point1 = new Point(x1,y1),
                        Point2 = new Point(x2,y2),
                        PointP = new Point(xp,yp);
                renderer.problem.addQuad(Point1, Point2, PointP);
            }
        });

        addCircle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double xc = Double.parseDouble(xCircleCenterField.getText());
                double yc = Double.parseDouble(yCircleCenterField.getText());
                double xp = Double.parseDouble(xCirclePointField.getText());
                double yp = Double.parseDouble(yCirclePointField.getText());
                Point
                        PointC = new Point(xc,yc),
                        PointP = new Point(xp,yp);
                renderer.problem.addCircle(PointC, PointP);
            }
        });

//        loadFromFileBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                renderer.problem.loadFromFile();
//            }
//        });

//        saveToFileBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                renderer.problem.saveToFile();
//            }
//        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.clear();
            }
        });

        solveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.solve();
            }
        });
    }

    /**
     * Событие таймера
     */
    private void onTime() {
        // события по таймеру
    }

    /**
     * Главный метод
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        new Form();
    }
}
