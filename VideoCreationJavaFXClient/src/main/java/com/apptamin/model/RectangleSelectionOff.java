package com.apptamin.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class RectangleSelectionOff extends Rectangle {
	
	private final ObjectProperty<Color> offColor = new SimpleObjectProperty<Color>(Color.web("0xb2bbc3"));
	private final ObjectProperty<Rectangle> zone = new SimpleObjectProperty<Rectangle>(new Rectangle(0, 19, offColor.get()));
	private final DoubleProperty xOffset = new SimpleDoubleProperty();
	private final DoubleProperty yOffset = new SimpleDoubleProperty(6);
	private final BooleanProperty inOff = new SimpleBooleanProperty() ;
	
	public RectangleSelectionOff(int i, boolean iOff){
		
		zone.get().setY(yOffset.get());
		zone.get().setStrokeWidth(1);
		zone.get().setStroke(Color.BLACK);
		
		
		if (iOff){
			inOff.set(true);
		}
		else {
			inOff.set(false);
		}
		
	}
	
	public void setOffX(double keyFrame, double size ){
		zone.get().setWidth(size);
		zone.get().setX(keyFrame);
	}
	
	public Rectangle getZone(){
		return zone.get();
	}

}
