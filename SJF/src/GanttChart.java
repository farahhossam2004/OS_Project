import javafx.application.Platform;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;

public class GanttChart {


        public static void DrawGanttChart (List<Process> processes) {


                // Create a task series for each level
                TaskSeries level1Series = new TaskSeries("First Part of Process");
                TaskSeries level2Series = new TaskSeries("Second Part of Process");



                //looping over all the process array list to draw the gantt chart
                for (int i = 0 ; i < processes.size() ; i ++){
                        String [] IDs = new String [processes.size()] ;
                        IDs [i] = "P" + " " + (processes.get(i).getID()) ;


                        double theLeastArrivalTime = 0 ;
                        double theGreatestTurnaroundTime = 0;


                        //internal loop to get the highest turnaround time and the lowest arrival time
                        for (int j = 0 ; j < processes.size() - 1 ; j ++ ){
                                if (processes.get(j).getArrivalTime() < processes.get(j+1).getArrivalTime() ) {
                                        theLeastArrivalTime = processes.get(j).getArrivalTime();
                                }
                                if (ProcessManagement.turnaroundtime(processes.get(j).getID()) < ProcessManagement.turnaroundtime(processes.get(j+1).getID()) ) {
                                        theGreatestTurnaroundTime = ProcessManagement.turnaroundtime(processes.get(j+1).getID());
                                }
                        }



                        //Making sure that the first part of process ends at the right point
                        int levelOneEnd  ;
                        if (ProcessManagement.responseTime(processes.get(i).getID()) > processes.get(i).getArrivalTime()) {
                            levelOneEnd = (int)processes.get(i).getArrivalTime();
                        }
                        else if (ProcessManagement.responseTime(processes.get(i).getID()) == 0 && processes.get(i).getID() != 1.0){
                            levelOneEnd = (int) ProcessManagement.responseTime(processes.get(i).getID()) + 1 ;
                        }
                        else if (processes.get(i).getArrivalTime() > ProcessManagement.responseTime(processes.get(i).getID())){
                                levelOneEnd = (int) processes.get(i).getArrivalTime();
                        }
                        else {
                            levelOneEnd = (int) ProcessManagement.responseTime(processes.get(i).getID()) + 1 ;
                        }


                        //Creating the first task which represents the first part of the process and adding it to level 1
                        Task t = new Task(IDs[i] , new SimpleTimePeriod(0 , (int)theGreatestTurnaroundTime)) ;
                        t.addSubtask(new Task(IDs[i] , new SimpleTimePeriod((int)processes.get(i).getArrivalTime() , (levelOneEnd))));
                        level1Series.add(t);



                        //Making sure that the second part of process starts from the right point
                        int levelTwoStart ;
                        if (ProcessManagement.responseTime(processes.get(i).getID()) == 0 && processes.get(i).getID() != 1.0){
                              levelTwoStart = (int) ProcessManagement.responseTime(processes.get(i).getID()) + 1 ;
                        }
                        else if (ProcessManagement.waitingTime(processes.get(i).getID()) < processes.get(i).getArrivalTime()){
                            levelTwoStart = (int) ProcessManagement.waitingTime(processes.get(i).getID()) + (int) processes.get(i).getArrivalTime();
                        }
                        else if (ProcessManagement.responseTime(processes.get(i).getID()) == 0) {
                            levelTwoStart = (int) ProcessManagement.waitingTime(processes.get(i).getID()) + 1 ;
                        }
                        else {
                            levelTwoStart = (int) ProcessManagement.waitingTime(processes.get(i).getID()) ;
                        }


                        //Creating the second task which represents the second part of the process and adding it to level 2
                        Task tt = new Task(IDs[i] , new SimpleTimePeriod((int)theLeastArrivalTime,(int)theGreatestTurnaroundTime)) ;
                        tt.addSubtask(new Task(IDs[i] , new SimpleTimePeriod(levelTwoStart , (int)ProcessManagement.turnaroundtime((processes.get(i).getID())))));
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
                        JPanel panel = new JPanel(new BorderLayout());
                        ChartPanel chartPanel = new ChartPanel(chart);
                        panel.add(chartPanel, BorderLayout.CENTER);
                        JButton button = new JButton("Back to calculations window");
                        panel.add(button, BorderLayout.SOUTH);
                        frame.add(panel);
                        frame.pack();
                        frame.setVisible(true);


                        //Back to calculation scene
                        button.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        Platform.runLater(() -> {
                                                try {
                                                        BackToPreviousScene();
                                                } catch (Exception ex) {
                                                        throw new RuntimeException(ex);
                                                }
                                        });


                }            public void BackToPreviousScene(){
                                         frame.dispose();
                                }


                        });
});
        }
}




