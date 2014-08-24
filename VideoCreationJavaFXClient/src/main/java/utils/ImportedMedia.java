package utils;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

import org.jcodec.api.JCodecException;

import applicationFX.VCGUIController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImportedMedia extends Observable {
	
	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<File> original = new SimpleObjectProperty<File>();
	private ObjectProperty<File> mediaPngPath = new SimpleObjectProperty<File>();
	private IntegerProperty duration = new SimpleIntegerProperty();
	private IntegerProperty position = new SimpleIntegerProperty();
	
	public ImportedMedia() {
	}
	
	public ImportedMedia(File mediaPath) throws IOException, JCodecException{
		
		this.original.set(mediaPath);
		this.name.set(mediaPath.getName());
		this.mediaPngPath.set(new File(VCGUIController.getBaseDir().get().toString(), this.name.get().toString())); 
		new File(mediaPngPath.get().toString()).mkdirs();
		this.duration.set(Splitter.split(mediaPath, this.mediaPngPath.get()));
		this.position.set(0);
		
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

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public ObjectProperty<File> mediaPathProperty() {
		return mediaPngPath;
	}

	public void setMediaPath(File mediaPath) {
		this.mediaPngPath.set(mediaPath);
	}

	public IntegerProperty durationProperty() {
		return duration;
	}

	public ObjectProperty<File> originalProperty() {
		return original;
	}
}
