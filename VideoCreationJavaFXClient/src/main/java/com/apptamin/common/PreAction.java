package com.apptamin.common;

public enum PreAction {
	FROM_BOTTOM (0),
	NONE (1);
	
	private final int code;
	
	private PreAction(int code) {
		this.code = code;

	}
	
	public int getCode(){
		return this.code;
	}
	

}
