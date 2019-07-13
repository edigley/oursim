package com.edigley.oursim.io.input.workload;

import com.edigley.oursim.entities.Job;
import com.edigley.oursim.io.input.InputAbstract;

/**
 * 
 * An convenient Class to deal with generic workloads.
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 18/05/2010
 * 
 */
public abstract class WorkloadAbstract extends InputAbstract<Job> implements Workload {

	public boolean merge(Workload other) {
		while (other.peek() != null) {
			// TODO: this.inputs.addLast(other.poll());
			this.inputs.add((Job)other.poll());
		}
		return true;
	}
	
	public void stop(){
		this.inputs.clear();
	}

}
