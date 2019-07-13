package com.edigley.oursim.dispatchableevents.workerevents;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventListenerAdapter;

/**
 * 
 * A default (empty) implementation of the listener.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 19/05/2010
 * 
 */
public abstract class WorkerEventListenerAdapter extends EventListenerAdapter implements WorkerEventListener {

	public void workerAvailable(Event<String> workerEvent) {
	}

	public void workerUnavailable(Event<String> workerEvent) {
	}

	public void workerUp(Event<String> workerEvent) {
	}

	public void workerDown(Event<String> workerEvent) {
	}

	public void workerIdle(Event<String> workerEvent) {
	}

	public void workerRunning(Event<String> workerEvent) {
	}

}
