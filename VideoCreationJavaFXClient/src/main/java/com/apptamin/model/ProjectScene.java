package com.apptamin.model;

import utils.ImportedMedia;
import utils.ImportedSound;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectScene {
	
	private final ObjectProperty<DevicePosition> devicePosition = new SimpleObjectProperty<DevicePosition>();
	private final ObservableList<ImportedMedia> medias = FXCollections.observableArrayList();
	private final ObservableList<ImportedSound> sounds = FXCollections.observableArrayList();
	private final ObjectProperty<SceneTransition> sceneTransition = new SimpleObjectProperty<SceneTransition>();
	private final ObjectProperty<SceneText> sceneLabel = new SimpleObjectProperty<SceneText>();

	
	public ObjectProperty<DevicePosition> devicePositionProperty() {
		return devicePosition;
	}
	public ObservableList<ImportedMedia> mediasProperty() {
		return medias;
	}
	public ObservableList<ImportedSound> soundsProperty() {
		return sounds;
	}
	public ObjectProperty<SceneTransition> sceneTransitionProperty() {
		return sceneTransition;
	}
	public ObjectProperty<SceneText> sceneLabelProperty() {
		return sceneLabel;
	}
}
