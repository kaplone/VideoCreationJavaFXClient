package applicationFX;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.apptamin.client.HttpClient;
import com.apptamin.client.Point;
import com.apptamin.common.ActionType;
import com.apptamin.common.DeviceChoices;
import com.apptamin.common.PositionChoices;
import com.apptamin.common.PostAction;
import com.apptamin.common.PreAction;
import com.apptamin.common.TapChoices;
import com.apptamin.model.Action;
import com.apptamin.model.History;
import com.apptamin.model.Marker;
import com.apptamin.model.MarkerAction;
import com.apptamin.model.MarkerCut;
import com.apptamin.model.Project;
import com.apptamin.model.RectangleSelectionOff;
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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class VCGUIController implements Initializable{
	
	// ------ container -----
	@FXML
	 private BorderPane root;
	
	@FXML
	private Pane pane_1;
	
	@FXML
	private Accordion accordion;
	@FXML
	private TitledPane startPane;
	@FXML
	private TitledPane mediasPane;
	@FXML
	private TitledPane sequencePane;
	@FXML
	private TitledPane settingsPane;
	
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
	@FXML
	private Button buttonGenerate;
	
	// ------ actions -----
	@FXML
	private Button addActionButton;
	@FXML
	private ChoiceBox<ActionType> actionTypeList;
	@FXML
	private ChoiceBox<PreAction> preActionList;
	@FXML
	private ChoiceBox<PostAction> postActionList;
	
	@FXML
	private Button newProjectButton;
	@FXML
	private Button openProjectButton;
	
	
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
	private final ObservableList<Action> actionArray = FXCollections.observableArrayList();
	private final ObservableList<Integer> touchList = FXCollections.observableArrayList();
	private final ObservableList<History> historyArray = FXCollections.observableArrayList();
	
	private ImportedMedia currentMedia;

	@FXML
	private VBox box1;
	
	// ----- settings pane -------
	
	@FXML
	private ChoiceBox tapChoiceBox;
	@FXML
	private ChoiceBox deviceChoiceBox;
	@FXML
	private ChoiceBox positionChoiceBox;
	@FXML
	private Accordion accordionSettings;
	@FXML
	private Button getStartedButton;
	@FXML
	private TitledPane devicePane;
	@FXML
	private TitledPane tapPane;
	@FXML
	private TitledPane fontPane;
	
	// ----- sequence pane -------
	
	@FXML
	private Accordion accordionOptional;
	@FXML
	private Rectangle zone;
	@FXML
	private Rectangle zone2;
	@FXML
	private TitledPane phoneAndTextPane;
	
	//final vars
	
	final Stage stage = new Stage();
	
	final private ObjectProperty<Project> projet = new SimpleObjectProperty<Project>(new Project());
	
	final private Line verticale = new Line();
	final private Line horizontale = new Line();
	final private Line [] lines = new Line [] {verticale, horizontale};
	
	final private Circle touchCircle = new Circle();

	final private ViewService viewService = new ViewService(this);
	
	final private JsonMediaGenerator mediaGenerator = new JsonMediaGenerator(this);
	
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
	
	
	final private RectangleSelectionOff offIn = new RectangleSelectionOff(0, true);
	final private RectangleSelectionOff offOut = new RectangleSelectionOff(0, false);
	
	// non final vars
	
	private boolean added = false;
	private boolean run = true;
	private EventHandler<Event> getEvent;
	private EventHandler<Event> changeLabel;
	private EventHandler<Event> updateProject ;
	
	
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
		ObservableList<TapChoices> tapChoices= FXCollections.observableArrayList(TapChoices.values());
		tapChoiceBox.setItems(tapChoices);
		tapChoiceBox.getSelectionModel().select(0);
		ObservableList<PositionChoices> positionChoices= FXCollections.observableArrayList(PositionChoices.values());
		positionChoiceBox.setItems(positionChoices);
		positionChoiceBox.getSelectionModel().select(0);
		ObservableList<DeviceChoices> deviceChoices= FXCollections.observableArrayList(DeviceChoices.values());
		deviceChoiceBox.setItems(deviceChoices);
		deviceChoiceBox.getSelectionModel().select(0);
		
		accordion.setExpandedPane(startPane);
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
    	
    	mediaGenerator.generator(file);
    	
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
    public void onGetStartedButtonAction(){
    	accordion.setExpandedPane(sequencePane);
    }
    
    @FXML
    public void onAddActionButton(){
    	
    	 
    	 markerTouch = new MarkerAction("°");
    	 // ajouter un ecouteur sur la marque -> modification
    	 
    	 changeLabel = new EventHandler<Event>() {

 			@Override
 			public void handle(Event event) {
 				//markerTouch.getMarker().removeEventHandler(Event.ANY, getEvent);
 				addActionButton.setText("Save changed Action");	
 			}
 		};
    	 
    	 getEvent = new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				addActionButton.setText("Edit Action");	
				actionTypeList.addEventHandler(MouseEvent.MOUSE_CLICKED, changeLabel);
				addActionButton.setOnMouseClicked(updateProject);
			}
		};
		
		updateProject = new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				addActionButton.setText("Edit Action");	
				System.out.println("mise à jour du projet");
				
			}
		};
	
    	 markerTouch.getMarker().addEventHandler(MouseEvent.MOUSE_CLICKED, getEvent);
    	 
    	 // attacher à un seul media
    	 
    	 touchList.add((int) cursorFrame1.getValue());
    	 markerTouch.setActionProperties(touchCircle.getCenterX(), touchCircle.getCenterY(),(int) cursorFrame1.getValue());
         anchorPane1.getChildren().add(markerTouch.getMarker());
         markerTouch.setPosition(((cursorFrame1.getWidth() -16) / cursorFrame1.getMax()) * Math.floor(cursorFrame1.getValue()));
         markerTouch.getMarker().setVisible(true);
         actionArray.add(new Action(new Point(touchCircle.getCenterX(), touchCircle.getCenterY(), null, (int) cursorFrame1.getValue()), actionTypeList.getValue(), preActionList.getValue(), postActionList.getValue()));
    	
    }
    
    @FXML
    public void onButtonGenerate(){
    	
    	try {
			HttpClient.httpClient(actionArray.toArray(new Action [0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	 
    
    protected void onAddActionButtonMod(){
    	System.out.println("mod");
    }
    
    @FXML
    public void onPreActionListSelect(){
    	
    }
    
    @FXML
    protected void onNewProjectButtonACtion(){
    	accordion.setExpandedPane(settingsPane);
    	accordionSettings.setExpandedPane(devicePane);
    }

    @FXML
    protected void onZoneAction(){
    	accordionOptional.setExpandedPane(phoneAndTextPane);
    	accordionOptional.setVisible(true);
    }
    @FXML
    protected void onZone2Action(){
    	accordionOptional.setVisible(false);
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
	

	public ObjectProperty<Project> projectProperty() {
		return projet;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initBinds();
		initModels();
		initChoices();
		initPrefs();
		
	}
}
