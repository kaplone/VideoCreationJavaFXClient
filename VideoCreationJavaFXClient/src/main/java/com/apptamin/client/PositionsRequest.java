package com.apptamin.client;

import java.util.ArrayList;
import java.util.List;

import com.apptamin.model.ActionPosition;

public class PositionsRequest {
	private List<ActionPosition> positions = new ArrayList<ActionPosition>();

	public List<ActionPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<ActionPosition> positions) {
		this.positions = positions;
	}
	
	public void add(ActionPosition item) {
		this.positions.add(item);
	}
}
