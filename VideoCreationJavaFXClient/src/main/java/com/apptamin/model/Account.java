package com.apptamin.model;

import java.net.URL;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {
	
	private final ObjectProperty<URL> serverURL;
	private final StringProperty username;
	private final StringProperty pass;
	
	public ObjectProperty<URL> serverURLProperty() {
		return serverURL;
	}
	public StringProperty usernameProperty() {
		return username;
	}
	public StringProperty passProperty() {
		return pass;
	}
	
	public Account(){
		this.serverURL = null; //new SimpleObjectProperty<URL>(new URL(""));
		this.username = new SimpleStringProperty();
		this.pass = new SimpleStringProperty();
	}

}
