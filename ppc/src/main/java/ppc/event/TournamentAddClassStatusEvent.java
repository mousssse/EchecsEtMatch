package ppc.event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ppc.annotation.Event;

@Event
public class TournamentAddClassStatusEvent extends StatusEvent {

	private static final List<RegisteredListener> HANDLERS = new ArrayList<>();
	
	private File csvFile;
	
	public TournamentAddClassStatusEvent() {
	}
	
	public TournamentAddClassStatusEvent(File csvFile, EventStatus status) {
		super(status);
		this.csvFile = csvFile;
	}
	
	public TournamentAddClassStatusEvent(File csvFile, EventStatus status, String errorMessage) {
		super(status, errorMessage);
		this.csvFile = csvFile;
	}
	
	public File getCSVFile() {
		return csvFile;
	}

	@Override
	public List<RegisteredListener> getHandlers() {
		return HANDLERS;
	}

}
