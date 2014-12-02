package com.apptamin.common;

public enum PostAction {
	TO_BOTTOM(0),
	NONE (1);
	
	private final int code;
	
	private PostAction(int code) {
		this.code = code;

	}
	
	public int getCode(){
		return this.code;
	}
}
