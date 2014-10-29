package applicationFX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.collections.ObservableList;
import utils.ImportedMedia;

import com.apptamin.model.Action;
import com.apptamin.model.History;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

public class JsonMediaGenerator {
	
	private VCGUIController uiController;
	private ObservableList<ImportedMedia> mediaArray;
	private ObservableList<Action> actionArray;
	private ObservableList<History> historyArray;
	
	public JsonMediaGenerator(VCGUIController vcguiController) {
		this.uiController = vcguiController;
		this.mediaArray = uiController.getMediaArray();
		this.actionArray = uiController.getActionArray();
		this.historyArray = uiController.getHistoryArray();
	}
	
	public void generator(File file){
		
		JsonGenerator jsonGenerator;
		try {
			jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream(file));
			//for pretty printing
			jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
			jsonGenerator.writeStartObject(); // start root object
	        
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
	        
	        jsonGenerator.writeArrayFieldStart("history"); //start history array
	        for(History historyToParse : this.historyArray){
	        	jsonGenerator.writeStartObject(); //start
	        	
	        	jsonGenerator.writeEndObject();
	        }
	        jsonGenerator.writeEndArray(); //closing history array
	         
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

}
