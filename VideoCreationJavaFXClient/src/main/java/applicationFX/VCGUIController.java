package applicationFX;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class VCGUIController implements Initializable{
	
	
	@FXML
	private Label frameNumber;
	@FXML
	private Slider cursorFrame1;
	@FXML
	private ImageView view_0;
    @FXML
    private BorderPane root;
	@FXML
	private Button play;
	@FXML
	private Button stop;
	
	private double f;
	private Image nouvelle ;
	boolean run = true;
	
	Line verticale = new Line();
	Line horizontale = new Line();
	Line [] lines = new Line [] {verticale, horizontale};

	ViewService viewService = new ViewService(this);
	
	boolean added = false;
	
	
	@FXML
	 protected void CursorChanged() {
		
		f = cursorFrame1.getValue();
		
		System.out.println(f);
		frameNumber.setText("" + (int)f);
		nouvelle = new Image(String.format("file:///home/autor/git/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/frames_WakeApp/image2_%05d.png", (int)f));
		view_0.setImage(nouvelle);
		
	 }
	
	@FXML
	 protected void CursorKeyPressed() {
		
		f = cursorFrame1.getValue();
        System.out.println(f);
		nouvelle = new Image(String.format("file:///home/autor/git/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/frames_WakeApp/image2_%05d.png", (int)f));
		view_0.setImage(nouvelle);
		frameNumber.setText("" + (int)f);
		
	 }
	
	
	@FXML
	 protected void OnPlayClicked() {
        run = true;
		f = cursorFrame1.getValue();
		viewService.restart();
	 }
	
	@FXML
	 protected void OnStopClicked() {
		run = false;
	    }
	
	@FXML
	 protected void OnMouseOverFrame() {
		
		double offsetX = view_0.getLayoutX();
		double offsetY = view_0.getLayoutY();
		
		horizontale.setStartX(offsetX);
		horizontale.setEndX(250 + offsetX);
		horizontale.setStrokeWidth(1);
		horizontale.setStroke(new Color(0,0,0,0.5));
		
		verticale.setStartY(53 + offsetY);
        verticale.setEndY(53 + 446 + offsetY);
        verticale.setStrokeWidth(1);
        verticale.setStroke(new Color(0,0,0,0.5));
		
		if (! added){
		     root.getChildren().addAll(lines);
		     added = true;
		}

	}
	
	@FXML
	 protected void OnMouseClickedFrame(MouseEvent event) {
		
		
	}
	
	
	@FXML
	 protected void OnMouseMovedOverFrame(MouseEvent event) {
		
		double x = event.getSceneX();
		double y = event.getSceneY();

		verticale.setStartX(x);
        verticale.setEndX(x);
        		
		horizontale.setStartY(y);
		horizontale.setEndY(y);
		

	}


	public Label getFrameNumber() {
		return frameNumber;
	}

	public Slider getCursorFrame1() {
		return cursorFrame1;
	}

	public ImageView getView_0() {
		return view_0;
	}

	public double getF() {
		return f;
	}

	public boolean isRun() {
		return run;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	
}
