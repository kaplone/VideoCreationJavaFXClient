package utils;

import java.io.IOException;

import org.jcodec.api.JCodecException;

import com.fasterxml.jackson.databind.JsonNode;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimeLine {
	
	private final ObservableList<Touch> touchs = FXCollections.observableArrayList();
//	private final IntegerProperty cutIn = new SimpleIntegerProperty(0);
//	private final IntegerProperty cutOut = new SimpleIntegerProperty(0);
	
	public TimeLine(){
		
	}
	
	public TimeLine(JsonNode jsonSave) throws IOException, JCodecException {
		
//		this.cutIn.set(jsonSave.get("cutIn").asInt());
//		this.cutOut.set(jsonSave.get("cutOut").asInt());
		// iterator sur les touch --> creation des objets touch 
		
	}
	
	public ObservableList<Touch> touchsProperty() {
		return touchs;
	}
//	public IntegerProperty cutInProperty() {
//		return cutIn;
//	}
//	public IntegerProperty cutOutProperty() {
//		return cutOut;
//	}
//
//	public int getCutIn(){
//		return cutIn.get();
//	}
//	
//	public int getCutOut(){
//		return cutOut.get();
//	}
//	
//	public void setCutIn(int in){
//		cutIn.set(in);
//	}
//	
//	public void setCutOut(int out){
//		cutOut.set(out);
//	}
}

