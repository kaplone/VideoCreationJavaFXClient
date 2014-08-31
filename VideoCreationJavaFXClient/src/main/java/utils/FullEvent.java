package utils;

import javafx.event.Event;

public class FullEvent {
	
	private int frameNo;
	private Event event;
	private InternalEventType type;
	public int getFrameNo() {
		return frameNo;
	}
	public void setFrameNo(int frameNo) {
		this.frameNo = frameNo;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public InternalEventType getType() {
		return type;
	}
	public void setType(InternalEventType type) {
		this.type = type;
	}

}
