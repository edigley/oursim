package com.edigley.oursim.dispatchableevents.taskevents;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventListenerAdapter;
import com.edigley.oursim.entities.Task;

/**
 * 
 * A default (empty) implementation of the listener
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 19/05/2010
 * 
 */
public abstract class TaskEventListenerAdapter extends EventListenerAdapter implements TaskEventListener {

	public void taskFinished(Event<Task> taskEvent) {
	}

	public void taskPreempted(Event<Task> taskEvent) {
	}

	public void taskStarted(Event<Task> taskEvent) {
	}

	public void taskSubmitted(Event<Task> taskEvent) {
	}

}
