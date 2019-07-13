package com.edigley.oursim.dispatchableevents.jobevents;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventListenerAdapter;
import com.edigley.oursim.entities.Job;

/**
 * 
 * A default (empty) implementation of the listener.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 19/05/2010
 * 
 */
public abstract class JobEventListenerAdapter extends EventListenerAdapter implements JobEventListener {

	public void jobSubmitted(Event<Job> jobEvent) {
	}

	public void jobStarted(Event<Job> jobEvent) {
	}

	public void jobFinished(Event<Job> jobEvent) {
	}

	public void jobPreempted(Event<Job> jobEvent) {
	}

}
