package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jcodec.api.JCodecException;

import com.fasterxml.jackson.databind.JsonNode;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Touch {
	
	private final HashMap<Integer, TouchPrint> touchMap = new HashMap<Integer, TouchPrint>();
	
	private final IntegerProperty touchedFirstFrame = new SimpleIntegerProperty();
	private final StringProperty touchedType = new SimpleStringProperty();
	
	public Touch(){
		
	}
	
	public Touch(FullEvent action){
		int frameNo = action.getFrameNo();
		for (TouchPrint touchPrint: computeTouchPrint()){
		    touchMap.put(frameNo, touchPrint);	
		    frameNo++;
		}
		
	}
	
	private ArrayList<TouchPrint> computeTouchPrint(){
		
		
		return null;
		
	}

	public Touch(JsonNode jsonSave) throws IOException, JCodecException {
		
		this.touchedFirstFrame.set(jsonSave.get("touchedFrame").asInt());
		this.touchedType.set(jsonSave.get("touchedType").asText());
	}

	public IntegerProperty touchedFrameProperty() {
		return touchedFirstFrame;
	}

	public StringProperty touchedTypeProperty() {
		return touchedType;
	}
	
	public int getTouchedFrame() {
		return touchedFirstFrame.get();
	}

	public String getTouchedType() {
		return touchedType.get();
	}

	public void setTouchedFrame(int frame) {
		touchedFirstFrame.set(frame);
	}

	public void setTouchedType(String type) {
		touchedType.set(type);
	}
	
	

}
