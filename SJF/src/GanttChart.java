import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GanttChart {

        public static void DrawGanttChart (List<Process> processes) {
                // Create a task series for each level
                TaskSeries level1Series = new TaskSeries("First Part of Process");
                TaskSeries level2Series = new TaskSeries("Second Part of Process");
                for (int i = 0 ; i < processes.size() ; i ++) {
                        ProcessManagement.addProcess(processes.get(i).getBurstTime() , processes.get(i).getArrivalTime());
                }
                ProcessManagement.serve();
                ProcessManagement.Calculation();

                for (int i = 0 ; i < processes.size() ; i ++){
                        String [] IDs = new String [processes.size()] ;
                        IDs [i] = "P" + " " + (processes.get(i).getID()) ;
                        double theLeastArrivalTime = 0 ;
                        double theGreatestTurnaroundTime = 0;
                        for (int j = 0 ; j < processes.size() - 1 ; j ++ ){
                                if (processes.get(j).getArrivalTime() < processes.get(j+1).getArrivalTime() ) {
                                        theLeastArrivalTime = processes.get(j).getArrivalTime();
                                }
                                if (ProcessManagement.turnaroundtime(processes.get(j).getID() + 4 ) <ProcessManagement.turnaroundtime(processes.get(j+1).getID() + 4 ) ) {
                                        theGreatestTurnaroundTime = ProcessManagement.turnaroundtime(processes.get(j+1).getID() + 4 );
                                }
                        }

                        Task t = new Task(IDs[i] , new SimpleTimePeriod((int)processes.get(i).getArrivalTime() ,(int)processes.get(i).getBurstTime())) ;
                        t.addSubtask(new Task(IDs[i] , new SimpleTimePeriod((int)processes.get(i).getArrivalTime() , (int)ProcessManagement.responseTime((processes.get(i).getID()) + 4) + 5)));
                        level1Series.add(t);
                        Task tt = new Task(IDs[i] , new SimpleTimePeriod((int)theLeastArrivalTime,(int)theGreatestTurnaroundTime)) ;
                        tt.addSubtask(new Task(IDs[i] , new SimpleTimePeriod((int)ProcessManagement.waitingTime((processes.get(i).getID()) + 4), (int)ProcessManagement.turnaroundtime((processes.get(i).getID()) + 4))));
                        level2Series.add(tt);
                }


                TaskSeriesCollection dataset = new TaskSeriesCollection();

                dataset.add(level1Series);

                dataset.add(level2Series);

                // Create the chart
                JFreeChart chart = ChartFactory.createGanttChart(
                        "Shortest Job first Gantt Chart", // Chart title
                        "Process",               // Domain axis label
                        "Turnaround Time",               // Range axis label
                        dataset,              // Data
                        true,                 // Include legend
                        true,                 // Tooltips
                        false                 // URLs
                );

                // Customize the chart
                CategoryPlot plot = (CategoryPlot) chart.getPlot();
                GanttRenderer renderer = new GanttRenderer();

                // Adjust the thickness of the bars
                renderer.setMaximumBarWidth(0.05); // Set the maximum bar width (0.05 = 5% of the space available for a task)

                // Adjust the margins to control the space between levels
                plot.getDomainAxis().setCategoryMargin(0.3); // Increase the category margin to separate the levels

                plot.setRenderer(renderer);

                // Display the chart
                SwingUtilities.invokeLater(() -> {
                        JFrame frame = new JFrame("Gantt Chart");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.add(new ChartPanel(chart));
                        frame.pack();
                        frame.setVisible(true);
                });
        }
}




