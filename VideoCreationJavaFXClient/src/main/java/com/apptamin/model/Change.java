package com.apptamin.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Change {
	
	private final StringProperty changeFieldName = new SimpleStringProperty();
	private final ObjectProperty<Object> changeOldValue = new SimpleObjectProperty<>();
	
	public StringProperty changeFieldNameProperty() {
		return changeFieldName;
	}

	public ObjectProperty<Object> changeOldValueProperty() {
		return changeOldValue;
	}

}
