package utils;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

import org.jcodec.api.JCodecException;

import com.fasterxml.jackson.databind.JsonNode;

import applicationFX.VCGUIController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImportedMedia extends Observable {
	
	private final StringProperty name = new SimpleStringProperty();
	private final ObjectProperty<File> original = new SimpleObjectProperty<File>();
	private final ObjectProperty<File> mediaPngPath = new SimpleObjectProperty<File>();
	private final DoubleProperty width = new SimpleDoubleProperty();
	private final DoubleProperty height = new SimpleDoubleProperty();
	private final BooleanProperty landscape = new SimpleBooleanProperty();
	private final IntegerProperty duration = new SimpleIntegerProperty();
	private final IntegerProperty position = new SimpleIntegerProperty();
	private final IntegerProperty rotation = new SimpleIntegerProperty();
	private final IntegerProperty cutIn = new SimpleIntegerProperty();
	private final IntegerProperty cutOut = new SimpleIntegerProperty();
	private final ObjectProperty<TimeLine> timeline = new SimpleObjectProperty<TimeLine>();
	
	public ImportedMedia() {
	}
	
	public ImportedMedia(File mediaPath) throws IOException, JCodecException{
		
		this.original.set(mediaPath);
		this.name.set(mediaPath.getName());
		this.mediaPngPath.set(new File(VCGUIController.getBaseDirMedias().toString(), this.name.get().toString())); 
		new File(mediaPngPath.get().toString()).mkdirs();
		this.duration.set(Splitter.split(mediaPath, this.mediaPngPath.get()));
		this.width.set(Splitter.getWidth());
		this.height.set(Splitter.getHeight());
		this.landscape.set(this.width.get() > this.height.get());
		this.position.set(0);
		this.rotation.set(0);
		this.cutIn.set(0);
		this.cutOut.set(0);
	}
	
public ImportedMedia(JsonNode jsonSave) throws IOException, JCodecException{	

		this.original.set(new File(jsonSave.get("original").asText()));
		this.name.set(jsonSave.get("name").asText());
		this.mediaPngPath.set(new File(jsonSave.get("mediaPngPath").asText())); 
		this.duration.set(jsonSave.get("duration").asInt());
		this.position.set(jsonSave.get("position").asInt());
		this.rotation.set(jsonSave.get("rotation").asInt());
		this.width.set(jsonSave.get("width").asDouble());
		this.height.set(jsonSave.get("height").asDouble());
		this.landscape.set(jsonSave.get("landscape").asBoolean());
		this.cutIn.set(jsonSave.get("cutIn").asInt());
		this.cutOut.set(jsonSave.get("cutOut").asInt());
		this.timeline.set(new TimeLine(jsonSave.get("timeline")));
		
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
	
	public BooleanProperty landscapeProperty() {
		return landscape;
	}
	
	public DoubleProperty widthProperty() {
		return width;
	}
	
	public DoubleProperty heightProperty() {
		return height;
	}
	

	public int getRotation() {
		return rotation.get();
	}

	public void setRotation(int rotation) {
		this.rotation.set(rotation + this.getRotation());
	}
	
	public int getCutIn() {
		return cutIn.get();
	}

	public void setCutIn(int cutIn) {
		this.cutIn.set(cutIn);
	}
	
	public int getCutOut() {
		return cutOut.get();
	}

	public void setCutOut(int cutOut) {
		this.cutOut.set(cutOut);
	}
	
	public int getDuration() {
		return duration.get();
	}
	
	public double getWidth() {
		return width.get();
	}
	
	public double getHeight() {
		return height.get();
	}
	
	public boolean getLandscape() {
		return landscape.get();
	}
	
	
	
	
}
