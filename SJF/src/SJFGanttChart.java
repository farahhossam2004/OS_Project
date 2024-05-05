import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SJFGanttChart extends Application{

    static ArrayList<Process> servedProcesses = ProcessManagement.getServedProcesses();

    @Override
    public void start(Stage stage) throws Exception {
        
        LineChart<Number, String> lineChart = createLineChart();

        // Add tasks to the chart 
        for(Process p : servedProcesses)
        {
            addProcess(lineChart, p.getID(), p.getStartClock(), p.getEndClock());
        }
        Scene scene = new Scene(new StackPane(lineChart), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private LineChart<Number, String> createLineChart()
    {
        CategoryAxis yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();
        xAxis.setAutoRanging(true);
        xAxis.setUpperBound((int)ProcessManagement.CalculateTotalBurstTime());
        
        LineChart<Number, String> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Shortest Job First");
        xAxis.setLabel("Time"); 
        yAxis.setLabel("Processes");
        lineChart.setLegendVisible(false);
        return lineChart;
    }

    private void addProcess(LineChart<Number, String> lineChart, int ProcessID, double startTime , double endTime)
    {
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(startTime, String.valueOf(ProcessID)));
        series.getData().add(new XYChart.Data<>(endTime, String.valueOf(ProcessID)));
        lineChart.getData().add(series);
    }
}