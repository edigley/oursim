package com.edigley.oursim.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.edigley.oursim.policy.FifoSharingPolicy;

public class TaskTest {

	@Test
	public void testUpdateProcessing() {

		Peer peer = new Peer("", FifoSharingPolicy.getInstance());
		peer.addMachine(new Machine("",Processor.EC2_COMPUTE_UNIT.getSpeed()));
		Job job = new Job(1,0,peer);

		Task task = new Task(0, "executavel.exe", 30, 0, job);

		Processor processor = new Processor(0, 500);

		TaskExecution taskExecution = new TaskExecution(task, processor, 0);

		assertEquals(new Long(130), taskExecution.updateProcessing(50));

		Processor processor2 = new Processor(0, 200);
		taskExecution.setProcessor(processor2);
		assertEquals(new Long(295), taskExecution.updateProcessing(80));

		Processor processor3 = new Processor(0, 1000);
		taskExecution.setProcessor(processor3);
		assertEquals(new Long(9), taskExecution.updateProcessing(130));
		taskExecution.setProcessor(processor);
		assertEquals(new Long(9), taskExecution.updateProcessing(139));

		assertEquals(new Long(0), taskExecution.updateProcessing(148));

	}
	
	@Test
	public void testMultiCoreTask() {
		
		Peer peer = new Peer("the-peer", FifoSharingPolicy.getInstance());
		peer.addMachine(new Machine("the-machine", Processor.EC2_COMPUTE_UNIT.getSpeed(), 4));
		Job job = new Job(1, 0, peer);

		Task task = new Task(0, "executavel.exe", 3, 30, 0, job);
		
	}

}