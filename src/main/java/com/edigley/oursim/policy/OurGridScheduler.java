package com.edigley.oursim.policy;

import java.util.Iterator;
import java.util.List;

import com.edigley.oursim.entities.Peer;
import com.edigley.oursim.entities.Task;
import com.edigley.oursim.policy.JobSchedulerPolicyAbstract;

/**
 * 
 * An reference implementation of a {@link JobSchedulerPolicy}.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 18/05/2010
 * 
 */
public class OurGridScheduler extends JobSchedulerPolicyAbstract {

	/**
	 * An ordinary constructor.
	 * 
	 * @param peers
	 *            All the peers that compound of the grid.
	 */
	public OurGridScheduler(List<Peer> peers) {
		super(peers);
	}

	public final void schedule() {
		for (Iterator<Task> iterator = this.getSubmittedTasks().iterator(); iterator.hasNext();) {
			Task Task = iterator.next();
			Task.getSourcePeer().prioritizePeersToConsume(this.getPeers());
			for (Peer provider : this.getPeers()) {
				boolean isTaskRunning = provider.executeTask(Task);
				if (isTaskRunning) {
					this.addStartedTaskEvent(Task);
					iterator.remove();
					break;
				}
			}
		}
	}
	
}
