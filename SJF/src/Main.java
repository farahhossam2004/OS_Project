import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("./stylesheet.css").toExternalForm();
        
        scene.getStylesheets().add(css);

        stage.setTitle("Shortest Job First Program");
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}  




