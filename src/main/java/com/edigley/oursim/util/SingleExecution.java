package com.edigley.oursim.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.edigley.oursim.OurSim;
import com.edigley.oursim.entities.Grid;
import com.edigley.oursim.entities.Job;
import com.edigley.oursim.entities.Peer;
import com.edigley.oursim.io.input.Input;
import com.edigley.oursim.io.input.availability.AvailabilityRecord;
import com.edigley.oursim.io.input.availability.MarkovModelAvailabilityCharacterization;
import com.edigley.oursim.io.input.workload.Workload;
import com.edigley.oursim.io.input.workload.WorkloadAbstract;
import com.edigley.oursim.policy.FifoSharingPolicy;
import com.edigley.oursim.policy.JobSchedulerPolicy;
import com.edigley.oursim.policy.OurGridPersistentScheduler;
import com.edigley.oursim.simulationevents.ActiveEntityImp;
import com.edigley.oursim.simulationevents.EventQueue;

public class SingleExecution {
	
	private List<Peer> peers;
	
	int nextJobId = 0;
	int jobsSubmissionTime = 0;

	public SingleExecution(List<Peer> listOfPeers) {
		this.peers = listOfPeers;
	}

	private static BufferedWriter createBufferedWriter(File utilizationFile) {
		try {
			if (utilizationFile != null) {
				return new BufferedWriter(new FileWriter(utilizationFile));
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}			
	
	public static void main(String[] args) throws FileNotFoundException {

		long availabilityThreshold = 300;
		Grid grid = new Grid();
		for (int i = 0; i < 4; i++) {
			grid.addPeer(new Peer(i + "", 5, FifoSharingPolicy.getInstance()));
		}
		Input<AvailabilityRecord> availability = new MarkovModelAvailabilityCharacterization(grid.getMapOfPeers(), availabilityThreshold, 0);

		// while (availability.peek() != null) {
		// System.out.println(availability.poll());
		// }

		SingleExecution s = new SingleExecution(grid.getListOfPeers());
		Workload workload = s.generateWorkload(2, 5, 8);

		JobSchedulerPolicy jobScheduler = new OurGridPersistentScheduler(grid.getListOfPeers());
		
		OurSim oursim = new OurSim(EventQueue.getInstance(), grid, jobScheduler, workload, availability);

		grid.setUtilizationBuffer(createBufferedWriter(new File("oursim_system_utilization.txt")));
		
		oursim.setActiveEntity(new ActiveEntityImp());

		oursim.start();


	}

	protected Workload generateWorkload(final int numberOfJobsByPeer, final int numberOfTasksByJob, final long jobLength) {

		Workload allWorkloads = new WorkloadAbstract() {
			@Override
			protected void setUp() {
			}
		};

		for (final Peer peer : peers) {

			WorkloadAbstract workloadForPeer = new WorkloadAbstract() {
				@Override
				protected void setUp() {
					for (int i = 0; i < numberOfJobsByPeer; i++) {
						Job job = new Job(nextJobId, jobsSubmissionTime, peer);
						for (int j = 0; j < numberOfTasksByJob; j++) {
							job.addTask("", jobLength);
						}
						this.inputs.add(job);
						nextJobId++;
						jobsSubmissionTime += 50;
					}
				}
			};

			allWorkloads.merge(workloadForPeer);
		}

		return allWorkloads;
	}

}
