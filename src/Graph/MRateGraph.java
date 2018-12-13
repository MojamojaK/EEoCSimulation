package Graph;

import Simulation.Simulator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;

public class MRateGraph extends ApplicationFrame {
    public MRateGraph(ArrayList<Simulator> simulators) {
        super("Rate of Mutual Cooperation");
        XYSeries series = new XYSeries("Average Mutual Cooperation Rate");

        ArrayList<Double> average = new ArrayList<>();
        for (int i = 0; i < Simulator.getRoundCount(); i++) {
            average.add(0.0);
        }
        for (Simulator simulator: simulators) {
            for (int i = 0; i < Simulator.getRoundCount(); i++) {
                average.set(i, average.get(i) + simulator.getMRate().get(i));
            }
        }
        for (int i = 0; i < Simulator.getRoundCount(); i++) {
            average.set(i, average.get(i) / Simulator.getAttemptCount());
            series.add(i, average.get(i));
        }

        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Rate of Mutual Cooperation",
                "Round",
                "Rate",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
