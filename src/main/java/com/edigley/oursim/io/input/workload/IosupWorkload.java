package com.edigley.oursim.io.input.workload;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;

import com.edigley.oursim.entities.Job;
import com.edigley.oursim.entities.Peer;

/**
 * 
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 10/06/2010
 * 
 */
public class IosupWorkload implements Workload {

	private Map<String, Peer> peers;
	private long startingTime;
	private Job nextJob;
	private Scanner scanner;

	public IosupWorkload(String workloadFilePath, Map<String, Peer> peers) throws FileNotFoundException {
		this(workloadFilePath, peers, 0);
	}

	public IosupWorkload(String workloadFilePath, Map<String, Peer> peers, long startingTime) throws FileNotFoundException {
		this.startingTime = startingTime;
		this.peers = peers;
		this.scanner = new Scanner(new BufferedReader(new FileReader(workloadFilePath)));
		this.scanner.nextLine();
	}

	public boolean merge(Workload other) {
		return false;
	}

	public void close() {
		this.scanner.close();
	}

	public Job peek() {
		if (this.nextJob == null && scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if (!nextLine.trim().isEmpty()) {
				this.nextJob = createJob(nextLine, peers, startingTime);
			}
		}
		return this.nextJob;
	}

	private static Job createJob(String line, Map<String, Peer> peers, long startingTime) {
		assert !line.trim().isEmpty();
		// "time" "jobId" "jobSize" "runtime" "tasks" "user" "peer"
		try {
			Scanner scLine = new Scanner(line);
			long time = scLine.nextLong();
			long jobID = scLine.nextLong();
			long jobSize = scLine.nextLong();
			long runTime = scLine.nextLong();
			String tasks = scLine.next();
			String userID = scLine.next();
			String peerID = scLine.next();
			Job job = new Job(jobID, time, peers.get(peerID));
			job.setUserId(userID);
			Scanner scTasks = new Scanner(tasks);
			scTasks.useDelimiter(";");
			while (scTasks.hasNext()) {
				long taskRunTime = scTasks.nextLong();
				job.addTask("", taskRunTime);
			}
			return job;
		} catch (Exception e) {
			System.err.println("linha: " + line);
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	public Job poll() {
		Job polledJob = this.peek();
		this.nextJob = null;
		return polledJob;
	}

	public void stop() {
		this.nextJob = null;
		this.scanner.close();
		this.scanner = new Scanner("");
	}

}
