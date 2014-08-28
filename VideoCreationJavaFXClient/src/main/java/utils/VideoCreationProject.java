package utils;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VideoCreationProject implements Serializable {
	
	StringProperty content = new SimpleStringProperty();

	public StringProperty contentProperty() {
		return content;
	}
	
	public String getContent() {
		return content.get();
	}

	public void setContent(StringProperty content) {
		this.content = content;
	}

}
