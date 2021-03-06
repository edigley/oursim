package com.edigley.oursim.entities;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Edigley P. Fraga, edigley@lsd.ufcg.edu.br
 * @since 22/04/2010
 * 
 */
public class Machine implements Comparable<Machine> {

	/**
	 * The name of this machine. This name must be unique.
	 */
	private String name;

	private long id;

	private static long nextMachineId = 0;

	private static final int DEFAULT_PROCESSOR_INDEX = 0;

	/**
	 * The processor owned by this machine.
	 * 
	 * TODO: All processor under the same Machine must have the same speed? For
	 * Shared Memory Multiprocessors (SMPs), it is generally assumed that all
	 * processor have the same rating.
	 */
	private List<Processor> processors;

	private String label;

	/**
	 * An special constructor for a monoprocessed machine.
	 * 
	 * @param name
	 *            the machine's name.
	 * @param processorSpeed
	 *            the rating of the only processor of this machine.
	 */
	public Machine(String name, long processorSpeed) {
		this(name, processorSpeed, 1);
	}

	/**
	 * 
	 * An generic constructor for a machine.
	 * 
	 * @param name
	 *            the machine's name.
	 * @param processorSpeed
	 *            the rating of all the processor of this machine.
	 * @param numOfProcessors
	 *            The number of processor of this machine.
	 * @throws IllegalArgumentException
	 *             in case <code>numProcessor < 1 </code>
	 */
	public Machine(String name, long processorSpeed, int numOfProcessors) throws IllegalArgumentException {
		assert numOfProcessors >= 1;
		if (numOfProcessors < 1) {
			throw new IllegalArgumentException("There must be at least one processor in a machine.");
		}

		this.name = name;
		this.processors = new ArrayList<Processor>();

		for (int i = 0; i < numOfProcessors; i++) {
			addProcessor(processorSpeed);
		}

		this.id = nextMachineId;
		nextMachineId++;

	}

	public Machine(String name, long processorSpeed, int numOfProcessors, String label) {
		this(name, processorSpeed, numOfProcessors);
		this.label = label;
	}
	
	private void addProcessor(long speed) {
		int processorId = this.processors.size();
		this.processors.add(new Processor(processorId, speed, this));
	}

	/**
	 * @return The name of this machine.
	 */
	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the number of processors of this machine.
	 */
	public int getNumberOfProcessors() {
		return this.processors.size();
	}

	/**
	 * @return the number of free processors of this machine.
	 */
	public int getNumberOfFreeProcessors() {
		int numFreeProcessors = 0;
		for (Processor processor : processors) {
			numFreeProcessors += processor.isBusy() ? 0 : 1;
		}
		return numFreeProcessors;
	}

	public Processor getFreeProcessor() {
		for (Processor processor : processors) {
			if (!processor.isBusy()) {
				return processor;
			}
		}
		return null;
	}

	public List<Processor> getFreeProcessors(int nOfProcessors) {
		assert nOfProcessors > 0;
		if (nOfProcessors <= getNumberOfFreeProcessors()) {
			List<Processor> freeProcessors = new ArrayList<>();
			for (Processor processor : processors) {
				if (!processor.isBusy() && freeProcessors.size() < nOfProcessors) {
					freeProcessors.add(processor);
				}
			}
			assert freeProcessors.size() == nOfProcessors;
			return freeProcessors;
		}
		assert nOfProcessors > getNumberOfFreeProcessors();
		return null;
	}

	/**
	 * 
	 * Gets the main processor of a machine.
	 * 
	 * @return the default processor of this machine.
	 */
	public Processor getDefaultProcessor() {
		return processors.get(DEFAULT_PROCESSOR_INDEX);
	}

	public int compareTo(Machine o) {
		// TODO: definir critério de comparação.
		return (int) (this.id - o.id);
	}

	public boolean isAllProcessorsFree() {
		return this.getNumberOfFreeProcessors() == this.getNumberOfProcessors();
	}

	public boolean isAnyProcessorBusy() {
		return !isAllProcessorsFree();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)

				.append("name", name)

				.append("#processors", processors.size())

				.append("processors_rating", getDefaultProcessor().getSpeed())

				.toString();
	}

}
