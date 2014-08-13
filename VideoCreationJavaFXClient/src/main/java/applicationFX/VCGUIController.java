package applicationFX;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VCGUIController {
	
	
	@FXML
	private Label frameNumber;
	@FXML
	private Slider cursorFrame1;
	@FXML
	private ImageView view_0;
	@FXML
	private Button play;
	@FXML
	private Button stop;
	
	private double f;
	private Image nouvelle ;
	boolean run = true;

	ViewService viewService = new ViewService(this);
	
	
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

	
}
