package it.polito.ezgas.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.dto.PriceReportDto;

public class PriceReportDtoTest {

	@Test
	public void testPriceReportDtoConstructor() {
		
		Integer gasStationId = 1;
		Double dieselPrice = 1.42;
		Double superPrice = 1.57;
		Double superPlusPrice = 1.70;
		Double gasPrice = 0.97;
		Double methanePrice = 0.99;
		Double premiumDieselPrice = 1.50;
		Integer userId = 1;
		
		PriceReportDto prDto = new PriceReportDto(gasStationId, dieselPrice, superPrice, superPlusPrice, 
				gasPrice, methanePrice, premiumDieselPrice, userId);
		
		assertTrue(prDto.getGasStationId() == gasStationId && prDto.getDieselPrice() == dieselPrice 
				&& prDto.getSuperPrice() == superPrice && prDto.getSuperPlusPrice() == superPlusPrice 
				&& prDto.getGasPrice() == gasPrice && prDto.getMethanePrice() == methanePrice 
				&& prDto.getPremiumDieselPrice() == premiumDieselPrice && prDto.getUserId() == userId);		
		
	}
	
	
	@Test
	public void testGasStationIdPriceReportDto() {
		
		Integer gasStationIdPositive = 1;

		PriceReportDto prDtoPositive = new PriceReportDto();
		prDtoPositive.setGasStationId(gasStationIdPositive);
		
		assertTrue(prDtoPositive.getGasStationId() == gasStationIdPositive);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer gasStationIdNegative = -1;
		 * PriceReportDto prDtoNegative = new PriceReportDto();
		 * prDtoNegative.setGasStationId(gasStationIdNegative);
		 * assertThrows(InvalidGasStationException.class, () -> { prDtoNegative.getGasStationId(); });
		 */
		
	}

	
	@Test
	public void testDieselPriceReportDto() {
		
		Double dieselPricePositve = 1.37;
		

		PriceReportDto prDtoPositive = new PriceReportDto();
		prDtoPositive.setDieselPrice(dieselPricePositve);
		
		assertTrue(prDtoPositive.getDieselPrice() == dieselPricePositve);
		

		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double dieselPriceNegative = -1.37;
		 * PriceReportDto prDtoNegative = new PriceReportDto();
		 * prDtoNegative.setDieselPrice(dieselPriceNegative);
		 * assertThrows(PriceException.class, () -> { prDtoNegative.getDieselPrice(); });
		 */

	}
	
	
	@Test
	public void testSuperPriceReportDto() {
		
		Double superPricePositve = 1.37;
		
		PriceReportDto prDtoPositive = new PriceReportDto();
		prDtoPositive.setSuperPrice(superPricePositve);
		
		assertTrue(prDtoPositive.getSuperPrice() == superPricePositve);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double superPriceNegative = -1.37;
		 * PriceReportDto prDtoNegative = new PriceReportDto();
		 * prDtoNegative.setSuperPrice(superPriceNegative);
		 * assertThrows(PriceException.class, () -> { prDtoNegative.getSuperPrice(); });
		 */
		
	}
	
	
	@Test
	public void testSuperPlusPriceReportDto() {
		
		Double superPlusPricePositve = 1.37;
		
		PriceReportDto prDtoPositive = new PriceReportDto();
		prDtoPositive.setSuperPlusPrice(superPlusPricePositve);
		
		assertTrue(prDtoPositive.getSuperPlusPrice() == superPlusPricePositve);

		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double superPlusPriceNegative = -1.37;
		 * PriceReportDto prDtoNegative = new PriceReportDto();
		 * prDtoNegative.setSuperPlusPrice(superPlusPriceNegative);
		 * assertThrows(PriceException.class, () -> { prDtoNegative.getSuperPlusPrice(); });
		 */
		
	}
	
	
	@Test
	public void testGasPriceReportDto() {
		
		Double gasPricePositve = 1.37;
		
		PriceReportDto prDtoPositive = new PriceReportDto();
		prDtoPositive.setGasPrice(gasPricePositve);
		
		assertTrue(prDtoPositive.getGasPrice() == gasPricePositve);

		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double gasPriceNegative = -1.37;
		 * PriceReportDto prDtoNegative = new PriceReportDto();
		 * prDtoNegative.setGasPrice(gasPriceNegative);
		 * assertThrows(PriceException.class, () -> { prDtoNegative.getGasPrice(); });
		 */
		
	}
	
	
	@Test
	public void testMethanePriceReportDto() {
		
		Double methanePricePositve = 1.37;
		
		PriceReportDto prDtoPositive = new PriceReportDto();
		prDtoPositive.setMethanePrice(methanePricePositve);
		
		assertTrue(prDtoPositive.getMethanePrice() == methanePricePositve);


		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double methanePriceNegative = -1.37;
		 * PriceReportDto prDtoNegative = new PriceReportDto();
		 * prDtoNegative.setMethanePrice(methanePriceNegative);
		 * assertThrows(PriceException.class, () -> { prDtoNegative.getMethanePrice(); });
		 */
		
	}
	
	
	@Test
	public void testPremiumDieselPriceReportDto() {
		
		Double premiumDieselPricePositve = 1.37;
		
		PriceReportDto prDtoPositive = new PriceReportDto();
		prDtoPositive.setPremiumDieselPrice(premiumDieselPricePositve);
		
		assertTrue(prDtoPositive.getPremiumDieselPrice() == premiumDieselPricePositve);

		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double premiumDieselPriceNegative = -1.37;
		 * PriceReportDto prDtoNegative = new PriceReportDto();
		 * prDtoNegative.setPremiumDieselPrice(premiumDieselPriceNegative);
		 * assertThrows(PriceException.class, () -> { prDtoNegative.getPremiumDieselPrice(); });
		 */
		
	}
	
	
	@Test
	public void testUserIdPriceReportDto() {
		
		Integer userIdPositive = 1;
		
		PriceReportDto prDtoPositive = new PriceReportDto();
		prDtoPositive.setUserId(userIdPositive);
		
		assertTrue(prDtoPositive.getUserId() == userIdPositive);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer userIdNegative = -1;
		 * PriceReportDto prDtoNegative = new PriceReportDto();
		 * prDtoNegative.setUserId(userIdNegative);
		 * assertThrows(PriceException.class, () -> { prDtoNegative.getUserId(); });
		 */
		
	}
	
	
}
