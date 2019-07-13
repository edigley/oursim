package com.edigley.oursim.io.output;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventListener;
import com.edigley.oursim.dispatchableevents.jobevents.JobEventCounter;
import com.edigley.oursim.dispatchableevents.jobevents.JobEventListener;
import com.edigley.oursim.dispatchableevents.taskevents.TaskEventCounter;
import com.edigley.oursim.dispatchableevents.taskevents.TaskEventListener;
import com.edigley.oursim.entities.Job;
import com.edigley.oursim.entities.Task;

/**
 * 
 * A print-out based implementation of an {@link Output}.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 18/05/2010
 * 
 */
public class ComputingElementEventCounter implements JobEventListener, TaskEventListener {

	private JobEventCounter jobEventCounter;
	private TaskEventCounter taskEventCounter;

	public ComputingElementEventCounter() {
		this.jobEventCounter = new JobEventCounter();
		this.taskEventCounter = new TaskEventCounter();
	}

	public long getSumOfTasksMakespan() {
		return this.taskEventCounter.getSumOfTasksMakespan();
	}
	
	public long getSumOfJobsMakespan() {
		return this.jobEventCounter.getSumOfJobsMakespan();
	}

	public final int getNumberOfSubmittedTasks() {
		return this.taskEventCounter.getNumberOfSubmittedTasks();
	}

	public final int getNumberOfFinishedTasks() {
		return this.taskEventCounter.getNumberOfFinishedTasks();
	}

	public final int getNumberOfPreemptionsForAllTasks() {
		return this.taskEventCounter.getNumberOfPreemptionsForAllTasks();
	}

	public final int getNumberOfPreemptionsForAllJobs() {
		return this.jobEventCounter.getNumberOfPreemptionsForAllJobs();
	}

	public double getTotalCostOfAllFinishedJobs() {
		return this.jobEventCounter.getTotalCostOfAllFinishedJobs();
	}

	public double getTotalCostOfAllPreemptedJobs() {
		return this.jobEventCounter.getTotalCostOfAllPreemptedJobs();
	}

	public final int getNumberOfSubmittedJobs() {
		return this.jobEventCounter.getNumberOfSubmittedJobs();
	}

	public final int getNumberOfFinishedJobs() {
		return this.jobEventCounter.getNumberOfFinishedJobs();
	}

	public void jobFinished(Event<Job> jobEvent) {
		this.jobEventCounter.jobFinished(jobEvent);
	}

	public void jobPreempted(Event<Job> jobEvent) {
		this.jobEventCounter.jobPreempted(jobEvent);
	}

	public void jobStarted(Event<Job> jobEvent) {
		this.jobEventCounter.jobStarted(jobEvent);
	}

	public void jobSubmitted(Event<Job> jobEvent) {
		this.jobEventCounter.jobSubmitted(jobEvent);
	}

	public void taskCancelled(Event<Task> taskEvent) {
		this.taskEventCounter.taskCancelled(taskEvent);
	}

	public void taskFinished(Event<Task> taskEvent) {
		this.taskEventCounter.taskFinished(taskEvent);
	}

	public void taskPreempted(Event<Task> taskEvent) {
		this.taskEventCounter.taskPreempted(taskEvent);
	}

	public void taskStarted(Event<Task> taskEvent) {
		this.taskEventCounter.taskStarted(taskEvent);
	}

	public void taskSubmitted(Event<Task> taskEvent) {
		this.taskEventCounter.taskSubmitted(taskEvent);
	}

	public int compareTo(EventListener o) {
		return this.hashCode() - o.hashCode();
	}

}