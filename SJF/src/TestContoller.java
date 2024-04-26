import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;

public class TestContoller {


        @FXML
        Button submit;

        @FXML
        public void Submit (ActionEvent e) throws Exception {

            List<Process> processes = new ArrayList<>();
            Process p1 = new Process(0, 8);
            Process p2 = new Process(1, 4);
            Process p3 = new Process(2, 9);
            Process p4 = new Process(3, 5);
            
            processes.add(p1);
            processes.add(p2);
            processes.add(p3);
            processes.add(p4);

            GanttChart.DrawGanttChart(processes);
        }
    }



