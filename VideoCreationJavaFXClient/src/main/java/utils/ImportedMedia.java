package utils;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

import org.jcodec.api.JCodecException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import applicationFX.VCGUIController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class ImportedMedia extends Observable {
	
	final private ObjectMapper mapper = new ObjectMapper();
	
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<File> original = new SimpleObjectProperty<File>();
	private ObjectProperty<File> mediaPngPath = new SimpleObjectProperty<File>();
	private IntegerProperty duration = new SimpleIntegerProperty();
	private IntegerProperty position = new SimpleIntegerProperty();
	private IntegerProperty rotation = new SimpleIntegerProperty();
	
	public ImportedMedia() {
	}
	
	public ImportedMedia(File mediaPath) throws IOException, JCodecException{
		
		this.original.set(mediaPath);
		this.name.set(mediaPath.getName());
		this.mediaPngPath.set(new File(VCGUIController.getBaseDirMedias().toString(), this.name.get().toString())); 
		new File(mediaPngPath.get().toString()).mkdirs();
		this.duration.set(Splitter.split(mediaPath, this.mediaPngPath.get()));
		this.position.set(0);
		this.rotation.set(0);
		
	}
	
public ImportedMedia(JsonNode jsonSave) throws IOException, JCodecException{	

		this.original.set(new File(jsonSave.get("original").asText()));
		this.name.set(jsonSave.get("name").asText());
		this.mediaPngPath.set(new File(jsonSave.get("mediaPngPath").asText())); 
		this.duration.set(jsonSave.get("duration").asInt());
		this.position.set(jsonSave.get("position").asInt());
		this.rotation.set(jsonSave.get("rotation").asInt());
		
	}
	
	public IntegerProperty positionProperty() {
		return position;
	}
	
	public int getPosition(){
		return position.get();
	}

	public void setPosition(int position) {
		this.position.set(position);
	}

	public ObjectProperty<File> mediaPngPathProperty() {
		return mediaPngPath;
	}
	
	public File getMediaPngPath() {
		return mediaPngPath.get();
	}


	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getName() {
		return this.name.get();
	}

	public void setMediaPngPath(File mediaPath) {
		this.mediaPngPath.set(mediaPath);
	}

	public IntegerProperty durationProperty() {
		return duration;
	}

	public ObjectProperty<File> originalProperty() {
		return original;
	}
	
	public File getOriginal() {
		return original.get();
	}

	public IntegerProperty rotationProperty() {
		return rotation;
	}

	public int getRotation() {
		return rotation.get();
	}

	public void setRotation(int rotation) {
		this.rotation.set(rotation + this.getRotation());
	}
	
	public int getDuration() {
		return duration.get();
	}
	
	
	
}
