package applicationFX;
	
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application implements javafx.fxml.Initializable {

	@FXML
	ImageView view_0;
	
	@FXML
	Slider cursorFrame1;
	
	@FXML
	Label frameNumber;
	
	@Override
	public void start(Stage primaryStage) {
		
//		cursorFrame1.valueProperty().addListener(new ChangeListener<Number>() {
//	        public void changed(ObservableValue<? extends Number> ov,
//	            Number old_val, Number new_val) {
//	        	frameNumber.setText("" + new_val);
//	        }
//	    });

		
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("VCGUI.fxml"));
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
		System.out.println("init");
		
		 view_0.setImage(new Image("file:///home/autor/git/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/frames_WakeApp/image2_00042.png"));
	}

}
