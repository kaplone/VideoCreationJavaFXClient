package applicationFX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.ImportedMedia;
import utils.ImportedSound;

import com.apptamin.model.Account;
import com.apptamin.model.Action;
import com.apptamin.model.Change;
import com.apptamin.model.History;
import com.apptamin.model.Project;
import com.apptamin.model.ProjectHistory;
import com.apptamin.model.ProjectScene;
import com.apptamin.model.ProjectSettings;
import com.apptamin.model.SceneText;
import com.apptamin.model.SceneTransition;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

public class JsonMediaGenerator {
	
	private final VCGUIController uiController;
	private ObjectProperty<Project> project;
	
	private ObjectProperty<ProjectHistory> historyUndoArray;
	
	private ObjectProperty<ProjectSettings> settings;
	private ObjectProperty<Account> account;
	
	private ObservableList<ProjectScene> scenes;
	private final ObservableList<ImportedMedia> mediaArray = FXCollections.observableArrayList();
	private final ObservableList<Action> actionArray = FXCollections.observableArrayList();
	private final ObservableList<ImportedSound> soundArray = FXCollections.observableArrayList();
	private final ObservableList<SceneTransition> sceneTransition =  FXCollections.observableArrayList();
	private final ObservableList<SceneText> sceneText =  FXCollections.observableArrayList();
	
	
	public JsonMediaGenerator(VCGUIController vcguiController) {
		this.uiController = vcguiController;
		
	}
	
	public void init(){
		
		this.project = uiController.projectProperty();
		this.settings = this.project.get().settingsProperty();
		this.historyUndoArray = this.project.get().history_undoProperty();
		this.scenes = this.project.get().scenesProperty();
		this.account =this.settings.get().accountProperty();
	}
	
	public void generator(File file){
		
		init();
		
		JsonGenerator jsonGenerator;
		try {
			jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream(file));
			//for pretty printing
			jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
			jsonGenerator.writeStartObject(); // start root object
			
			jsonGenerator.writeStringField("projectName", file.getName());
            
			
			
			jsonGenerator.writeObjectFieldStart("settings"); //start settings
				jsonGenerator.writeStringField("device", this.settings.get().deviceProperty().get().toString());
				jsonGenerator.writeStringField("handType", this.settings.get().handTypeProperty().get().toString());
				jsonGenerator.writeStringField("background", this.settings.get().backgroundProperty().get().toString());
				jsonGenerator.writeStringField("fontName", this.settings.get().fontProperty().get().getName());
			
				jsonGenerator.writeObjectFieldStart("account"); //start account
					jsonGenerator.writeStringField("accountURL", "URL TO COME");//this.settings.get().accountProperty().get().serverURLProperty().get().toString());
					jsonGenerator.writeStringField("accountUsername", this.settings.get().accountProperty().get().usernameProperty().get());
					jsonGenerator.writeStringField("accountPass", this.settings.get().accountProperty().get().passProperty().get());
				jsonGenerator.writeEndObject(); // end account
			jsonGenerator.writeEndObject(); // end settings
			
			
			jsonGenerator.writeArrayFieldStart("scenes"); //start scenes array
			for(ProjectScene sceneToParse : this.scenes){
				
				jsonGenerator.writeArrayFieldStart("medias"); //start medias array
		        for(ImportedMedia mediaToParse : this.mediaArray){
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
		            
		            jsonGenerator.writeArrayFieldStart("actions"); //start actions array
			        for(Action actionToParse : this.actionArray){
			        	jsonGenerator.writeStartObject(); //start
			        	jsonGenerator.writeStringField("type", actionToParse.actionTypeProperty().get().toString());
			        	jsonGenerator.writeStringField("pre", actionToParse.preActionProperty().get().toString());
			        	jsonGenerator.writeStringField("post", actionToParse.postActionProperty().get().toString());
			        	jsonGenerator.writeNumberField("x", actionToParse.positionProperty().get().getCoordX());
			        	jsonGenerator.writeNumberField("y", actionToParse.positionProperty().get().getCoordY());
			        	jsonGenerator.writeNumberField("frame", actionToParse.positionProperty().get().getImageNumber());
			        	jsonGenerator.writeEndObject();
			        	
			        }
			        jsonGenerator.writeEndArray(); //closing actions array
		            jsonGenerator.writeEndObject();
		        }
		        jsonGenerator.writeEndArray(); //closing medias array
		        jsonGenerator.writeEndObject();
				
			}
			jsonGenerator.writeEndArray(); //closing scenes array
	        jsonGenerator.writeEndObject();
			
	        
	        
	        
//	        jsonGenerator.writeArrayFieldStart("history"); //start history array
//	        for(History historyToParse : this.historyArray){
//	        	jsonGenerator.writeStartObject(); //start
//	        	
//	        	jsonGenerator.writeEndObject();
//	        }
//	        jsonGenerator.writeEndArray(); //closing history array
//	         
	        //jsonGenerator.writeEndObject(); //closing root object
	        
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

}
