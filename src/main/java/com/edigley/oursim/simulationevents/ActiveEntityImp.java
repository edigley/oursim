package com.edigley.oursim.simulationevents;

import com.edigley.oursim.entities.Job;
import com.edigley.oursim.entities.Task;
import com.edigley.oursim.io.input.availability.AvailabilityRecord;
import com.edigley.oursim.simulationevents.jobevents.FinishJobEvent;
import com.edigley.oursim.simulationevents.jobevents.PreemptedJobEvent;
import com.edigley.oursim.simulationevents.jobevents.StartedJobEvent;
import com.edigley.oursim.simulationevents.jobevents.SubmitJobEvent;
import com.edigley.oursim.simulationevents.taskevents.CancelledTaskEvent;
import com.edigley.oursim.simulationevents.taskevents.FinishTaskEvent;
import com.edigley.oursim.simulationevents.taskevents.PreemptedTaskEvent;
import com.edigley.oursim.simulationevents.taskevents.StartedTaskEvent;
import com.edigley.oursim.simulationevents.taskevents.SubmitTaskEvent;
import com.edigley.oursim.simulationevents.workerevents.WorkerAvailableEvent;
import com.edigley.oursim.simulationevents.workerevents.WorkerUnavailableEvent;

/**
 * 
 * A default, convenient implementation of an {@link ActiveEntity}.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 01/06/2010
 * 
 */
public class ActiveEntityImp implements ActiveEntity {

	/**
	 * The event queue that will be processed.
	 */
	private EventQueue eventQueue;

	public final void setEventQueue(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

	public EventQueue getEventQueue() {
		return eventQueue;
	}

	public long getCurrentTime() {
		return eventQueue.getCurrentTime();
	}

	public void addSubmitJobEvent(long submitTime, Job job) {
		assert submitTime >= getCurrentTime() : submitTime + " >= " + getCurrentTime();
		this.getEventQueue().addEvent(new SubmitJobEvent(submitTime, job));
	}

	@Deprecated
	public void addStartedJobEvent(Job job) {
		this.getEventQueue().addEvent(new StartedJobEvent(job));
		this.addFinishJobEvent(job.getEstimatedFinishTime(), job);
	}

	public void addPreemptedJobEvent(long preemptionTime, Job job) {
		this.getEventQueue().addEvent(new PreemptedJobEvent(preemptionTime, job));
	}

	public void addFinishJobEvent(long finishTime, Job job) {
		assert finishTime >= this.getCurrentTime();
		FinishJobEvent finishJobEvent = new FinishJobEvent(finishTime, job);
		this.getEventQueue().addEvent(finishJobEvent);
	}

	public void addSubmitTaskEvent(long submitTime, Task Task) {
		this.getEventQueue().addEvent(new SubmitTaskEvent(submitTime, Task));
	}

	public void addStartedTaskEvent(Task Task) {
		this.getEventQueue().addEvent(new StartedTaskEvent(Task));
		this.addFinishTaskEvent(Task.getEstimatedFinishTime(), Task);
	}

	public void addPreemptedTaskEvent(long preemptionTime, Task Task) {
		this.getEventQueue().addEvent(new PreemptedTaskEvent(preemptionTime, Task));
	}

	public void addPreemptedTaskEvent(Task Task) {
		addPreemptedTaskEvent(getCurrentTime(), Task);
	}

	public void addCancelledTaskEvent(long cancellingTime, Task Task) {
		this.getEventQueue().addEvent(new CancelledTaskEvent(cancellingTime, Task));
	}

	public void addCancelledTaskEvent(Task Task) {
		addCancelledTaskEvent(getCurrentTime(), Task);
	}

	public void addFinishTaskEvent(long finishTime, Task Task) {
		assert finishTime > this.getCurrentTime():finishTime +" > "+ this.getCurrentTime();
		FinishTaskEvent finishTaskEvent = new FinishTaskEvent(finishTime, Task);
		this.getEventQueue().addEvent(finishTaskEvent);
	}

	public void addWorkerAvailableEvent(long time, String machineName, long duration) {
		assert duration > 0 && time >= 0;
		this.getEventQueue().addEvent(new WorkerAvailableEvent(time, machineName));
		this.getEventQueue().addEvent(new WorkerUnavailableEvent(time + duration, machineName));
	}

	public void addAvailabilityRecordEvent(long time, AvailabilityRecord avRecord) {
		// XXX if (avRecord instanceof SpotPrice) {
		// this.getEventQueue().addEvent(new NewSpotPriceEvent((SpotPrice)
		// avRecord));
		// } else {
		// this.addWorkerAvailableEvent(time, avRecord.getMachineName(),
		// avRecord.getDuration());
		// }
		if (avRecord.getClass() == AvailabilityRecord.class) {
			this.addWorkerAvailableEvent(time, avRecord.getMachineName(), avRecord.getDuration());
		} else {
			System.out.println("(avRecord.getClass() == AvailabilityRecord.class) -----> " + (avRecord.getClass() == AvailabilityRecord.class));
		}
	}

	public void addHaltEvent(long haltTime) {
		HaltSimulationEvent haltEvent = new HaltSimulationEvent(haltTime);
		this.getEventQueue().addEvent(haltEvent);
	}

}
