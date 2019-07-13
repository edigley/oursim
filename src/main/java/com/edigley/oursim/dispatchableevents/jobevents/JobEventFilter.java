package com.edigley.oursim.dispatchableevents.jobevents;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventFilter;
import com.edigley.oursim.entities.Job;

/**
 * 
 * The filter that determines which events related to jobs the listener wants to
 * be notified.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 19/05/2010
 * 
 */
public interface JobEventFilter extends EventFilter<Event<Job>> {

	/**
	 * A lenient JobEventFilter that accepts all events.
	 */
	JobEventFilter ACCEPT_ALL = new JobEventFilter() {

		public boolean accept(Event<Job> jobEvent) {
			return true;
		}

	};

	boolean accept(Event<Job> jobEvent);

}