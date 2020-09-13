package OTY;

import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;
import org.jfree.data.xy.XYSeries;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GraphBuilder {  //класс, строящий графики
    private final double dt = 0.0001, dw = 0.001;
    private double l, k, T1, T2, T3, Xmax = 0, Ymax = 0, Xmin = Double.MAX_VALUE, Ymin = Double.MAX_VALUE;

    private VectorSeriesCollection collection;
    public GraphBuilder(double l, double k, double T1, double T2, double T3) {  //конструктор
        this.l = l;
        this.k = k;
        this.T1 = T1;
        this.T2 = T2;
        this.T3 = T3;
        collection = new VectorSeriesCollection();
    }
    public void addGraph(String title, double tau) {  //моделирование системы
       double std = 0, x, g=1, y=0, y1=0, y2=0, y3=0;
        System.out.println(tau);
        int ns = (int) (tau/dt), i=0;
        VectorSeries y_ser = new VectorSeries("Переходная функция системы без запаздывания");
        VectorSeries delay_ser = new VectorSeries(title);
        while(std < l) {
            x = g - y;

            y1 += getY(1/T1, k/T1, x, y1);
            y2 += getY(1/T2, 1/T2, y1, y2);
            y3 += getY(1/T3, 1/T3, y2, y3);

            y_ser.add(std, y3, 0, 0);

            if(i > ns) {  //запаздывание
                y = y_ser.getYValue(i - ns);
            }
            else
                y = 0;
            i++;
            delay_ser.add(std, y, 0, 0);

            std += dt;
        }
        collection.addSeries(y_ser);
        collection.addSeries(delay_ser);
    }
    private double getY(double a0, double alpha, double x, double z) {  //моделирование звена
        double K1, K2, K3, K4;
        K1 = (-a0*z + alpha*x)*dt;
        K2 = (-a0*(z + K1/2) + alpha*x)*dt;
        K3 = (-a0*(z + K2/2) + alpha*x)*dt;
        K4 = (-a0*(z + K3) + alpha*x)*dt;
        return (K1 + 2*K2 + 2*K3 + K4)/6;
    }
    public void addTh(double tau) {  //време нарастания
        double [] tauAray = {0, tau/1.75, tau/1.5,tau/1.25, tau};
        VectorSeries y_ser, th_ser = new VectorSeries("tн(tau)");
        for(int i=0;i<tauAray.length;i++) {
            addGraph("some title", tauAray[i]);
            y_ser = collection.getSeries(1);
            collection.removeAllSeries();
            th_ser.add(tauAray[i], getTh(y_ser),0,0);
            System.out.println("tн(" + tauAray[i] + ") = " + th_ser.getYValue(i));
        }
        collection.addSeries(th_ser);
    }
    private double getTh(VectorSeries series) {
        double th=0, y;
        int itemsCount = series.getItemCount();
        for(int i=0;i<itemsCount;i++) {
            y = series.getYValue(i);
            if(y <= 1.05 && y >= 0.95) {
                th = series.getXValue(i);
                break;
            }
        }
        return  th;
    }
    public void addTp(double tau) {  //время переходного процесса
        double [] tauAray = {0, tau/1.75, tau/1.5,tau/1.25, tau};
        VectorSeries y_ser, tp_ser = new VectorSeries("tп(tau)");
        for(int i=0;i<tauAray.length;i++) {
            addGraph("some title", tauAray[i]);
            y_ser = collection.getSeries(0);
            collection.removeAllSeries();
            tp_ser.add(tauAray[i], getTp(y_ser), 0, 0);
            System.out.println("tп(" + tauAray[i] + ") = " + tp_ser.getYValue(i));
        }
        collection.addSeries(tp_ser);
    }
    private double getTp(VectorSeries series) {
        double tp=0, y;
        int itemsCount = series.getItemCount();
        boolean entered = false;
        for(int i=0;i<itemsCount;i++) {
            y = series.getYValue(i);
            if(y <= 1.05 &&  y >= 0.95) {
                if(!entered) {
                    tp = series.getXValue(i);
                    entered = true;
                }
            }
            else
                entered = false;

            if(y == 0.95)
                System.out.println(tp);
        }
        return  tp;
    }
    public void addOvershoot(double tau) {  //перерегулирование
        double [] tauAray = {0, tau/1.75, tau/1.5,tau/1.25, tau};
        VectorSeries y_ser, ov_ser = new VectorSeries("sigma(tau)");
        for(int i=0;i<tauAray.length;i++) {
            addGraph("some title", tauAray[i]);
            y_ser = collection.getSeries(0);
            collection.removeAllSeries();
            ov_ser.add(tauAray[i], getOvershoot(y_ser), 0, 0);
            System.out.println("sigma(" + tauAray[i] + ") = " + ov_ser.getYValue(i));
        }
        collection.addSeries(ov_ser);
    }
    private double getOvershoot(VectorSeries series) {
        double ov = Ymax;
        for (int i=0;i<series.getItemCount();i++) {
            if(series.getYValue(i) > ov)
                ov = series.getYValue(i);
        }
        return (ov-1)*100;
    }
    public void addError(double tau) {  //статическая ошибка
        double [] tauAray = {0, tau/1.75, tau/1.5,tau/1.25, tau};
        VectorSeries y_ser, err_ser = new VectorSeries("delta(tau)");
        for(int i=0;i<tauAray.length;i++) {
            addGraph("some title", tauAray[i]);
            y_ser = collection.getSeries(0);
            collection.removeAllSeries();
            err_ser.add(tauAray[i], Math.abs(y_ser.getYValue(y_ser.getItemCount()-1) - 1),0,0);
        }
        collection.addSeries(err_ser);
    }

    public void calcGraphically() { //посчитать графически тау
        double t = 0, x, y, w = dt, A, ph, Re, Im;
        VectorSeries circle_ser = new VectorSeries("Единичная окружность");
        VectorSeries APFR_ser = new VectorSeries("АФЧХ предельной системы");
            while(t < 200) {
                x = Math.cos(t);
                y = Math.sin(t);
                circle_ser.add(x ,y, 0, 0);
                t += 0.01;
            }
        while (w < l) {
            A = k / Math.sqrt((1 + Math.pow(T1*w, 2)) * (1 + Math.pow(T2*w, 2)) * (1 + Math.pow(T3*w, 2)));
            ph = -Math.atan(T1*w) -Math.atan(T2*w) - Math.atan(T3*w);

            Re = A * Math.cos(ph);
            Im = A * Math.sin(ph);
            if (Im > -2 && Re <1.5) {
                APFR_ser.add(Re, Im, 0, 0);
                if(inCircle(Re, Im, circle_ser)) {
                    System.out.println("Критическая частота равна " + w);
                    System.out.println("Критическая фаза равна " + (ph));
                    System.out.println("Критическое время запаздывания: " + (3.14 + ph)/w);
                    System.out.println(ph);
                }
            }
            w += dw;
        }
        determineRange(APFR_ser);
        collection.addSeries(circle_ser);
        collection.addSeries(APFR_ser);
    }
    private boolean inCircle(double x, double y, VectorSeries circle_ser) {
        double bottomX, topX, bottomY, topY;
        for(int i=0;i<circle_ser.getItemCount();i++) {
            bottomX = circle_ser.getXValue(i) - circle_ser.getXValue(i)/1000;
            topX = circle_ser.getXValue(i) + circle_ser.getXValue(i)/1000;
            bottomY = circle_ser.getYValue(i) - circle_ser.getYValue(i)/1000;
            topY = circle_ser.getYValue(i) + circle_ser.getYValue(i)/1000;
            if(x < bottomX && x > topX) {
                if(y < bottomY && y > topY)
                    return  true;
            }
        }
        return  false;
    }
    public void addAPFR(String title) {  //АФЧХ без запаздывания
        double w = dt, A, ph, Re, Im;
        VectorSeries APFR_ser = new VectorSeries(title);

        while (w < l) {
            A = k / Math.sqrt((1 + Math.pow(T1*w, 2)) * (1 + Math.pow(T2*w, 2)) * (1 + Math.pow(T3*w, 2)));
            ph = -Math.atan(T1*w) -Math.atan(T2*w) - Math.atan(T3*w);
            Re = A * Math.cos(ph);
            Im = A * Math.sin(ph);
            APFR_ser.add(Re, Im, 0, 0);

            w += dw;
        }
        determineRange(APFR_ser);
        collection.addSeries(APFR_ser);
    }
    public void addAPFRwithDelay(String title, double tau) { //АФЧХ с запаздыванием
        double w = dt, A, ph, Re, Im,Re1, Im1;
        VectorSeries APFR_ser = new VectorSeries("АФЧХ предельной системы");
        VectorSeries newAPFR_ser = new VectorSeries(title);
        while (w < l) {
            A = k / Math.sqrt((1 + Math.pow(T1*w, 2)) * (1 + Math.pow(T2*w, 2)) * (1 + Math.pow(T3*w, 2)));
            ph = -Math.atan(T1*w) -Math.atan(T2*w) - Math.atan(T3*w);
            Re = A * Math.cos(ph);
            Im = A * Math.sin(ph);

            Re1 = A * Math.cos(ph - tau*w);
            Im1 = A * Math.sin(ph - tau*w);
            if(Im > -2 && Re <1.5)
                APFR_ser.add(Re, Im, 0, 0);
            if(Im1 > -2 && Re1 <1.5)
                newAPFR_ser.add(Re1, Im1, 0, 0);

            w += dw;
        }
        //determineRange(APFR_ser);
        determineRange(newAPFR_ser);
        System.out.println(Xmin);
        collection.addSeries(APFR_ser);
        collection.addSeries(newAPFR_ser);
    }

    private void determineRange(VectorSeries series) {
        int itemsCount = series.getItemCount();
        double currentXValue, currentYValue;
        for(int i=0; i<itemsCount; i++) {
            currentXValue = series.getXValue(i);
            currentYValue = series.getYValue(i);

            if(currentXValue > Xmax)
                Xmax = Math.ceil(currentXValue);
            else if(currentXValue < Xmin)
                Xmin = Math.floor(currentXValue);
            if(currentYValue > Ymax)
                Ymax = Math.ceil(currentYValue);
            else if(currentYValue < Ymin)
                Ymin = Math.floor(currentYValue);
        }
    }

    public void addAxes(double dx, double dy, XYPlot plot, String xAsixLabel, String yAsixLabel, boolean ScaleAtTheMaxValue, boolean ScaleAxesByOneValue) {
        if (ScaleAtTheMaxValue) {
            if(Math.abs(Xmax) > Math.abs(Xmin))
                Xmin = -Math.ceil(Xmax);
            else
                Xmax = -Math.ceil(Xmin);
            if(Math.abs(Ymax) > Math.abs(Ymin))
                Ymin = -Math.ceil(Ymax);
            else
                Ymax = -Math.ceil(Ymin);
        }
        if(ScaleAxesByOneValue) {
            if(Math.abs(Xmax) > Math.abs(Xmin))
                Xmin = -Math.ceil(Xmax);
            else
                Xmax = -Math.ceil(Xmin);
            if(Math.abs(Ymax) > Math.abs(Ymin))
                Ymin = -Math.ceil(Ymax);
            else
                Ymax = -Math.ceil(Ymin);
            if(Math.abs(Xmax) < Math.abs(Ymax)) {
                Xmax = Ymax;
                Xmin = -Xmax;
            }
            else {
                Ymax = Xmax;
                Ymin = -Ymax;
            }
           // Ymax += dy;
            //Xmax = Ymax;
            //Xmin = -Ymax;
        }

       // Xmax += dx;
        System.out.println(Xmax);
        //Xmax += dx;
        //Xmin += dx;
        //Ymin -= dy;

        addXaxis(dx);
        addYaxis(dy);
        addXlabels(plot, dx, dy, xAsixLabel);
        addYlabels(plot, dx, dy, yAsixLabel);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(collection.getSeriesCount()-1, Color.BLACK);
        renderer.setSeriesPaint(collection.getSeriesCount()-2, Color.BLACK);
        renderer.setSeriesVisibleInLegend(collection.getSeriesCount()-1, false, true);
        renderer.setSeriesVisibleInLegend(collection.getSeriesCount()-2, false, true);
        Axis axis = plot.getDomainAxis();
        axis.setTickLabelsVisible(false);
        NumberAxis na = (NumberAxis) plot.getRangeAxis();
        na.setVisible(false);

    }
    private void addXaxis(double dx) {
        VectorSeries Xaxis = new VectorSeries("x");
        Xaxis.add(Xmin-dx/2,0,0,0);
        Xaxis.add(Xmax+dx/2,0,0,0);
        collection.addSeries(Xaxis);
    }
    private void addYaxis(double dy) {
        VectorSeries Yaxis = new VectorSeries("y");
        Yaxis.add(0, Ymin-dy/2, 0, 0);
        Yaxis.add(0, Ymax+dy/2, 0, 0);
        collection.addSeries(Yaxis);
    }
    private void addXlabels(XYPlot plot, double dx, double dy, String xAsixLabel) {
        double x = round(Xmin + Math.abs(Xmin % dx),3);
        String str;
        while(x <= Xmax) {
            if(dx % 1 == 0)
                str = Integer.toString((int) x);
            else
                str = Double.toString(x);

            if(x == 0)
                plot.addAnnotation(new XYTextAnnotation(str, dx/5, -dy/3));
            else
                plot.addAnnotation(new XYTextAnnotation(str, x, -dy/3));
                x = round(x, 3) + round(dx, 3);
                x = round(x, 3);
        }
        plot.addAnnotation(new XYTextAnnotation(xAsixLabel, x-dx/2, -dy/3));
    }
    private void addYlabels(XYPlot plot, double dx, double dy, String yAsixLabel) {
        double y = Ymin + Math.abs(Ymin % dy);
        String str;
        while(y <= Ymax) {
            if(dy % 1 == 0)
                str = Integer.toString((int) y);
            else
                str = Double.toString(y);

            if (y != 0)
                plot.addAnnotation(new XYTextAnnotation(str, dx/5, y));
            y += dy;
            y = round(y, 3);
        }
        plot.addAnnotation(new XYTextAnnotation(yAsixLabel, dx/5, y-dy/2));
    }
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public void clear(XYPlot plot) {
        collection.removeAllSeries();
        plot.getAnnotations().clear();
    }

    public VectorSeriesCollection getCollection() { return collection; }
    private void setK(double k) {
        this.k = k;
    }

}
