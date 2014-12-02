package com.apptamin.client;

//TODO voir  : 
//             java.awt.geom.Point2D.Double
//             et java.awt.geom.Point2D

import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.apptamin.model.ActionPosition;

public class Point {
	
	private double coordX;
	private double coordY;
	private Point relativeTo;
	private int imageNumber;
	
	final static Point rootPoint = new Point(0, 0);

	public Point(double coordX, double coordY, Point relatif, int num) {
		this.coordX = coordX;
		this.coordY = coordY;
		this.relativeTo = relatif;
		this.imageNumber = num;
	}
	public Point(double coordX, double coordY) {
		this(coordX, coordY, null, -1);
	}
	
	public double fullDeltaX(Point p){
		return p.getCoordX() - this.coordX;
	}
	public double fullDeltaY(Point p){
		return p.getCoordY() - this.coordY;
	}
	
	public double distanceToPoint (Point p){
		double squareDeltaX = ( this.coordX - p.getCoordX())*  (this.coordX - p.getCoordX());
		double squareDeltaY = (this.coordY - p.getCoordY()) *  (this.coordY - p.getCoordY());
		return Math.sqrt(squareDeltaX + squareDeltaY);
	}
	
	public Point fingerRelativeToRootPoint(int x, int y){
		return new Point(x - this.getCoordX( ), y - this.getCoordY(), rootPoint, this.imageNumber);
	}
	
	public Point fingerRelativeToDevice(int x, int y){
		return fingerRelativeToRootPoint(x + 788, y + 451);// TODO : update with object
	}
	
	public static Map<Integer, Point> getPoints(ActionPosition [] liste){
		Map<Integer, Point> points = new HashMap<Integer, Point>();
		for (ActionPosition ap : liste){
			points.put(ap.getImageNumber(), Point.getPoint(ap));
		}
		
		return points;
	}
	
	public static Point getPoint(ActionPosition ap){
		
		return new Point(ap.getCoordX(), ap.getCoordY(), null, ap.getImageNumber()) ;
	}

	public double getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public double getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public Point getRelativeTo() {
		return relativeTo;
	}

	public void setRelativeTo(Point relatif) {
		this.relativeTo = relatif;
	}
	
	public int getImageNumber() {
		return imageNumber;
	}
	
	public void setImageNumber(int imageNumero) {
		this.imageNumber = imageNumero;
	}

}
