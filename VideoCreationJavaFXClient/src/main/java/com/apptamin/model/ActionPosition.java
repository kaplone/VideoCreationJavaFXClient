package com.apptamin.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.apptamin.client.Point;

/**
 * Model for a touch.
 * @param coordX x coordinate in the UI size
 * @param coordY y coordinate in the UI size
 * @param imageNumber frame number the touch occurs (before trim in) aka frame number in temp folder
 * @param previousPosition position where the hand is comming from
 * @param nextPosition position where the hand is going
 * @param zoomLevel (unused)
 * @param actionType type of touch
 *
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionPosition {
	public ActionPosition() {
		super();
	}
	
	
	public ActionPosition(double coordX, double coordY, int imageNumber,
			int previousPosition, int nextPosition, int zoomLevel,
			int actionType) {
		super();
		this.coordX = coordX;
		this.coordY = coordY;
		this.imageNumber = imageNumber;
		this.previousPosition = previousPosition;
		this.nextPosition = nextPosition;
		this.zoomLevel = zoomLevel;
		this.actionType = actionType;
	}
	private double coordX;
	private double coordY;
	private int imageNumber;
	private int previousPosition;
	private int nextPosition;
	private int zoomLevel;
	private int actionType;
	
	
	public String print(){
		return "x : " + this.coordX +
			   ", y : " + this.coordY +
			   ", i : " + this.imageNumber + 
			   ", p : " + this.previousPosition + 
			   ", n : " + this.nextPosition + 
			   ", a : " + this.actionType;
			   
	}

	public double getCoordX() {
		return coordX;
	}
	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}
	public double getCoordY() {
		return coordY;
	}
	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}
	public int getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}
	public int getPreviousPosition() {
		return previousPosition;
	}
	public void setPreviousPosition(int previousPosition) {
		this.previousPosition = previousPosition;
	}
	public int getNextPosition() {
		return nextPosition;
	}
	public void setNextPosition(int nextPosition) {
		this.nextPosition = nextPosition;
	}
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	
}
