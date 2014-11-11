package com.apptamin.model;

import com.apptamin.common.ActionType;
import com.apptamin.common.PostAction;
import com.apptamin.common.PreAction;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import utils.ImportedMedia;
import utils.Point;

public class Action {
	
	private final ObjectProperty<Point> position;
	private final ObjectProperty<ActionType> actionType;
	private final ObjectProperty<PreAction> preAction;
	private final ObjectProperty<PostAction> postAction;
	
	public Action (Point p, ActionType at, PreAction pra,PostAction poa){
		
		this.position = new SimpleObjectProperty<Point>(p);
		this.actionType = new SimpleObjectProperty<ActionType>(at);
		this.preAction = new SimpleObjectProperty<PreAction>(pra);
		this.postAction = new SimpleObjectProperty<PostAction>(poa);
		
		
	}

	public ObjectProperty<Point> positionProperty() {
		return position;
	}

	public ObjectProperty<ActionType> actionTypeProperty() {
		return actionType;
	}

	public ObjectProperty<PreAction> preActionProperty() {
		return preAction;
	}

	public ObjectProperty<PostAction> postActionProperty() {
		return postAction;
	}
	
	

}
