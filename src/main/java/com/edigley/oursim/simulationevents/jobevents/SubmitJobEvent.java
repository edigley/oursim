package com.edigley.oursim.simulationevents.jobevents;

import com.edigley.oursim.dispatchableevents.jobevents.JobEventDispatcher;
import com.edigley.oursim.entities.Job;

/**
 * 
 * Event indicating that a job was submitted.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 20/05/2010
 * 
 */
public class SubmitJobEvent extends JobTimedEvent {

	public static final int PRIORITY = 4;
	
	public SubmitJobEvent(long time, Job job) {
		super(time, PRIORITY, job);
	}

	@Override
	protected final void doAction() {
		JobEventDispatcher.getInstance().dispatchJobSubmitted(this.source);
	}

}
