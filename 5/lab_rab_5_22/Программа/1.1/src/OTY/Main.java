package OTY;


import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.*;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

enum Dependence{
    RISE_TIME("Время нарастания", "tн") {
        public void add(GraphBuilder gb, double tau) {
            gb.addTh(tau);
        }
    },
    TRANSITION_TIME("Время переходного процесса", "tп") {
        public void add(GraphBuilder gb, double tau) {
            gb.addTp(tau);
        }
    },
    OVERSHOOT("Перерегулирование", "sigma") {
        public void add(GraphBuilder gb, double tau) {
            gb.addOvershoot(tau);
        }
    },
    ERROR("Статическая погрешность", "delta") {
        public void add(GraphBuilder gb, double tau) {
            gb.addError(tau);
        }
    };

    private String title;
    private String yAxisLabel;
    Dependence(String title, String yAxisLabel) {
        this.title = title;
        this.yAxisLabel = yAxisLabel;
    }

    public abstract void add(GraphBuilder gb, double tau);
    public String getTitle() {
        return title;
    }
    public String getyAxisLabel() {return  yAxisLabel;}
}

public class Main {
   //private static double tau = 3.6351163881971404E-4;
    private static double tau;
    public static void main(String[] args) {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame("Лабораторная работа №5");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        // TODO тут менять tau
        tau = 0.3168;

        Menu menu = new Menu();
        menu.items.add(new MenuItem("График АФЧХ предельной системы") {
            @Override
            public void run() {
                APFR(panel, frame);
            }
        });
        menu.items.add(new MenuItem("Графики АФЧХ системы с запаздыванием") {
            @Override
            public void run() {
                menu.subMenu = new Menu();
                apfrChoice(menu.subMenu, panel, frame);
            }
        });
        menu.items.add(new MenuItem("Посчитать графически критическое время запаздывания") {
            @Override
            public void run() {
                calcGraph(panel, frame);
            }
        });
        menu.items.add(new MenuItem("Моделирование") {
            @Override
            public void run() {
                menu.subMenu = new Menu();
                modelingChoice(menu.subMenu, panel, frame);
            }
        });
        menu.items.add(new MenuItem("Зависимости") {
            @Override
            public void run() {
                menu.subMenu = new Menu();
                dependenceChoice(menu.subMenu, panel, frame);
            }
        });
        menu.run();
        System.exit(0);
    }
    public static void  calcGraph(JPanel panel, JFrame frame) {
        // TODO тут менять входные значения
        GraphBuilder graphBuilder = new GraphBuilder(50, 100, 25, 0.1, 0.01);
        JFreeChart chart = ChartFactory.createXYLineChart("Графическое нахождение критического времени запаздывания", "", "", graphBuilder.getCollection(), PlotOrientation.VERTICAL, true, true, true);
        clear(chart, panel, graphBuilder);

        graphBuilder.calcGraphically();

        XYPlot plot = chart.getXYPlot();
        graphBuilder.addAxes(0.5, 0.5, plot, "Re", "Im", false, true);
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.black);
        renderer.setSeriesVisibleInLegend(0, false,true);
        renderer.setSeriesStroke(0, new BasicStroke(0.5f));

        repaint(chart, panel, frame);

    }
    public static void modeling(double  tau, JPanel panel, JFrame frame) {
        GraphBuilder graphBuilder = new GraphBuilder(10, 100, 25, 0.1, 0.01);
        JFreeChart chart = ChartFactory.createXYLineChart("Моделирование", "t", "h", graphBuilder.getCollection(), PlotOrientation.VERTICAL, true, true, true);
        clear(chart, panel, graphBuilder);

        graphBuilder.addGraph("Переходная функция системы с запаздыванием", tau);

        repaint(chart, panel, frame);
    }
    public static void APFR(JPanel panel, JFrame frame) {
        GraphBuilder graphBuilder = new GraphBuilder(50, 100, 25, 0.1, 0.01);
        JFreeChart chart = ChartFactory.createXYLineChart("Критерий устойчивости Найквиста", "", "", graphBuilder.getCollection(), PlotOrientation.VERTICAL, true, true, true);
        clear(chart, panel, graphBuilder);

        graphBuilder.addAPFR("АФЧХ предельной системы");

        XYPlot plot = chart.getXYPlot();
        graphBuilder.addAxes(10, 5, plot, "Re", "Im", false, false);

        panel.setPreferredSize(new Dimension(400, 400));
        repaint(chart, panel, frame);

    }  //построение АФЧХ
    public static void APRFwithDelay(double tau, JPanel panel, JFrame frame) {
        GraphBuilder graphBuilder = new GraphBuilder(50, 100, 25, 0.1, 0.01);
        JFreeChart chart = ChartFactory.createXYLineChart("АФЧХ", "", "", graphBuilder.getCollection(), PlotOrientation.VERTICAL, true, true, true);
        clear(chart, panel, graphBuilder);

        graphBuilder.addAPFRwithDelay("АФЧХ системы с запаздыванием", tau);

        XYPlot plot = chart.getXYPlot();
        graphBuilder.addAxes(0.5, 0.2, plot, "Re", "Im", false, false);

        repaint(chart, panel, frame);
    }
    public static void dependences(Dependence dependence, JPanel panel, JFrame frame) {
        GraphBuilder graphBuilder = new GraphBuilder(70, 100, 25, 0.1, 0.01);
        JFreeChart chart = ChartFactory.createXYLineChart(dependence.getTitle(), "tau", dependence.getyAxisLabel(), graphBuilder.getCollection(), PlotOrientation.VERTICAL, true, true, true);
        clear(chart, panel, graphBuilder);

        dependence.add(graphBuilder, 0.18);

        repaint(chart, panel, frame);
    }

    private static void apfrChoice(Menu menu, JPanel panel, JFrame frame) {
        menu.items.add(new MenuItem("tau < tau критическое") {
            @Override
            public void run() {
                APRFwithDelay(tau - 0.03, panel, frame);
            }
        });
        menu.items.add(new MenuItem("tau = tau критическое") {
            @Override
            public void run() {
                APRFwithDelay(tau, panel, frame);
            }
        });
        menu.items.add(new MenuItem("tau > tau критическое") {
            @Override
            public void run() {
                APRFwithDelay(tau + 0.03, panel, frame);
            }
        });
        menu.runAsSubMenu();
    }
    private static void modelingChoice(Menu menu, JPanel panel, JFrame frame) {
        menu.items.add(new MenuItem("tau < tau критическое") {
            @Override
            public void run() {
                modeling(tau - 0.03, panel, frame);
            }
        });
        menu.items.add(new MenuItem("tau = tau критическое") {
            @Override
            public void run() {
                modeling(tau, panel, frame);
            }
        });
        menu.items.add(new MenuItem("tau > tau критическое") {
            @Override
            public void run() {
                modeling(tau + 0.03, panel, frame);
            }
        });
        menu.runAsSubMenu();
    }
    private static void dependenceChoice(Menu menu, JPanel panel, JFrame frame) {
        menu.items.add(new MenuItem("Время нарастания") {
            @Override
            public void run() {
                dependences(Dependence.RISE_TIME, panel, frame);
            }
        });
        menu.items.add(new MenuItem("Время переходного процесса") {
            @Override
            public void run() {
                dependences(Dependence.TRANSITION_TIME, panel, frame);
            }
        });
        menu.items.add(new MenuItem("Перерегулирование") {
            @Override
            public void run() {
                dependences(Dependence.OVERSHOOT, panel, frame);
            }
        });
        menu.items.add(new MenuItem("Статическая погрешность") {
            @Override
            public void run() {
                dependences(Dependence.ERROR, panel, frame);
            }
        });
        menu.runAsSubMenu();
    }

    private static void clear(JFreeChart chart, JPanel panel, GraphBuilder graphBuilder) {
        XYPlot plot = chart.getXYPlot();
        if(panel.getComponentCount() != 0)
            panel.remove(panel.getComponent(0));
        graphBuilder.clear(plot);
    }
    private static void repaint(JFreeChart chart, JPanel panel, JFrame frame) {
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.GRAY);
        ChartPanel cp = new ChartPanel(chart);
        //cp.setPreferredSize(new Dimension(600, 600));
        panel.add(cp);
        panel.repaint();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}
