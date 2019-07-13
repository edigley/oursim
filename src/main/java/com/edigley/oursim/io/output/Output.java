package com.edigley.oursim.io.output;

import java.io.Closeable;
import java.io.IOException;

import com.edigley.oursim.dispatchableevents.jobevents.JobEventListener;
import com.edigley.oursim.dispatchableevents.taskevents.TaskEventListener;
import com.edigley.oursim.dispatchableevents.workerevents.WorkerEventListener;

/**
 * 
 * The generic (Job based) output of a simulation.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 18/05/2010
 * 
 */
public interface Output extends JobEventListener, TaskEventListener, WorkerEventListener, Closeable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	void close() throws IOException;

}
