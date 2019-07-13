package com.edigley.oursim.policy;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventListener;
import com.edigley.oursim.entities.Job;
import com.edigley.oursim.entities.Peer;
import com.edigley.oursim.entities.Task;
import com.edigley.oursim.io.input.workload.Workload;
import com.edigley.oursim.simulationevents.ActiveEntityImp;

/**
 * 
 * An abstract definition of a grid Scheduler. The only mandatory method to be
 * implemented by the subclasses is the method
 * {@link JobSchedulerPolicy#schedule()}.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 18/05/2010
 * 
 */
public abstract class JobSchedulerPolicyAbstract extends ActiveEntityImp implements JobSchedulerPolicy {

	/**
	 * The jobs that have been submitted to this scheduler. TODO talvez esse
	 * atributo seja um disperdício de memória.
	 */
	// private Set<Job> submittedJobs;
	/**
	 * The tasks of all jobs that have been submitted to this scheduler. The
	 * schedulling is effectively performed in this collection.
	 */
	private Set<Task> submittedTasks;

	private Set<Task> runningTasks;

	/**
	 * All grid's participating peers.
	 */
	private List<Peer> peers;

	/**
	 * The workload to be processed by this scheduler.
	 */
	private Workload workload;

	/**
	 * An ordinary constructor.
	 * 
	 * @param peers
	 *            All the peers that compound of the grid.
	 */
	public JobSchedulerPolicyAbstract(List<Peer> peers) {
		this.peers = peers;
		// this.submittedJobs = new HashSet<Job>();
		this.submittedTasks = new TreeSet<Task>();
		this.runningTasks = new TreeSet<Task>();
	}

	/**
	 * 
	 * Performs a rescheduling of the task. The task already has been schedulled
	 * but it was preempted by some reason.
	 * 
	 * @param Task
	 *            The task to be rescheduled.
	 */
	protected void rescheduleTask(Task Task) {
		this.addSubmitTaskEvent(getCurrentTime(), Task);
	}

	public boolean isFinished() {
		return this.submittedTasks.isEmpty() && this.runningTasks.isEmpty();
	}

	public void stop() {
		this.submittedTasks.clear();
		//XXX talvez devesse cancelar todas as runningTasks antes
		this.runningTasks.clear();
	}

	public void addJob(Job job) {
		assert !job.getTasks().isEmpty();
		// this.submittedJobs.add(job);
		for (Task Task : job.getTasks()) {
			this.addSubmitTaskEvent(this.getCurrentTime(), Task);
		}
	}

	public void addWorkload(Workload workload) {
		if (this.workload != null) {
			this.workload.merge(workload);
		} else {
			this.workload = workload;
		}
	}

	public int getQueueSize() {
		return submittedTasks.size();
	}

	public int getNumberOfRunningTasks() {
		return runningTasks.size();
	}

	protected final void addFutureJobEventsToEventQueue() {
		long nextSubmissionTime = (workload.peek() != null) ? workload.peek().getSubmissionTime() : -1;
		while (workload.peek() != null && workload.peek().getSubmissionTime() == nextSubmissionTime) {
			Job job = workload.poll();
			long time = job.getSubmissionTime();
			this.addSubmitJobEvent(time, job);
		}
	}

	// protected Set<Job> getSubmittedJobs() {
	// return submittedJobs;
	// }

	protected Set<Task> getSubmittedTasks() {
		return submittedTasks;
	}

	public Set<Task> getRunningTasks() {
		return runningTasks;
	}

	protected List<Peer> getPeers() {
		return peers;
	}

	// B-- beginning of implementation of JobEventListener

	public void jobSubmitted(Event<Job> jobEvent) {
		this.addJob(jobEvent.getSource());
	}

	public void jobPreempted(Event<Job> jobEvent) {
	}

	public void jobFinished(Event<Job> jobEvent) {
	}

	public void jobStarted(Event<Job> jobEvent) {
	}

	// E-- end of implementation of JobEventListener

	// B-- beginning of implementation of TaskEventListener

	public void taskStarted(Event<Task> taskEvent) {
		Task Task = taskEvent.getSource();
		this.runningTasks.add(Task);
	}

	public void taskFinished(Event<Task> taskEvent) {
		Task Task = taskEvent.getSource();
		Task.getTargetPeer().finishTask(Task);
		boolean removed = this.runningTasks.remove(Task);
		assert removed : Task;
	}

	public void taskSubmitted(Event<Task> taskEvent) {
		Task Task = taskEvent.getSource();
		this.submittedTasks.add(Task);
	}

	public void taskPreempted(Event<Task> taskEvent) {
		Task Task = taskEvent.getSource();
		boolean removed = this.runningTasks.remove(Task);
		assert removed : Task;
	}

	public void taskCancelled(Event<Task> taskEvent) {
		Task Task = taskEvent.getSource();
		boolean removed = this.runningTasks.remove(Task);
		assert removed : Task;
	}

	// E-- end of implementation of TaskEventListener

	// B-- beginning of implementation of SpotPriceEventListener

	public void workerAvailable(Event<String> workerEvent) {
		this.schedule();
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

	// E-- end of implementation of SpotPriceEventListener

	public int compareTo(EventListener o) {
		return this.hashCode() - o.hashCode();
	}
}
