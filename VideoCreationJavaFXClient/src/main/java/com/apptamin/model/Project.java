package com.apptamin.model;

import java.net.MalformedURLException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Project {
	
	private final StringProperty projectName;	
	private final ObjectProperty<ProjectHistory> history_undo;
	private final ObjectProperty<ProjectSettings> settings;
	private final ObservableList<ProjectScene> scenes;

	public StringProperty projectNameProperty() {
		return projectName;
	}
	public ObjectProperty<ProjectHistory> history_undoProperty() {
		return history_undo;
	}
	public ObjectProperty<ProjectSettings> settingsProperty() {
		return settings;
	}
	public ObservableList<ProjectScene> scenesProperty() {
		return scenes ;
	}
	
	public Project(){
		this.projectName = new SimpleStringProperty();	
		this.history_undo = new SimpleObjectProperty<ProjectHistory>(new ProjectHistory());
		this.settings = new SimpleObjectProperty<ProjectSettings>(new ProjectSettings());
		this.scenes = FXCollections.observableArrayList();
	}

	
	

}
