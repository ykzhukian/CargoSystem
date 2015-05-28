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

/**
 * 
 * @author CAB302 Than Nhat Huy Nguyen - 8781583
 *
 */

public class ManifestTests {
	//Implementation Here	
	private CargoManifest manifestTest;
	private ContainerCode valid_code_1;
	private ContainerCode valid_code_2;
	private ContainerCode valid_code_3;
	private FreightContainer container_1;
	private FreightContainer container_2;
	private FreightContainer container_3;
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
	public void noSuitableSpaceForContainerTest() throws ManifestException{
		manifestTest = new CargoManifest(1, 1, 1);
		manifestTest.loadContainer(container_1);
		manifestTest.loadContainer(container_1);
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
		valid_code_2 = new ContainerCode("FQUU8201776");
		valid_code_3 = new ContainerCode("HCTU7419009");
		container_1 = new GeneralGoodsContainer(valid_code_1, 15);
		container_2 = new GeneralGoodsContainer(valid_code_2, 30);
		container_3 = new DangerousGoodsContainer(valid_code_3, 30, 2);
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
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 * @throws InvalidContainerException 
	 */
	@Test(expected=Exception.class)
	public void shipsWeightExceedingLimitTest() throws ManifestException, InvalidContainerException{
		manifestTest.loadContainer(new GeneralGoodsContainer(valid_code_1, 140));
		manifestTest.loadContainer(container_1);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 * @throws InvalidCodeException 
	 */
	@Test
	public void unloadContainerTest() throws ManifestException, InvalidCodeException{
		manifestTest.loadContainer(container_1);
		manifestTest.unloadContainer(valid_code_1);
		assertTrue(manifestTest.whichStack(valid_code_1) == null);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 * @throws InvalidCodeException 
	 * @throws InvalidContainerException 
	 */
	@Test
	public void containerNotOnTopTest() throws ManifestException, InvalidCodeException, InvalidContainerException{
		manifestTest.loadContainer(container_2);
		manifestTest.loadContainer(container_1);
		manifestTest.unloadContainer(valid_code_1);
		assertTrue(manifestTest.howHigh(valid_code_1) == null);
		assertTrue(manifestTest.howHigh(valid_code_2) == 0);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 * @throws InvalidCodeException 
	 */
	@Test(expected=Exception.class)
	public void invalidCodeContainer() throws ManifestException, InvalidCodeException{
		valid_code_1 = new ContainerCode("mSCU66398711");
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 * @throws InvalidCodeException 
	 */
	@Test
	public void checkNullContainerCodeWhichStack() throws ManifestException, InvalidCodeException{
		manifestTest.loadContainer(container_2);
		assertTrue(manifestTest.whichStack(valid_code_1) == null);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 * @throws InvalidCodeException 
	 */
	@Test
	public void checkValidContanerCodeWhichStack() throws ManifestException, InvalidCodeException{
		manifestTest.loadContainer(container_2);
		manifestTest.loadContainer(container_3);
		assertTrue(manifestTest.whichStack(valid_code_3) == 1);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 * @throws InvalidCodeException 
	 */
	@Test
	public void checkNullContainerCodeHowHigh() throws ManifestException, InvalidCodeException{
		manifestTest.loadContainer(container_1);
		assertTrue(manifestTest.howHigh(valid_code_2) == null);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)}.
	 * @throws InvalidCodeException 
	 */
	@Test
	public void checkValidContanerCodeHowHigh() throws ManifestException, InvalidCodeException{
		manifestTest.loadContainer(container_1);
		manifestTest.loadContainer(container_2);
		manifestTest.loadContainer(container_3);
		System.out.println(manifestTest.howHigh(valid_code_2));
		System.out.println(valid_code_2.toString());
		assertTrue(manifestTest.howHigh(valid_code_2) == 1);
		
	}
	
	
	
	
	
}
