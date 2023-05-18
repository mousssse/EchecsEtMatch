package ppc.event;

import java.util.ArrayList;
import java.util.List;

import ppc.annotation.Event;

@Event
public class TournamentDeleteClassStatusEvent extends StatusEvent {
	
	private static final List<RegisteredListener> HANDLERS = new ArrayList<>();

	private int listIndex;
	
	public TournamentDeleteClassStatusEvent() {
	}

	public TournamentDeleteClassStatusEvent(int listIndex, EventStatus status) {
		super(status);
		this.listIndex = listIndex;
	}
	
	public TournamentDeleteClassStatusEvent(int listIndex, EventStatus status, String errorMessage) {
		super(status, errorMessage);
		this.listIndex = listIndex;
	}
	
	public int getListIndex() {
		return listIndex;
	}
	
	@Override
	public List<RegisteredListener> getHandlers() {
		return HANDLERS;
	}
}
