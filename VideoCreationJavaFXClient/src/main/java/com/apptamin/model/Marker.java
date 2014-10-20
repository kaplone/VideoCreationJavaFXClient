package com.apptamin.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.text.Text;

public class Marker extends Text {

	private final DoubleProperty position;
	private final ObjectProperty<Text> marker;
	
	
	// problème de constructeur
	public Marker(String text) {
		this.marker = new SimpleObjectProperty<Text>(new Text(text));
		this.position = new SimpleDoubleProperty(0);
	}
	
	public DoubleProperty positionProperty() {
		return position;
	}
	public ObjectProperty<Text> markerProperty() {
		return marker;
	}
	
	public Double getPosition() {
		return position.get();
	}
	public Text getMarker() {
		return marker.get();
	}
	
	public void setPosition(double p) {
		position.set(p);
		marker.get().setX(p);
	}
	
	
	

}
