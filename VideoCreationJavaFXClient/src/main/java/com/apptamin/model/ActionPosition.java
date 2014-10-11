package com.apptamin.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


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
	public int getZoomLevel() {
		return zoomLevel;
	}
	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	
}
