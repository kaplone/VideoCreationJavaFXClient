package com.apptamin.common;

public enum ActionType {
	TAP (0),
	DOUBLE_TAP (1),
	SLIDE_UP (2),
	SLIDE_RIGHT (3),
	SLIDE_DOWN (4),
	SLIDE_LEFT (5);

	private final int code;
	
	private ActionType(int code) {
		this.code = code;

	}
	
	public int getCode(){
		return this.code;
	}
}
