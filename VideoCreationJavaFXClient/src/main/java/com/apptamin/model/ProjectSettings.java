package com.apptamin.model;

import java.net.MalformedURLException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.text.Font;

public class ProjectSettings {
	
	private final ObjectProperty<Device> device;
	private final ObjectProperty<Background> background;
	private final ObjectProperty<RightHand> handType;
	private final ObjectProperty<Font> font;
	private final ObjectProperty<Account> account;
	
	public ObjectProperty<Device> deviceProperty() {
		return device;
	}
	public ObjectProperty<Background> backgroundProperty() {
		return background;
	}
	public ObjectProperty<RightHand> handTypeProperty() {
		return handType;
	}
	public ObjectProperty<Font> fontProperty() {
		return font;
	}
	public ObjectProperty<Account> accountProperty() {
		return account;
	}
	
	public ProjectSettings(){
		
		this.device = new SimpleObjectProperty<Device>(new Device());
		this.background = new SimpleObjectProperty<Background>(new Background());
		this.handType = new SimpleObjectProperty<RightHand>(new RightHand());
		this.font = new SimpleObjectProperty<Font>(new Font(10.0));
		this.account = new SimpleObjectProperty<Account>(new Account());
		
	}
	

}
