package com.apptamin.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import com.apptamin.model.Marker;

public class MarkerAction extends Marker {
	
	private final IntegerProperty frameNo = new SimpleIntegerProperty();
	private final DoubleProperty XPosition = new SimpleDoubleProperty();
	private final DoubleProperty YPosition = new SimpleDoubleProperty();
	

	public MarkerAction(String text) {
		super(text);
		super.getMarker().setFont(new Font("Lucida Sans", 36));
		super.getMarker().setFill(Color.web("0xE10808"));
		super.getMarker().setY(38);
		}
	
	public void setActionProperties(int fno, double x, double y){
		frameNo.set(fno);
		XPosition.set(x);
		YPosition.set(y);
		
	}
	

}
