import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class CalculationController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ChoiceBox<Integer> processChoiceBox;

    @FXML
    Label responseLabel;

    @FXML
    Label turnaroundLabel;

    @FXML
    Label waitingLabel;

    @FXML
    private Label avgResponse;

    @FXML
    private Label avgTurnaround;

    @FXML
    private Label avgWaitingLabel;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        for(Process process : ProcessManagement.getAllProcesses())
        {
            processChoiceBox.getItems().add(process.getID());
        }
        avgWaitingLabel.setText(String.valueOf(Math.round(ProcessManagement.calculateAverageWaiting() * 100.0) / 100.0));
        avgTurnaround.setText(String.valueOf(Math.round(ProcessManagement.calculateAverageTurnaroundTime() * 100.0) / 100.0));
        avgResponse.setText(String.valueOf(Math.round(ProcessManagement.calculateAverageResponseTime() * 100.0) / 100.0));
        processChoiceBox.setOnAction(this::calculateTime);
    }


    public void calculateTime(ActionEvent e)
    {
        int processID = processChoiceBox.getValue();
        responseLabel.setText(String.valueOf(ProcessManagement.responseTime(processID)));
        turnaroundLabel.setText(String.valueOf(ProcessManagement.turnaroundtime(processID)));
        waitingLabel.setText(String.valueOf(ProcessManagement.waitingTime(processID)));

    }
    public void ShowGanttChart (ActionEvent e){
        Platform.runLater(() -> {
        try {
            Stage stage = new Stage(); // Create a new stage
            SJFGanttChart ganttChart = new SJFGanttChart(); // Assuming TestGanttChart can be instantiated like this
            ganttChart.start(stage); // Start the Gantt chart on this new stage
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });
    }

    public void BackToScene(ActionEvent e) throws IOException
    {
        ProcessManagement.getAllProcesses().clear();
        ProcessManagement.getServedProcesses().clear();
        Process.setProcessCounter(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./Home.fxml"));
        root = loader.load();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

