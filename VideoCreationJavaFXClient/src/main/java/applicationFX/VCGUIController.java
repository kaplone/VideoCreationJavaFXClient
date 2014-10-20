package applicationFX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.apptamin.common.ActionType;
import com.apptamin.model.Marker;
import com.apptamin.model.MarkerAction;
import com.apptamin.model.MarkerCut;
import com.apptamin.model.MarkerMove;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.ImportedMedia;
import utils.JsonUtils;
import utils.ListCellUtils;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class VCGUIController implements Initializable{
	
	// ------ container -----
	@FXML
	 private BorderPane root;
	
	@FXML
	private Pane pane_1;
	
	// ------ timeline ------
	@FXML
	private Slider cursorFrame1;
	@FXML
	private Rectangle selectedZone;
	@FXML
	private AnchorPane anchorPane1;
	@FXML
	private Button inButton;
	@FXML
	private Button outButton;
	
	// ------ actions -----
	@FXML
	private Button addActionButton;
	@FXML
	private ChoiceBox<ActionType> actionTypeList;
	@FXML
	private ChoiceBox<PreAction> preActionList;
	@FXML
	private ChoiceBox<PostAction> postActionList;
	
	
    // ------ player -------
    @FXML
	private ImageView view_0;
    @FXML
	private Label frameNumber;
	@FXML
	private Button play;
	@FXML
	private Button stop;
	@FXML
	private Button rotate_left;
	@FXML
	private Button rotate_right;
	
	
	// ------ menu --------
	
	// ----- medias -------
	@FXML
	private ListView<ImportedMedia> medias;
	
	private ObservableList<ImportedMedia> mediaArray = FXCollections.observableArrayList();
	
	private ImportedMedia currentMedia;

	@FXML
	private VBox box1;
	
	//final vars
	
	final Stage stage = new Stage();
	
	final private Line verticale = new Line();
	final private Line horizontale = new Line();
	final private Line [] lines = new Line [] {verticale, horizontale};
	
	final private Circle touchCircle = new Circle();

	final private ViewService viewService = new ViewService(this);
	
	final private DoubleProperty fNumber = new SimpleDoubleProperty();
	
	final private static ObjectProperty<File> baseDirMedias = new SimpleObjectProperty<File>();
	
	final private static ObjectProperty<File> baseDirSaves = new SimpleObjectProperty<File>();
	
	final private ObjectProperty<Image> displayedImage = new SimpleObjectProperty<Image>();
	
	final private ObjectMapper mapper = new ObjectMapper();
	
	final private Marker markerIn = new MarkerCut("[");
	final private Marker markerOut = new MarkerCut("]");
	//final private Marker markerUp = new MarkerMove("↑");
	//final private Marker markerDown = new MarkerMove("↓");
	private MarkerAction markerTouch;
	ObservableList<Integer> touchList = FXCollections.observableArrayList();
	
	final private RectangleSelectionOff offIn = new RectangleSelectionOff(0, true);
	final private RectangleSelectionOff offOut = new RectangleSelectionOff(0, false);
	
	// non final vars
	
	private boolean added = false;
	private boolean run = true;
	
	
	// getters - setters
	
	public static ObjectProperty<File> baseDirMediasProperty() {
		return baseDirMedias;
	}
	
	public static File getBaseDirMedias() {
		return baseDirMedias.get();
	}


	public void setBaseDirMedias(File baseDirMedias) {
		this.baseDirMedias.set(baseDirMedias);
	}
	
	public static ObjectProperty<File> baseDirSavesProperty() {
		return baseDirSaves;
	}
	
	public static File getBaseDirSaves() {
		return baseDirSaves.get();
	}


	public void setBaseDirSaves(File baseDirSaves) {
		this.baseDirSaves.set(baseDirSaves);
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
		anchorPane1.getChildren().add(offIn.getZone());
		anchorPane1.getChildren().add(offOut.getZone());	
	}
	
	protected void initChoices(){
		ObservableList<ActionType> actions= FXCollections.observableArrayList(ActionType.values());
		actionTypeList.setItems(actions);
		actionTypeList.getSelectionModel().select(0);
		ObservableList<PreAction> preActions= FXCollections.observableArrayList(PreAction.values());
		preActionList.setItems(preActions);
		preActionList.getSelectionModel().select(0);
		ObservableList<PostAction> postActions= FXCollections.observableArrayList(PostAction.values());
		postActionList.setItems(postActions);
		postActionList.getSelectionModel().select(0);
	}
	
	protected void initPrefs(){
		String pathMedias = System.getProperty("user.home") + File.separator + "VideoCreation" + File.separator + "medias";
	    baseDirMedias.set(new File(pathMedias));
	    String pathSaves = System.getProperty("user.home") + File.separator + "VideoCreation" + File.separator + "saves";
	    baseDirSaves.set(new File(pathSaves));
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
	 protected void OnMouseClickedFrame(MouseEvent event) {
		
		System.out.println(view_0.getLayoutX());
		System.out.println(view_0.getLayoutY());
		System.out.println(view_0.getX());
		System.out.println(view_0.getY());
		
		if (currentMedia.getLandscape()){
			
			if (currentMedia.getRotation() % 180 == 0){
				System.out.println("horizontal" + currentMedia.getRotation() + " " +  event.getX()+ "   " + ( event.getY()  - view_0.getLayoutY()));
			}
			else{
			   System.out.println("vertical " + currentMedia.getRotation() + " "+  (event.getX() - view_0.getLayoutY())+ "   " +  event.getY());
			}
		}
		else {
			if (currentMedia.getRotation() % 180 != 0){
				System.out.println("vertical " + currentMedia.getRotation() + " "+  event.getX()+ "   " +  (event.getY() - view_0.getLayoutX()));
			}
			else{
				System.out.println( "horizontal" + currentMedia.getRotation() + " " +  (event.getX() - view_0.getLayoutX())+ "   " + event.getY());
			   
			}
		}
		
        touchCircle.setRadius(50);
        touchCircle.setStrokeWidth(5);
        touchCircle.setStroke(new Color(0.95,0.05,0.05,0.8));
        touchCircle.setFill(new Color(0.95,0.05,0.05,0.0));
      
		
		if (! added){
		     pane_1.getChildren().add(touchCircle);
		     added = true;
		}
		
		touchCircle.setVisible(true);
		touchCircle.setCenterX(event.getX());
        touchCircle.setCenterY(event.getY());	
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
	private void importMediaDrop(DragEvent event){
		
		mediaArray.addAll(ParseMedias.ParseEvent(event));
		ListCellUtils.populateMediasCells(mediaArray, medias);
		if (medias.getSelectionModel().isEmpty()){
			medias.getSelectionModel().select(0);
		}
		currentMedia = medias.getSelectionModel().getSelectedItem();
		currentMedia.positionProperty().bind(currentPosition);
		
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
			
			view_0.setRotate(currentMedia.getRotation());
			
			view_0.imageProperty().bind(imgFrame);
			
			currentMedia.positionProperty().bind(currentPosition);	
		}
		if (currentMedia.getRotation() % 180 != 0){
		}
	}
	
	@FXML
	private void onLeftRotation(){
		currentMedia.setRotation(-90);
		view_0.setRotate(currentMedia.getRotation());
	}
	
	@FXML
	private void onRightRotation(){
		currentMedia.setRotation(90);
		view_0.setRotate(currentMedia.getRotation());

	}
	
	@FXML
	private void OnNewProjectMenuItem(){
		final FileChooser newProject = new FileChooser();
		newProject.setInitialDirectory(getBaseDirSaves());
		newProject.setInitialFileName("newProject.vcg");
		newProject.setTitle("New Video Creation Project");
		//File file = newProject.showOpenDialog(stage);
	}
	

	@FXML
	private void OnSaveProjectMenuItem(){
		final FileChooser newProject = new FileChooser();
		newProject.setInitialDirectory(getBaseDirSaves());
		newProject.setInitialFileName("newProject.vcg");
		newProject.setTitle("Save Video Creation Project");
		File file = newProject.showSaveDialog(stage);
		
		saveProjectAs(file);
	}
	
	@FXML
	private void OnSaveAsProjectMenuItem(){
		final FileChooser newProject = new FileChooser();
		newProject.setInitialDirectory(getBaseDirSaves());
		newProject.setInitialFileName("newProject.vcg");
		newProject.setTitle("Save Video Creation Project As ...");
		File file = newProject.showSaveDialog(stage);
		
		saveProjectAs(file);
	}
	
	
	@FXML
	protected void OnOpenProjectMenuItem() {
		stage.setTitle("Open a Video Creation Project");
		
		final FileChooser menuOpenFile = new FileChooser();
		menuOpenFile.setInitialDirectory(getBaseDirSaves());
		File file = menuOpenFile.showOpenDialog(stage);
		JsonUtils.loadProject(file, mediaArray, medias);
		initCell();
	}
	
	protected void initCell(){
		
		if (mediaArray.size() >= 1){
			 medias.getSelectionModel().select(0);
			 currentMedia = mediaArray.get(0);
		     cursorFrame1.setMax(currentMedia.durationProperty().get());
		     cursorFrame1.setValue(0); 
			 currentMedia.positionProperty().bind(currentPosition);	 
			 view_0.setRotate(currentMedia.getRotation());
		}
	}


    private void saveProjectAs(File file) {
    	
    	JsonGenerator jsonGenerator;
		try {
			jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream(file));
			//for pretty printing
			jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
			jsonGenerator.writeStartObject(); // start root object
	        
	        jsonGenerator.writeArrayFieldStart("medias"); //start medias array
	        for(ImportedMedia mediaToParse : mediaArray){
	        	jsonGenerator.writeStartObject(); //start
	            jsonGenerator.writeNumberField("rotation", mediaToParse.getRotation());
	            jsonGenerator.writeNumberField("position", mediaToParse.getPosition());
	            jsonGenerator.writeNumberField("duration", mediaToParse.getDuration());
	            jsonGenerator.writeNumberField("width", mediaToParse.getWidth());
	            jsonGenerator.writeNumberField("height", mediaToParse.getHeight());
	            jsonGenerator.writeBooleanField("landscape", mediaToParse.getLandscape());
	            jsonGenerator.writeNumberField("cutIn", mediaToParse.getCutIn());
	            jsonGenerator.writeNumberField("CutOut", mediaToParse.getCutOut());
	            jsonGenerator.writeStringField("name", mediaToParse.getName());
	            jsonGenerator.writeStringField("original", mediaToParse.getOriginal().toString());
	            jsonGenerator.writeStringField("mediaPngPath", mediaToParse.getMediaPngPath().toString());
	            jsonGenerator.writeEndObject();
	        }
	        jsonGenerator.writeEndArray(); //closing medias array
	         
	        jsonGenerator.writeEndObject(); //closing root object
	        
	        jsonGenerator.flush();
	        jsonGenerator.close();
	        
	        
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    @FXML
    public void onInButtonClick(){
        markerIn.getMarker().setX((cursorFrame1.getWidth() / cursorFrame1.getMax()) * Math.floor(cursorFrame1.getValue()));
        offIn.setOffX(0, (cursorFrame1.getWidth() / cursorFrame1.getMax()) * Math.floor(cursorFrame1.getValue()));
    }
    
    
    @FXML
    public void onOutButtonClick(){
        markerOut.getMarker().setX((cursorFrame1.getWidth() / cursorFrame1.getMax()) * Math.floor(cursorFrame1.getValue()));
        offOut.setOffX((cursorFrame1.getWidth() / cursorFrame1.getMax()) * Math.floor(cursorFrame1.getValue()),
        		       (cursorFrame1.getWidth() / cursorFrame1.getMax()) * Math.floor(cursorFrame1.getMax() - cursorFrame1.getValue()));
    }
    
    @FXML
    public void onAddActionButton(){
    	 markerTouch = new MarkerAction("°");
    	 // ajouter un ecouteur sur la marque -> modification
    	 
    	 // ajouter l'enregitrement dans le projet
    	 
    	 touchList.add((int) cursorFrame1.getValue());
    	 markerTouch.setActionProperties((int) cursorFrame1.getValue(), touchCircle.getCenterX(), touchCircle.getCenterY());
         anchorPane1.getChildren().add(markerTouch.getMarker());
         markerTouch.setPosition(((cursorFrame1.getWidth() -16) / cursorFrame1.getMax()) * Math.floor(cursorFrame1.getValue()));
         markerTouch.getMarker().setVisible(true);
    	
    }
    
    @FXML
    public void onPreActionListSelect(){
    	
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
		initChoices();
		initPrefs();
		
	}
}
