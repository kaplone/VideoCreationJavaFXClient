package utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.shape.Circle;

public class TouchPrint {
	
	private final ObjectProperty<Circle> touchPrint = new SimpleObjectProperty<Circle>();
	private final GaussianBlur blur = new GaussianBlur();
	
	public TouchPrint(){
		this.touchPrint.get().setEffect(this.blur);
	}
	

	public ObjectProperty<Circle> touchPrintProperty() {
		return touchPrint;
	}

	public double getTouchPrintRadius() {
		return touchPrint.get().getRadius();
	}


	public void setTouchPrintRadius(double touchPrintRadius) {
		this.touchPrint.get().setRadius(touchPrintRadius);
	}


	public double getTouchPrintOpacity() {
		return touchPrint.get().getOpacity();
	}


	public void setTouchPrintOpacity(double touchPrintOpacity) {
		this.touchPrint.get().setOpacity(touchPrintOpacity);
	}


	public double getTouchPrintBlur() {
		return ((GaussianBlur) touchPrint.get().getEffect()).getRadius();
	}


	public void setTouchPrintBlur(double touchPrintBlur) {
		blur.setRadius(touchPrintBlur);
		this.touchPrint.get().setEffect(blur);
	}
	
	public double getTouchedFrameX() {
		return touchPrint.get().getCenterX();
	}
	public double getTouchedFrameY() {
		return touchPrint.get().getCenterY();
	}
	
	public void setTouchedFrameX(double x) {
		touchPrint.get().setCenterX(x);
	}
	public void setTouchedFrameY(double y) {
		touchPrint.get().setCenterX(y);
	}
	

}
