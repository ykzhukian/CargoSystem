package asgn2Tests;

/* Some valid container codes used in the tests below:
 * INKU2633836
 * KOCU8090115
 * MSCU6639871
 * CSQU3054389
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.InvalidCodeException;
import asgn2Exceptions.InvalidContainerException;

/**
 * @author Yunkai(Kian) Zhu n9253921
 *
 */
public class ContainerTests {
	//Implementation Here - includes tests for ContainerCode and for the actual container classes. 
	
	/*****************************************Container Code Tests***************************************************/
	private ContainerCode code_validatoin_test;
	private ContainerCode valid_code_1;
	private ContainerCode valid_code_2;
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String code)}.
	 * Test a valid code(MSCU6639871) for following test
	 */
	@Test
	public void validCode() throws InvalidCodeException{
		code_validatoin_test = new ContainerCode("MSCU6639871");
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String code)}.
	 * Add 0s will not change the sum of numbers but length
	 */
	@Test(expected=Exception.class)
	public void lengthTest() throws InvalidCodeException{
		code_validatoin_test = new ContainerCode("MSCU0006639871");
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String code)}.
	 */
	@Test(expected=Exception.class)
	public void nullCode() throws InvalidCodeException{
		code_validatoin_test = new ContainerCode(null);
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String code)}.
	 */
	@Test(expected=Exception.class)
	public void emptyCode() throws InvalidCodeException{
		code_validatoin_test = new ContainerCode("");
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String code)}.
	 */
	@Test(expected=Exception.class)
	public void lowCaseLetters() throws InvalidCodeException{
		code_validatoin_test = new ContainerCode("mscU6639871");
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String code)}.
	 */
	@Test(expected=Exception.class)
	public void categoryIdentifierLowCase() throws InvalidCodeException{
		code_validatoin_test = new ContainerCode("MSCu6639871");
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String code)}.
	 * T is 1 less than U so changed check digit from 1 to 0 for test
	 */
	@Test(expected=Exception.class)
	public void categoryIdentifierChange() throws InvalidCodeException{
		code_validatoin_test = new ContainerCode("MSCT6639870");
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String code)}.
	 * removed 3 from code thus the check digit comes to 8
	 */
	@Test(expected=Exception.class)
	public void checkIfSixDigits() throws InvalidCodeException{
		code_validatoin_test = new ContainerCode("MSCU669878");
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String code)}.
	 * changed the Check Digit to another number
	 */
	@Test(expected=Exception.class)
	public void checkCheckDigit() throws InvalidCodeException{
		code_validatoin_test = new ContainerCode("MSCU6639870");
	}
	
	/**
	 * @throws InvalidCodeException 
	 */
	@Before
	public void setUpValidationTest() throws InvalidCodeException {
		valid_code_1 = new ContainerCode("MSCU6639871");
		valid_code_2 = new ContainerCode("KOCU8090115");
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#equals(Object obj)}.
	 */
	@Test
	public void checkEqualTrue() {
		assertTrue(valid_code_1.equals(valid_code_1));
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#equals(Object obj)}.
	 */
	@Test
	public void checkEqualFalse() {
		assertFalse(valid_code_1.equals(valid_code_2));
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#toString)}.
	 */
	@Test
	public void checkToStringTrue() {
		assertTrue(valid_code_1.toString() == "MSCU6639871");
	}
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#toString)}.
	 */
	@Test
	public void checkToStringFalse() {
		assertFalse(valid_code_1.toString() == "KOCU8090115");
	}
	
	/*****************************************Freight Container Tests***************************************************/
	// GeneralGoodsContainer
	
	private GeneralGoodsContainer generalGoodsContainerTest;
	private RefrigeratedContainer refrigeratedContainer;
	private DangerousGoodsContainer dangerousGoodsContainer;
	private int grossWeight_1 = 20;
	private int temperature = 5;
	private int category = 5;
	
	/**
	 * Test method for {@link asgn2Cotainers.GeneralGoodsContainer#GeneralGoodsContainer(ContainerCode code, Integer grossWeight)}.
	 */
	@Test(expected=Exception.class)
	public void invalidGeneralGoodsContainerCode() throws InvalidContainerException{
		generalGoodsContainerTest = new GeneralGoodsContainer(null, grossWeight_1);
	}
	
	/**
	 * Test method for {@link asgn2Cotainers.GeneralGoodsContainers#GeneralGoodsContainer(ContainerCode code, Integer grossWeight)}.
	 */
	@Test(expected=Exception.class)
	public void invalidGeneralGoodsContainerGrossWeight() throws InvalidContainerException{
		generalGoodsContainerTest = new GeneralGoodsContainer(valid_code_1, 3);
	}
	
	/**
	 * @throws InvalidContainerException 
	 */
	@Before
	public void setUpGeneralGoodsContainerTest() throws InvalidContainerException {
		generalGoodsContainerTest = new GeneralGoodsContainer(valid_code_1, grossWeight_1);
	}
	
	/**
	 * Test method for {@link asgn2Cotainers.FreightContainers#getCode()}.
	 */
	@Test
	public void GeneralGoodsContainerGetCode() throws InvalidContainerException{
		assertTrue(generalGoodsContainerTest.getCode().equals(valid_code_1));	
	}
	
	/**
	 * Test method for {@link asgn2Cotainers.FreightContainers#getGrossWeight()}.
	 */
	@Test
	public void GeneralGoodsContainerGetGrossWeight() throws InvalidContainerException{
		assertTrue(generalGoodsContainerTest.getGrossWeight().equals(grossWeight_1));
	}
	
	// RefrigeratedContainer
	/**
	 * Test method for {@link asgn2Cotainers.RefrigeratedContainer#RefrigeratedContainer(ContainerCode code, Integer grossWeight, Integer temperature)}.
	 */
	@Test(expected=Exception.class)
	public void invalidRefrigeratedContainerGrossWeight() throws InvalidContainerException{
		refrigeratedContainer = new RefrigeratedContainer(valid_code_1, 50, temperature);
	}
	
	/**
	 * @throws InvalidContainerException 
	 */
	@Before
	public void setUpRefrigeratedContainerTest() throws InvalidContainerException {
		refrigeratedContainer = new RefrigeratedContainer(valid_code_1, grossWeight_1, temperature);
	}
	
	/**
	 * Test method for {@link asgn2Cotainers.RefrigeratedContainer#getTemperature()}.
	 */
	@Test
	public void RefrigeratedContainerGetTemperature() throws InvalidContainerException{
		assertTrue(refrigeratedContainer.getTemperature().equals(temperature));
	}
	
	/**
	 * Test method for {@link asgn2Cotainers.RefrigeratedContainer#setTemperature()}.
	 */
	@Test
	public void RefrigeratedContainerSetTemperature() throws InvalidContainerException{
		refrigeratedContainer.setTemperature(10);
		assertTrue(refrigeratedContainer.getTemperature().equals(10));
	}
	
	// DangerousGoodsContainer
	/**
	 * Test method for {@link asgn2Cotainers.DangerousGoodsContainer#DangerousGoodsContainer(ContainerCode code, Integer grossWeight, Integer category)}.
	 */
	@Test(expected=Exception.class)
	public void invalidDangerousGoodsContainerCategory() throws InvalidContainerException{
		dangerousGoodsContainer = new DangerousGoodsContainer(valid_code_1, grossWeight_1, 10);
	}
	
	/**
	 * Test method for {@link asgn2Cotainers.DangerousGoodsContainer#DangerousGoodsContainer(ContainerCode code, Integer grossWeight, Integer category)}.
	 */
	@Test(expected=Exception.class)
	public void invalidDangerousGoodsContainerGrossWeight() throws InvalidContainerException{
		dangerousGoodsContainer = new DangerousGoodsContainer(valid_code_1, 50, category);
	}
	
	/**
	 * @throws InvalidContainerException 
	 */
	@Before
	public void setUpDangerousGoodsContainerTest() throws InvalidContainerException {
		dangerousGoodsContainer = new DangerousGoodsContainer(valid_code_1, grossWeight_1, category);
	}
	
	/**
	 * Test method for {@link asgn2Cotainers.RefrigeratedContainer#setTemperature()}.
	 */
	@Test
	public void DangerousGoodsContainerGetCategory() throws InvalidContainerException{
		assertTrue(dangerousGoodsContainer.getCategory().equals(category));
	}
	
	
	
}
