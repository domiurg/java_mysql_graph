package domiurg;

import java.util.*;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class Main {
    static TimeSeries series = new TimeSeries("Humidity", Millisecond.class);

    static class Generate implements Runnable {
        public void run(){
            SQLConnect db = new SQLConnect();
            while (true){
                try {
                    //System.out.println(db.readDB());
                    HashMap data = db.readDB();
                    Set set = data.entrySet();
                    Iterator i = set.iterator();
                    while (i.hasNext()){
                        Map.Entry tuple = (Map.Entry)i.next();
                        System.out.println(Float.parseFloat(tuple.getValue().toString()));
                        series.addOrUpdate(new Millisecond(), Float.parseFloat(tuple.getValue().toString()));
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(2000);                 //1000 milliseconds is one second.
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Generate gen = new Generate();
        new Thread(gen).start();

        TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Humidity",
                "Time",
                "Percentage",
                dataset,
                true,
                true,
                false
        );

        final XYPlot plot = chart.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);

        JFrame frame = new JFrame("Humidity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel label = new ChartPanel(chart);
        frame.getContentPane().add(label);
        //Suppose I add combo boxes and buttons here later

        frame.pack();
        frame.setVisible(true);
    }
}

