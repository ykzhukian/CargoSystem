package asgn2Tests;

/* Some valid container codes used in the tests below:
 * INKU2633836
 * KOCU8090115
 * MSCU6639871
 * CSQU3054389
 * QUTU7200318
 * IBMU4882351
 */



import org.junit.Before;
import org.junit.Test;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.InvalidCodeException;
import asgn2Exceptions.InvalidContainerException;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;
import static org.junit.Assert.*;

public class ManifestTests {
	//Implementation Here	
	private CargoManifest manifestTest;
	private ContainerCode valid_code_1;
	private FreightContainer container_1;
	private static final int NUM_STACKS = 150;
	private static final int MAX_HEIGHT = 3;
	private static final int MAX_WEIGHT = 150;
	

	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 */
	@Test(expected=Exception.class)
	public void invalidNumStacks() throws ManifestException{
		manifestTest = new CargoManifest(-1, MAX_WEIGHT, MAX_HEIGHT);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 */
	@Test(expected=Exception.class)
	public void invalidMaxWeight() throws ManifestException{
		manifestTest = new CargoManifest(NUM_STACKS, -1, MAX_HEIGHT);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 */
	@Test(expected=Exception.class)
	public void invalidMaxHeight() throws ManifestException{
		manifestTest = new CargoManifest(NUM_STACKS, MAX_WEIGHT, -1);
	}
	
	
	/**
	 * @throws InvalidCodeException, ManifestException
	 * @throws InvalidContainerException 
	 */
	@Before
	public void setUp() throws InvalidCodeException, ManifestException, InvalidContainerException {
		valid_code_1 = new ContainerCode("MSCU6639871");
		container_1 = new GeneralGoodsContainer(valid_code_1, 15);
		manifestTest = new CargoManifest(NUM_STACKS, MAX_HEIGHT, MAX_WEIGHT);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 */
	@Test(expected=Exception.class)
	public void duplicateTest() throws ManifestException{
		manifestTest.loadContainer(container_1);
		manifestTest.loadContainer(container_1);
	}
	

	
}
