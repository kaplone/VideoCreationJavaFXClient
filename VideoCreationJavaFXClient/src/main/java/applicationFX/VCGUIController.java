package applicationFX;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import utils.ImportedMedia;
import utils.ParseMedias;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
import javafx.util.Callback;


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
	private ListView<ImportedMedia> medias;
	
	private ObservableList<ImportedMedia> mediaArray = FXCollections.observableArrayList();
	
	private ImportedMedia currentMedia;
	
	
	@FXML
	private VBox box1;
	
	private Line verticale = new Line();
	private Line horizontale = new Line();
	private Line [] lines = new Line [] {verticale, horizontale};

	private ViewService viewService = new ViewService(this);
	
	private boolean added = false;
	private boolean run = true;
	
	private DoubleProperty fNumber = new SimpleDoubleProperty();
	
	private static ObjectProperty<File> baseDir = new SimpleObjectProperty<File>();
	
	public static ObjectProperty<File> getBaseDir() {
		return baseDir;
	}


	public void setBaseDir(ObjectProperty<File> baseDir) {
		this.baseDir = baseDir;
	}
	
	ObjectBinding<Double> currentPosition;
	ObjectBinding<Image> imgFrame ;


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
		
		imgFrame = new ObjectBinding<Image>() {
			{
				super.bind(cursorFrame1.valueProperty());
			}
			
			@Override
			protected Image computeValue() {
				if (currentMedia != null){
					return new Image("file://" + currentMedia.mediaPngPathProperty().get().toString()
							                   + File.separator
							                   +  String.format("frame_%08d.png", cursorFrame1.valueProperty().intValue()));
				}
				else return null;
			}
		};
		
		currentPosition = new ObjectBinding<Double>() {
			{
				super.bind(cursorFrame1.valueProperty());
			}
			
			@Override
			protected Double computeValue() {
			    return cursorFrame1.valueProperty().get();
			}
				
		};
		
		
		
		frameNumber.textProperty().unbind();
		frameNumber.textProperty().bind(strFrame);
		
		view_0.imageProperty().unbind();
		view_0.imageProperty().bind(imgFrame);
	}


	protected void initModels(){	
		
	}
	
	protected void initPrefs(){
		String path = System.getProperty("user.home") + File.separator + "VideoCreation" + File.separator + "medias";
	    baseDir.set(new File(path));
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
	private void importMediaDrop(DragEvent event){
		
		mediaArray.addAll(ParseMedias.ParseEvent(event));
		medias.setItems(mediaArray);
		medias.setCellFactory(new Callback<ListView<ImportedMedia>, ListCell<ImportedMedia>>() {
			@Override
			public ListCell<ImportedMedia> call(ListView<ImportedMedia> param) {
				ListCell<ImportedMedia> cell = new ListCell<ImportedMedia>(){
					@Override
					public void updateItem(ImportedMedia item, boolean empty){
						super.updateItem(item, empty);
						if (item != null){
							setText(item.nameProperty().get());
						}
					}
				};
				return cell;
			}
		});
		
		if (mediaArray.size() == 1){
			 currentMedia = mediaArray.get(0);
		     cursorFrame1.setMax(currentMedia.durationProperty().get());
		     cursorFrame1.setValue(0);
		     
			 currentMedia.positionProperty().bind(currentPosition);
		}
		
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
	
	@FXML
	private void mediaSelect(){
		currentMedia.positionProperty().unbind();
		currentMedia = medias.getSelectionModel().getSelectedItem();
		if (currentMedia != null){
			cursorFrame1.setMax(currentMedia.durationProperty().get());
			cursorFrame1.setValue(currentMedia.getPosition());
			
			view_0.imageProperty().unbind();
			view_0.setImage(new Image("file://" + currentMedia.mediaPngPathProperty().get().toString()
                                                + File.separator
                                                + String.format("frame_%08d.png", cursorFrame1.valueProperty().intValue())));
			view_0.imageProperty().bind(imgFrame);
			
			currentMedia.positionProperty().bind(currentPosition);
			
			
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
		initPrefs();
		
	}
}
