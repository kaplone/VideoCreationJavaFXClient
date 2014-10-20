package com.apptamin.model;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import com.apptamin.model.Marker;

public class MarkerMove extends Marker {

	public MarkerMove(String text) {
		super(text);
		super.getMarker().setFont(new Font("Lucida Sans", 23));
		super.getMarker().setLayoutY(23);
	}
	

}
