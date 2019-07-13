package com.edigley.oursim.io.input.workload;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventListener;
import com.edigley.oursim.dispatchableevents.jobevents.JobEventListener;
import com.edigley.oursim.entities.Job;
import com.edigley.oursim.io.input.InputAbstract;

public class DynamicWorkload extends InputAbstract<Job> implements JobEventListener {

	Job nextJob;

	@Override
	public Job peek() {
		return this.nextJob;
	}

	@Override
	public Job poll() {
		Job polledJob = this.peek();
		this.nextJob = null;
		return polledJob;
	}

	public void jobFinished(Event<Job> jobEvent) {
		Job theJob = jobEvent.getSource();
		Job newJob = null; // list.next(theJob)
		// newJob.setSubmitTime(jobEvent.getTime() + theJob.GetThinkTime());
		this.inputs.add(newJob);
	}

	public void jobPreempted(Event<Job> jobEvent) {
	}

	public void jobStarted(Event<Job> jobEvent) {
	}

	public void jobSubmitted(Event<Job> jobEvent) {
	}

	public int compareTo(EventListener o) {
		return this.hashCode() - o.hashCode();
	}

}
