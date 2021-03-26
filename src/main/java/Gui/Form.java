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
    private JFrame frame = new JFrame();

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
    private JTextField dSizeField;
    private JTextField npField;

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
    //другие кнопки
    private JButton changeDSizeBth;
    private JButton changeNpBth;
    private JCheckBox checkBoxD;

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
        setSolveTextMethod();
    }

    public void setSolveTextMethod(){
        if(renderer.problem.resultCircle!=null && renderer.problem.resultQuad!=null)
            solveText.setText("<html>" + "Решение: <br>окружность с центром " + renderer.problem.resultCircle
                    + "<br>" + " и прямоугольник с вершинами " +  renderer.problem.resultQuad);
        else
            solveText.setText("Решения нет");
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

        addQuad.setText("Добавить прямоугольник");
        addCircle.setText("Добавить окружность");

        checkBoxD.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (checkBoxD.isSelected()) {
                    renderer.problem.axis = true;
                }else renderer.problem.axis = false;
            }
        });

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

        randomQuad.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int n = (int)Double.parseDouble(quadCountField.getText());
                renderer.problem.getRandomQuad(n);
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

        randomCircle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int n = (int)Double.parseDouble(circleCountField.getText());
                renderer.problem.getRandomCircle(n);
            }
        });

        changeDSizeBth.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double newDSize = Double.parseDouble(dSizeField.getText());
                renderer.problem.dSize = newDSize;
                renderer.problem.fill_GRID();
                //renderer.problem.solve();
            }
        });

        changeNpBth.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double newNp = Double.parseDouble(npField.getText());
                renderer.problem.np = newNp;
                renderer.problem.fill_GRID();
                renderer.problem.solve();
                setSolveTextMethod();
            }
        });

        loadFromFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.showOpenDialog(frame);
                renderer.problem.loadFromFile(fileOpen.getSelectedFile());
                dSizeField.setText(String.valueOf(renderer.problem.dSize));
                renderer.problem.solve();
                setSolveTextMethod();
            }
        });

        saveToFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileSave = new JFileChooser();
                fileSave.showSaveDialog(frame);
                renderer.problem.saveToFile(fileSave.getSelectedFile());
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.clear();
                setSolveTextMethod();
            }
        });

        solveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.solve();
                setSolveTextMethod();
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
