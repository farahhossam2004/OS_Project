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

    public static void DrawGanttChart () {

        List<Process> processes = ProcessManagement.getAllProcesses();
        List<Switch> switches  = ProcessManagement.getAllSwitch();
        // Create a task series for each level
        TaskSeries level1Series = new TaskSeries("Process execution time");




        //looping over all the process array list to draw the gantt chart
        for (int i = 0 ; i < processes.size() ; i ++) {
            String[] IDs = new String[processes.size()];
            IDs[i] = "P" + " " + (processes.get(i).getID());
            double theLeastArrivalTime ;
            double theGreatestTurnaroundTime ;
            int levelOneStart = 0 ;
            int levelOneEnd = 0 ;


            //getting the highest turnaround time and the lowest arrival time
            int [] turnaroundTimes  = new int [processes.size()] ;
            turnaroundTimes [i] = (int) ProcessManagement.turnaroundtime(processes.get(i).getID());
            int max = turnaroundTimes[0] ;
            theGreatestTurnaroundTime = Math.max(max, turnaroundTimes [i]);
            int [] arrivalTimes  = new int [processes.size()];
            arrivalTimes [i] = (int) processes.get(i).getArrivalTime();
            int min = arrivalTimes [0] ;
            theLeastArrivalTime = Math.min(min , arrivalTimes[i]);


            //checking if process arrived and served once it's arrived if it's done we serve the process until it's switch time
            for (int j = 0 ; j < switches.size() ; j ++) {
                if (switches.get(j).getPrevProcess() != null) {

                    //handling boundary conditions and null pointer exception
                    try {
                        if (switches.get(j).getPrevProcess() == null){
                            if (ProcessManagement.responseTime(processes.get(j).getID()) == 0 && switches.get(j).getPrevProcess().getID() == processes.get(j).getID()) {
                                levelOneStart = (int) processes.get(j).getArrivalTime();
                                levelOneEnd = (int) switches.get(j).switchtime;
                            }
                        }
                        else {
                            try {
                                if (switches.get(j).getPrevProcess() != null) {
                                    if (ProcessManagement.responseTime(processes.get(i).getID()) == 0 && switches.get(i).getPrevProcess().getID() == processes.get(i).getID()) {
                                        levelOneStart = (int) processes.get(i).getArrivalTime();
                                        levelOneEnd = (int) switches.get(i).switchtime;
                                    }
                                }
                            }
                            catch (NullPointerException e) {
                                if (ProcessManagement.responseTime(processes.get(j).getID()) == 0 && switches.get(j).getPrevProcess().getID() == processes.get(j).getID()) {
                                    levelOneStart = (int) processes.get(i).getArrivalTime();
                                    levelOneEnd = (int) switches.get(i).switchtime;
                                }
                            }
                        }
                    }
                    catch (IndexOutOfBoundsException e) {
                        try {
                            if (switches.get(i).getPrevProcess() != null) {
                                if (ProcessManagement.responseTime(processes.get(j).getID()) == 0 && switches.get(j).getPrevProcess().getID() == processes.get(j).getID()) {
                                    levelOneStart = (int) processes.get(j).getArrivalTime();
                                    levelOneEnd = (int) switches.get(j).switchtime;
                                }
                            }
                        }
                        catch (IndexOutOfBoundsException ee){
                            if (switches.get(i - 1).getPrevProcess() != null) {
                                if (ProcessManagement.responseTime(processes.get(i - 1).getID()) == 0 && switches.get(i - 1).getPrevProcess().getID() == processes.get(i - 1).getID()) {
                                    levelOneStart = (int) processes.get(i - 1).getArrivalTime();
                                    levelOneEnd = (int) switches.get(i - 1).switchtime;
                                    theGreatestTurnaroundTime = ProcessManagement.turnaroundtime(processes.get(i).getID()) + 15;
                                }
                            }
                        }
                    }


                }
                else if (switches.get(j).getPrevProcess() == null){
                    theGreatestTurnaroundTime = ProcessManagement.turnaroundtime(processes.get(i).getID()) + 15;
                }

                else {
                    levelOneStart = (int) processes.get(i).getArrivalTime() ;
                    levelOneEnd = levelOneStart  ;
                }
            }




            //if process end clock is zero we set it to its compilation time
            if (processes.get(i).getEndClock() == 0.0) {
                processes.get(i).setEndClock(ProcessManagement.turnaroundtime(processes.get(i).getID()) + processes.get(i).getArrivalTime());
            }
            if (processes.get(i).getEndClock() < processes.get(i).getStartClock()) {
                processes.get(i).setEndClock(ProcessManagement.turnaroundtime(processes.get(i).getID()) + processes.get(i).getArrivalTime());
            }

            //Creating the first task which represents the process
            Task t = new Task(IDs[i] , new SimpleTimePeriod((int) theLeastArrivalTime , (int) theGreatestTurnaroundTime )) ;
            t.addSubtask(new Task(IDs[i] , new SimpleTimePeriod(levelOneStart , levelOneEnd)));
            System.out.println(processes.get(i).getStartClock());
            System.out.println(processes.get(i).getEndClock());
            t.addSubtask(new Task(IDs[i] , new SimpleTimePeriod((int) processes.get(i).getStartClock() , (int) processes.get(i).getEndClock())));
            level1Series.add(t);
    }


        TaskSeriesCollection dataset = new TaskSeriesCollection();

        dataset.add(level1Series);

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
                            processes.clear();
                            switches.clear();
                            BackToPreviousScene();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    });


                }            
                public void BackToPreviousScene(){
                    frame.dispose();
                }


            });
        });
    }
}