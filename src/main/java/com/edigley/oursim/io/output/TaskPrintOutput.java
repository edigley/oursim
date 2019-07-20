package com.edigley.oursim.io.output;

import java.io.File;
import java.io.IOException;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.entities.Task;

/**
 * 
 * A print-out based implementation of an {@link Output}.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 18/05/2010
 * 
 */
public class TaskPrintOutput extends OutputAdapter {

	public TaskPrintOutput() {
		super();
	}

	public TaskPrintOutput(File file) throws IOException {
		super(file);
		super.appendln("type:time:taskId:replicaId");
	}

	@Override
	public void taskSubmitted(Event<Task> taskEvent) {
		Task Task = taskEvent.getSource();
		super.appendln("(U:" + Task.getSubmissionTime() + ":" + Task.getId() + ":" + Task.getReplicaId() + ":NA)");
	}

	@Override
	public void taskStarted(Event<Task> taskEvent) {
		Task task = taskEvent.getSource();
		String machineName = task.getTaskExecution().getMachine().getName();
		super.appendln("(S:" + task.getStartTime() + ":" + task.getId() + ":" + task.getReplicaId() + ":" + machineName + ":" + task.getEstimatedFinishTime()
				+ ")");
	}

	@Override
	public void taskPreempted(Event<Task> taskEvent) {
		Task task = taskEvent.getSource();
		String machineName = task.getTaskExecution().getMachine().getName();
		super.appendln("(P:" + taskEvent.getTime() + ":" + task.getId() + ":" + task.getReplicaId() + ":" + machineName + ")");
	}

	@Override
	public void taskFinished(Event<Task> taskEvent) {
		Task task = taskEvent.getSource();
		String machineName = task.getTaskExecution().getMachine().getName();
		super.appendln("(F:" + task.getFinishTime() + ":" + task.getId() + ":" + task.getReplicaId() + ":" + machineName + ")");
	}

	@Override
	public void taskCancelled(Event<Task> taskEvent) {
		Task task = taskEvent.getSource();
		String machineName = task.getTaskExecution().getMachine().getName();
		super.appendln("(C:" + taskEvent.getTime() + ":" + task.getId() + ":" + task.getReplicaId() + ":" + machineName + ")");
	}

}