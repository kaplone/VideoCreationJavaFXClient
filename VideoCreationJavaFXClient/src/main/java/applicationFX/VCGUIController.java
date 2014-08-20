package applicationFX;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.jcodec.api.JCodecException;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class VCGUIController implements Initializable{
	
	// ------ container -----
	@FXML
	 private BorderPane root;
	    
	
	// ------ timeline ------
	@FXML
	private Slider cursorFrame1;
	
    // ------ player -------
    @FXML
	private ImageView view_0;
    @FXML
	private Label frameNumber;
	@FXML
	private Button play;
	@FXML
	private Button stop;
	
	
	// ------ menu --------
	@FXML
	private MenuItem menuOpenProject;
	
	// ----- medias -------
	@FXML
	private ListView<String> medias;
	@FXML
	private VBox box1;
	
	
	
	boolean run = true;
	
	Line verticale = new Line();
	Line horizontale = new Line();
	Line [] lines = new Line [] {verticale, horizontale};

	ViewService viewService = new ViewService(this);
	
	boolean added = false;
	
	SimpleDoubleProperty fNumber = new SimpleDoubleProperty();
	
	
	protected void initBinds(){
		
		StringBinding strFrame = new StringBinding() {
			{
				super.bind(cursorFrame1.valueProperty());
			}
			
			@Override
			protected String computeValue() {
				return "" + cursorFrame1.valueProperty().intValue();
			}
		};
		
		ObjectBinding<Image> imgFrame = new ObjectBinding<Image>() {
			{
				super.bind(cursorFrame1.valueProperty());
			}
			
			@Override
			protected Image computeValue() {
				//return new Image(String.format("file:///home/david/git/VideoCreationJavaFXClient/VideoCreationJavaFXClient/images/frames_WakeApp/image2_%05d.png", cursorFrame1.valueProperty().intValue()));
				return new Image(String.format("file:///home/david/Bureau/img/frame_%08d.png", cursorFrame1.valueProperty().intValue()));
			}
		};
		
		frameNumber.textProperty().unbind();
		frameNumber.textProperty().bind(strFrame);
		
		view_0.imageProperty().unbind();
		view_0.imageProperty().bind(imgFrame);
	}


	protected void initModels(){
		
		
		
	}
	
	@FXML
	 protected void OnPlayClicked() {
        run = true;
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
	
	@FXML
	protected void OnMenuOpenProject () {
		final Stage stage = new Stage();
		stage.setTitle("Open a project");
		final FileChooser menuOpenFile = new FileChooser();
		File file = menuOpenFile.showOpenDialog(stage);
		loadProject(file);
	}


	private void loadProject(File file) {
		
	}
	
	@FXML
	private void importMediaRequest(DragEvent event){
		
	}
		
	@FXML
	private void importMedia(DragEvent event){
		
	}
	
	@FXML
	private void importMediaDrop(DragEvent event){
		
		Dragboard db = event.getDragboard();
		boolean success = false;
		
		System.out.println(event.getEventType());
		
		int duration = 0;
		
        if (db.hasFiles()) {
            success = true;
            String filePath = null;
            for (File file:db.getFiles()) {
                filePath = file.getAbsolutePath();
                System.out.println(filePath);
                try {
					duration = Splitter.split(file);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JCodecException e) {
					e.printStackTrace();
				}
            }	
        }
        System.out.println(duration);
        event.setDropCompleted(success);
        event.consume();
		
	}
	

	@FXML
	private void importMediaDone(DragEvent event){
		
		System.out.println("done");
	}
	

	@FXML
	private void importMediaDetec(DragEvent event){

		System.out.println("detect");
	}
	
	@FXML
	private void importMediaEnter(DragEvent event){

		System.out.println("enter");;
	}
	
	@FXML
	private void importMediaExit(DragEvent event){

		System.out.println("exit");
	}
	
	@FXML
	private void importMediaOver(DragEvent event){
		
        Dragboard db = event.getDragboard();
		
		if (db.hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
        } else {
            event.consume();
        }
		
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
		return cursorFrame1.getValue();
	}

	public boolean isRun() {
		return run;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initBinds();
		initModels();
		
	}

	
}
