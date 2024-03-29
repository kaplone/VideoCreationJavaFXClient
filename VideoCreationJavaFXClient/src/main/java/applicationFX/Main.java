package applicationFX;
	
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application implements javafx.fxml.Initializable {

	@Override
	public void start(Stage primaryStage) {
		
		

		try {
			VBox root = FXMLLoader.load(getClass().getResource("VCGUI.fxml"));
			Scene scene = new Scene(root,1000,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

}
