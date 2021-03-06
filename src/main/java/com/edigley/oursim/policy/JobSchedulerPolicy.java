package com.edigley.oursim.policy;

import com.edigley.oursim.dispatchableevents.jobevents.JobEventListener;
import com.edigley.oursim.dispatchableevents.taskevents.TaskEventListener;
import com.edigley.oursim.dispatchableevents.workerevents.WorkerEventListener;
import com.edigley.oursim.entities.Job;
import com.edigley.oursim.io.input.workload.Workload;
import com.edigley.oursim.simulationevents.ActiveEntity;

/**
 * 
 * A mininal interface to define a grid Scheduler. A Scheduler defines
 * inplicitly many policies to decide what to do when events occurs, like
 * finishs or preemptions of tasks and jobs, through the implementation of
 * methods from the interfaces {@link JobEventListener},
 * {@link TaskEventListener} and {@link SpotPriceEventListener}. For example, the
 * definition of what to do face a task's preemption must to be done by the
 * implementation of
 * {@link TaskEventListener#taskPreempted(com.edigley.oursim.dispatchableevents.Event)}.
 * Though the concrete schedulers could implement directly this interface, it's
 * recomended to <i>all</i> concrete schedulers to implement this interface by
 * extending the abstract class {@link JobSchedulerPolicyAbstract} that
 * implements an skeleton of how an ordinary scheduler should perform.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 18/05/2010
 * 
 */
public interface JobSchedulerPolicy extends JobEventListener, TaskEventListener, WorkerEventListener, ActiveEntity {

	/**
	 * Simply Adds the job to this scheduler. To This method, unlike
	 * {@link JobSchedulerPolicy#addWorkload(Workload)}, doesn't matter the
	 * time at which the job was originally submitted.
	 * 
	 * @param job
	 *            The job to be added.
	 */
	void addJob(Job job);

	/**
	 * Adds a workload o this scheduler. The original workload will be
	 * invalidated after the calling of this operation. Actually, this operation
	 * performs a merge of the given workload with the others that has already
	 * been included in this scheduler.
	 * 
	 * @param workload
	 *            the workload to be added.
	 * @see {@link Workload#merge(Workload)}
	 */
	void addWorkload(Workload workload);

	/**
	 * Performs efectivelly the scheduling of the jobs enqueued in this
	 * scheduler.
	 */
	void schedule();
	
	boolean isFinished();
	
	void stop();
	
	int getQueueSize();

	int getNumberOfRunningTasks();

}
