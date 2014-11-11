package utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImportedSound {
	
	private final DoubleProperty soundSeek = new SimpleDoubleProperty();
	private final DoubleProperty soundOffset = new SimpleDoubleProperty();
	private final DoubleProperty soundLength = new SimpleDoubleProperty();
	private final StringProperty soundName = new SimpleStringProperty();
	private final StringProperty soundPath = new SimpleStringProperty();
	private final BooleanProperty soundEnable = new SimpleBooleanProperty();
	public DoubleProperty getSoundSeek() {
		return soundSeek;
	}
	public DoubleProperty soundOffsetProperty() {
		return soundOffset;
	}
	public DoubleProperty soundLengthProperty() {
		return soundLength;
	}
	public StringProperty soundNameProperty() {
		return soundName;
	}
	public StringProperty soundPathProperty() {
		return soundPath;
	}
	public BooleanProperty soundEnableProperty() {
		return soundEnable;
	}
}
