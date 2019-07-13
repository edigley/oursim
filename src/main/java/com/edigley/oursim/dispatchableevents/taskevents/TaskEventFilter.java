package com.edigley.oursim.dispatchableevents.taskevents;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventFilter;
import com.edigley.oursim.entities.Task;

/**
 * 
 * The filter that determines which events related to tasks the listener wants
 * to be notified.
 * 
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 19/05/2010
 * 
 */
public interface TaskEventFilter extends EventFilter<Event<Task>> {

	/**
	 * A lenient TaskEventFilter that accepts all events.
	 */
	TaskEventFilter ACCEPT_ALL = new TaskEventFilter() {

		public boolean accept(Event<Task> taskEvent) {
			return true;
		}

	};

	boolean accept(Event<Task> taskEvent);

}