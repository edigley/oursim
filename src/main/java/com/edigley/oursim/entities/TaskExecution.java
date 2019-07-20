package com.edigley.oursim.entities;

import java.util.List;

import com.google.common.collect.Lists;

public class TaskExecution {

	/**
	 * The size in Millions of Instructions (MI) of this Task to be executed in
	 */
	private long size;

	private long previousTime;

	private long remainingSize;

	private Task task;

	private List<Processor> processors;

	public TaskExecution(Task task, Processor processor, long startTime) {
		this(task, Lists.newArrayList(processor), startTime);
	}
	
	public TaskExecution(Task task, List<Processor> processors, long startTime) {
		this.processors = processors;
		for (Processor processor : processors) {			
			assert !processor.isBusy() : task + " -> " + processor;
			if (processor.isBusy()) {
				throw new IllegalArgumentException("The processor is already executing some other task.");
			}
			this.task = task;
			processor.busy();
			// this.size =
			// Processor.EC2_COMPUTE_UNIT.calculateNumberOfInstructionsProcessed(this.task.getDuration());
			this.size = convertTaskDurationToNumberOfInstruction(task);
			this.remainingSize = size;
			this.previousTime = startTime;
			// TODO talvez nesse momento já devesse setar o startTime da Task, para
			// não deixar para alguém externo fazer isso
		}
	}

	private long convertTaskDurationToNumberOfInstruction(Task Task) {
		Peer sourcePeer = Task.getSourcePeer();
		Processor referenceProcessor = sourcePeer.getReferenceProcessor();
		return referenceProcessor.calculateNumberOfInstructionsProcessed(this.task.getDuration());
	}

	/**
	 * @param currentTime
	 * @return The time lacking to this Task be finished in that processor.
	 */
	public Long updateProcessing(long currentTime) {
		assert currentTime > previousTime;

		// time since last update
		long timeElapsed = currentTime - previousTime;

		// TODO: verificar as consequências do remaining time negativo.
		this.remainingSize -= processors.get(0).calculateNumberOfInstructionsProcessed(timeElapsed);

		this.previousTime = currentTime;

		return (remainingSize <= 0) ? 0 : processors.get(0).calculateTimeToExecute(remainingSize);

	}

	public Long getRemainingTimeToFinish() {
		if (remainingSize > 0) {
			return processors.get(0).calculateTimeToExecute(remainingSize);
		} else {
			return 0L;
		}
	}

	public Long getEstimatedFinishTime() {
		return previousTime + getRemainingTimeToFinish();
	}

	public void setProcessors(List<Processor> processors) {
		this.processors = processors;
	}

	public void setProcessor(Processor processor) {
		this.processors = Lists.newArrayList(processor);
	}
	
	public Machine getMachine() {
		return this.processors.get(0).getMachine();
	}

	public void finish() {
		for (Processor processor : processors) {
			processor.free();
		}
	}

	public List<Processor> getProcessor() {
		return this.processors;
	}

}
