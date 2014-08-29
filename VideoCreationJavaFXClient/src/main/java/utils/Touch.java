package utils;

import java.io.IOException;

import org.jcodec.api.JCodecException;

import com.fasterxml.jackson.databind.JsonNode;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Touch {
	
	private final IntegerProperty touchedFrame = new SimpleIntegerProperty();
	private final DoubleProperty touchedFrameX = new SimpleDoubleProperty();
	private final DoubleProperty touchedFrameY = new SimpleDoubleProperty();
	private final StringProperty touchedType = new SimpleStringProperty();
	
	public Touch(){
		
	}

	public Touch(JsonNode jsonSave) throws IOException, JCodecException {
		
		this.touchedFrame.set(jsonSave.get("touchedFrame").asInt());
		this.touchedFrameX.set(jsonSave.get("touchedFrameX").asDouble());
		this.touchedFrameY.set(jsonSave.get("touchedFrameY").asDouble());
		this.touchedType.set(jsonSave.get("touchedType").asText());
	}

	public IntegerProperty touchedFrameProperty() {
		return touchedFrame;
	}
	public DoubleProperty touchedFrameXProperty() {
		return touchedFrameX;
	}
	public DoubleProperty touchedFrameYProperty() {
		return touchedFrameY;
	}
	public StringProperty touchedTypeProperty() {
		return touchedType;
	}
	
	public int getTouchedFrame() {
		return touchedFrame.get();
	}
	public double getTouchedFrameX() {
		return touchedFrameX.get();
	}
	public double getTouchedFrameY() {
		return touchedFrameY.get();
	}
	public String getTouchedType() {
		return touchedType.get();
	}

	public void setTouchedFrame(int frame) {
		touchedFrame.set(frame);
	}
	public void setTouchedFrameX(double x) {
		touchedFrameX.set(x);
	}
	public void setTouchedFrameY(double y) {
		touchedFrameY.set(y);
	}
	public void setTouchedType(String type) {
		touchedType.set(type);
	}
	
	

}
