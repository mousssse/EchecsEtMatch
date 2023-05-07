package ppc.event;

import java.util.ArrayList;
import java.util.List;

import ppc.annotation.Event;

@Event
public class TournamentCopyStatusEvent extends StatusEvent {

	private static final List<RegisteredListener> HANDLERS = new ArrayList<>();

	public TournamentCopyStatusEvent() {
	}

	public TournamentCopyStatusEvent(EventStatus status) {
		super(status);
	}

	public TournamentCopyStatusEvent(EventStatus status, String errorMessage) {
		super(status, errorMessage);
	}

	@Override
	public List<RegisteredListener> getHandlers() {
		return HANDLERS;
	}

}