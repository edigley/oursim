package com.edigley.oursim.dispatchableevents.workerevents;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventFilter;

/**
 * 
 * The filter that determines which events related to workers the listener wants
 * to be notified.
 * 
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 19/05/2010
 * 
 */
public interface WorkerEventFilter extends EventFilter<Event<String>> {

	/**
	 * A lenient SpotPriceEventFilter that accepts all events.
	 */
	WorkerEventFilter ACCEPT_ALL = new WorkerEventFilter() {

		public boolean accept(Event<String> workerEvent) {
			return true;
		}

	};

	boolean accept(Event<String> workerEvent);

}