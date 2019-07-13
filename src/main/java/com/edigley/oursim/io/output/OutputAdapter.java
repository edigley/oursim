package com.edigley.oursim.io.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import com.edigley.oursim.dispatchableevents.Event;
import com.edigley.oursim.dispatchableevents.EventListener;
import com.edigley.oursim.entities.Job;
import com.edigley.oursim.entities.Task;

/**
 * 
 * A default (empty) implementation of {@link Output}.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 18/05/2010
 * 
 */
public abstract class OutputAdapter implements Output {

	/**
	 * the stream where the results will be printed out.
	 */
	protected BufferedWriter bw = null;

	protected PrintStream out;

	/**
	 * An default constructor. Using this constructor the results will be
	 * printed out in the default output.
	 */
	public OutputAdapter() {
		this.out = System.out;
	}

	/**
	 * Using this constructor the results will be printed out in the file
	 * <code>file</code>.
	 * 
	 * @param file
	 *            The file where the results will be printed out.
	 */
	public OutputAdapter(File file) throws IOException {
		this.bw = new BufferedWriter(new FileWriter(file));
	}

	public void jobFinished(Event<Job> jobEvent) {
	}

	public void jobPreempted(Event<Job> jobEvent) {
	}

	public void jobStarted(Event<Job> jobEvent) {
	}

	public void jobSubmitted(Event<Job> jobEvent) {
	}

	public void taskFinished(Event<Task> taskEvent) {
	}

	public void taskPreempted(Event<Task> taskEvent) {
	}

	public void taskStarted(Event<Task> taskEvent) {
	}

	public void taskSubmitted(Event<Task> taskEvent) {
	}

	public void taskCancelled(Event<Task> taskEvent) {
	}

	public void workerAvailable(Event<String> workerEvent) {
	}

	public void workerDown(Event<String> workerEvent) {
	}

	public void workerIdle(Event<String> workerEvent) {
	}

	public void workerRunning(Event<String> workerEvent) {
	}

	public void workerUnavailable(Event<String> workerEvent) {
	}

	public void workerUp(Event<String> workerEvent) {
	}

	public void close() throws IOException {
		if (bw != null) {
			this.bw.close();
		}
	}

	protected void appendln(String line) {
		if (bw != null) {
			try {
				this.bw.append(line).append("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			this.out.println(line);
		}
	}

	public int compareTo(EventListener o) {
		return this.hashCode() - o.hashCode();
	}
}
