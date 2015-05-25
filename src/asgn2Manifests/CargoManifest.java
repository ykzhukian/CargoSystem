package asgn2Manifests;

import java.util.*;

import asgn2Codes.ContainerCode;
import asgn2Containers.FreightContainer;
import asgn2Exceptions.ManifestException;

/**
 * A class for managing a container ship's cargo manifest.  It
 * allows freight containers of various types to be loaded and
 * unloaded, within various constraints.
 * <p>
 * In particular, the ship's captain has set the following rules
 * for loading of new containers:
 * <ol>
 * <li>
 * New containers may be loaded only if doing so does not exceed
 * the ship's weight limit.
 * </li>
 * <li>
 * New containers are to be loaded as close to the bridge as possible.
 * (Stack number zero is nearest the bridge.)
 * </li>
 * <li>
 * A new container may be added to a stack only if doing so will
 * not exceed the maximum allowed stack height.
 * <li>
 * A new container may be loaded only if a container with the same
 * code is not already on board.
 * </li>
 * <li>
 * Stacks of containers must be homogeneous, i.e., each stack must
 * consist of containers of one type (general,
 * refrigerated or dangerous goods) only.
 * </li>
 * </ol>
 * <p>
 * Furthermore, since the containers are moved by an overhead
 * crane, a container can be unloaded only if it is on top of
 * a stack.
 *  
 * @author CAB302 Yunkai (Kian) Zhu n9253921
 * @version 1.0
 */
public class CargoManifest {
	
	int numStacks;
	int maxHeight;
	int maxWeight;
	// used to store all the containers loaded group by stack [[contain_1, contain_2], [contain_3], ...]
	ArrayList<ArrayList<FreightContainer>> containers; 
	// used to store the type of each stack which has container(s), type will clear when it has no container
	HashMap<Integer, String> stackType;
	// used to store the codes of loaded containers
	HashMap<String, Integer> codes;
	// used to store current weight
	int currentWeight;
	

	/**
	 * Constructs a new cargo manifest in preparation for a voyage.
	 * When a cargo manifest is constructed the specific cargo
	 * parameters for the voyage are set, including the number of
	 * stack spaces available on the deck (which depends on the deck configuration
	 * for the voyage), the maximum allowed height of any stack (which depends on
	 * the weather conditions expected for the
	 * voyage), and the total weight of containers allowed onboard (which depends
	 * on the amount of ballast and fuel being carried).
	 * 
	 * @param numStacks the number of stacks that can be accommodated on deck
	 * @param maxHeight the maximum allowable height of any stack
	 * @param maxWeight the maximum weight of containers allowed on board 
	 * (in tonnes)
	 * @throws ManifestException if negative numbers are given for any of the
	 * parameters
	 */
	public CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)
	throws ManifestException {
		if (numStacks < 0) {
			throw new ManifestException("Invalid Stacks Number");
		}
		if (maxHeight < 0) {
			throw new ManifestException("Invalid Max Height");
		}
		if (maxWeight < 0) {
			throw new ManifestException("Invalid Max Weight");
		}
		
		this.numStacks = numStacks;
		this.maxHeight = maxHeight;
		this.maxWeight = maxWeight;
		
		currentWeight = 0;
		containers = new ArrayList<ArrayList<FreightContainer>>();
		stackType = new HashMap<Integer, String>();
		codes = new HashMap<String, Integer>();
		
		for (int i = 0; i < numStacks; i++) {
			containers.add(new ArrayList<FreightContainer>()); // add empty list of element, can be used by get index later when uploading
		}
	}

	/**
	 * Loads a freight container onto the ship, provided that it can be
	 * accommodated within the five rules set by the captain.
	 * 
	 * @param newContainer the new freight container to be loaded
	 * @throws ManifestException if adding this container would exceed
	 * the ship's weight limit; if a container with the same code is
	 * already on board; or if no suitable space can be found for this
	 * container
	 */
	public void loadContainer(FreightContainer newContainer) throws ManifestException {
		if (newContainer.getGrossWeight() + currentWeight > maxWeight) {
			throw new ManifestException("Exceed the ship's weight limit");
		}
		System.out.println("current: " + currentWeight + " Max: "+ maxWeight);
		
		if (codes.containsKey(newContainer.getCode().toString())) {
			throw new ManifestException("A duplicate code has been found");
		}
		
		for (int i = 0; i < numStacks; i++) {
			if (stackType.get(i) != null) {
				if (stackType.get(i).equals(newContainer.getType())) {
					if (containers.get(i).size() < maxHeight) {
						containers.get(i).add(newContainer);
						codes.put(newContainer.getCode().toString(), i);
						currentWeight += newContainer.getGrossWeight();
						break;
					} else if (i == numStacks - 1) { // if there's no more space
						throw new ManifestException("No suitable space can be found for this container");
					}
				} else if (i == numStacks - 1) {  // if there's no matching type
					throw new ManifestException("No suitable space can be found for this container");
				}
			} else {
				stackType.put(i, newContainer.getType()); // create a stack type to that stack
				containers.get(i).add(newContainer);	// add container to that stack
				codes.put(newContainer.getCode().toString(), i); // add code to local
				currentWeight += newContainer.getGrossWeight();
				break;
			}
			
		}
	
	}


	/**
	 * Unloads a particular container from the ship, provided that
	 * it is accessible (i.e., on top of a stack).
	 * 
	 * @param containerId the code of the container to be unloaded
	 * @throws ManifestException if the container is not accessible because
	 * it's not on the top of a stack (including the case where it's not on board
	 * the ship at all)
	 */
	public void unloadContainer(ContainerCode containerId) throws ManifestException {
		if (!codes.containsKey(containerId.toString())) {
			throw new ManifestException("No such container");
		}
		int stackNumber = codes.get(containerId.toString());
		FreightContainer topContainer = containers.get(stackNumber).get(containers.get(stackNumber).size() - 1);
		if (topContainer.getCode().toString().equals(containerId.toString())) {
			containers.get(stackNumber).remove(containers.get(stackNumber).size() - 1);	// remove it from containers
			currentWeight -= topContainer.getGrossWeight();				// minus the current weight
			if (containers.get(stackNumber).size() == 0) {
				stackType.remove(codes.get(containerId.toString()));		// if it is the last container, remove the type of that stack
			}
			codes.remove(containerId.toString());
		} else {
			throw new ManifestException("The container is not on the top of that stack");
		}
	}

	
	/**
	 * Returns which stack holds a particular container, if any.  The
	 * container of interest is identified by its unique
	 * code.  Constant <code>null</code> is returned if the container is
	 * not on board.
	 * 
	 * @param queryContainer the container code for the container of interest
	 * @return the number of the stack with the container in it, or <code>null</code>
	 * if the container is not on board
	 */
	public Integer whichStack(ContainerCode queryContainer) {
		if (!codes.containsKey(queryContainer.toString())) {
			return null;
		} else {
			return codes.get(queryContainer.toString());
		}
	}

	
	/**
	 * Returns how high in its stack a particular container is.  The container of
	 * interest is identified by its unique code.  Height is measured in the
	 * number of containers, counting from zero.  Thus the container at the bottom
	 * of a stack is at "height" 0, the container above it is at height 1, and so on.
	 * Constant <code>null</code> is returned if the container is
	 * not on board.
	 * 
	 * @param queryContainer the container code for the container of interest
	 * @return the container's height in the stack, or <code>null</code>
	 * if the container is not on board
	 */
	public Integer howHigh(ContainerCode queryContainer) {
		if (!codes.containsKey(queryContainer.toString())) {
			return null;
		} else {
			int stackNumber = codes.get(queryContainer.toString());
			int index = containers.get(stackNumber).indexOf(queryContainer.toString());
			return index;
		}
	}


	/**
	 * Returns the contents of a particular stack as an array,
	 * starting with the bottommost container at position zero in the array.
	 * 
	 * @param stackNo the number of the stack of interest
	 * @return the stack's freight containers as an array
	 * @throws ManifestException if there is no such stack on the ship
	 */
	public FreightContainer[] toArray(Integer stackNo) throws ManifestException {
		if (stackNo < 0 || stackNo > numStacks - 1) {
			throw new ManifestException("No such stack");
		}
		FreightContainer[] array = new FreightContainer[containers.get(stackNo).size()];
		for (int i = 0; i < containers.get(stackNo).size(); i++) {
			array[i] = containers.get(stackNo).get(i);
		}
		return array;
	}

	
	/* ***** toString methods added to support the GUI ***** */
	
	public String toString(ContainerCode toFind) {
		String toReturn = "";
		for (int i = 0; i < containers.size(); ++i) {
			ArrayList<FreightContainer> currentStack = containers.get(i);
			toReturn += "|";
			for (int j = 0; j < currentStack.size(); ++j) {
				if (toFind != null && currentStack.get(j).getCode().toString().equals(toFind.toString()))
					toReturn += "|*" + currentStack.get(j).getCode().toString() + "*|";
				else
					toReturn += "| " + currentStack.get(j).getCode().toString() + " |";
			}
			if (currentStack.size() == 0)
				toReturn +=  "|  ||\n";
			else
				toReturn += "|\n";
		}
		return toReturn;
	}

	@Override
	public String toString() {
		return toString(null);
	}
	
	public int getStackNumber() {
		return numStacks;
	}
}
