package com.apptamin.model;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import com.apptamin.model.Marker;

public class MarkerCut extends Marker {

	public MarkerCut(String text) {
		super(text);
		super.getMarker().setFont(new Font("Lucida Sans", 30));
		super.getMarker().setFill(Color.web("0x397C94"));
		super.getMarker().setY(25);
		}
	

}
