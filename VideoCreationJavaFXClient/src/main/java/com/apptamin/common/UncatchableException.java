package com.apptamin.common;

import java.util.logging.Logger;

public class UncatchableException extends RuntimeException {

	
	public UncatchableException(String mess) {
		super(mess);
		Logger log = Logger.getLogger("SocketClient");
		log.warning(mess);
	}

	private static final long serialVersionUID = 1L;

}
