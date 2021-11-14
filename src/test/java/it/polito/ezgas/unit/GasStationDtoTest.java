package it.polito.ezgas.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;

public class GasStationDtoTest {
	
	@Test
	public void testGasStationIdDto() {
		
		Integer gasStationIdPositive = 1;

		GasStationDto gsDtoPositive = new GasStationDto();
		gsDtoPositive.setGasStationId(gasStationIdPositive);
		
		assertTrue(gsDtoPositive.getGasStationId() == gasStationIdPositive);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer gasStationIdNegative = -1;
		 * GasStationDto gsDtoNegative = new GasStationDto();
		 * gsDtoNegative.setGasStationId(gasStationIdNegative);
		 * assertThrows(InvalidGasStationException.class, () -> { gsDtoNegative.getGasStationId(); });
		 */
		
	}
	
	
	@Test
	public void testGasStationNameDto() {
		
		String gasStationName = "Torino 2";

		GasStationDto gsDto = new GasStationDto();
		gsDto.setGasStationName(gasStationName);
		
		assertTrue(gsDto.getGasStationName() == gasStationName);
		
	}
	
	
	@Test
	public void testGasStationAddressDto() {
		
		String gasStationAddress = "Porta Susa Torino Piemonte Italia";

		GasStationDto gsDto = new GasStationDto();
		gsDto.setGasStationAddress(gasStationAddress);
		
		assertTrue(gsDto.getGasStationAddress() == gasStationAddress);
		
	}
	
	
	@Test
	public void testHasDieselDto() {
		
		boolean gasolineTypeYes = true;
		boolean gasolineTypeNo = false;

		GasStationDto gsDtoYes = new GasStationDto();
		gsDtoYes.setHasDiesel(gasolineTypeYes);
		GasStationDto gsDtoNo = new GasStationDto();
		gsDtoNo.setHasDiesel(gasolineTypeNo);
	
		assertTrue(gsDtoYes.getHasDiesel() == gasolineTypeYes);
		assertTrue(gsDtoNo.getHasDiesel() == gasolineTypeNo);
		
	}
	
	
	@Test
	public void testHasGasDto() {
		
		boolean gasolineTypeYes = true;
		boolean gasolineTypeNo = false;

		GasStationDto gsDtoYes = new GasStationDto();
		gsDtoYes.setHasGas(gasolineTypeYes);
		GasStationDto gsDtoNo = new GasStationDto();
		gsDtoNo.setHasGas(gasolineTypeNo);
		
		assertTrue(gsDtoYes.getHasGas() == gasolineTypeYes);
		assertTrue(gsDtoNo.getHasGas() == gasolineTypeNo);
		
	}

	
	@Test
	public void testHasSuperDto() {
		
		boolean gasolineTypeYes = true;
		boolean gasolineTypeNo = false;

		GasStationDto gsDtoYes = new GasStationDto();
		gsDtoYes.setHasSuper(gasolineTypeYes);
		GasStationDto gsDtoNo = new GasStationDto();
		gsDtoNo.setHasSuper(gasolineTypeNo);

		assertTrue(gsDtoYes.getHasSuper() == gasolineTypeYes);
		assertTrue(gsDtoNo.getHasSuper() == gasolineTypeNo);
		
	}
	
	
	@Test
	public void testHasSuperPlusDto() {
		
		boolean gasolineTypeYes = true;
		boolean gasolineTypeNo = false;

		GasStationDto gsDtoYes = new GasStationDto();
		gsDtoYes.setHasSuperPlus(gasolineTypeYes);
		GasStationDto gsDtoNo = new GasStationDto();
		gsDtoNo.setHasSuperPlus(gasolineTypeNo);

		assertTrue(gsDtoYes.getHasSuperPlus() == gasolineTypeYes);
		assertTrue(gsDtoNo.getHasSuperPlus() == gasolineTypeNo);
		
	}
	
	
	@Test
	public void testHasMethaneDto() {
		
		boolean gasolineTypeYes = true;
		boolean gasolineTypeNo = false;

		GasStationDto gsDtoYes = new GasStationDto();
		gsDtoYes.setHasMethane(gasolineTypeYes);
		GasStationDto gsDtoNo = new GasStationDto();
		gsDtoNo.setHasMethane(gasolineTypeNo);

		assertTrue(gsDtoYes.getHasMethane() == gasolineTypeYes);
		assertTrue(gsDtoNo.getHasMethane() == gasolineTypeNo);
		
	}
	
	
	@Test
	public void testHasPremiumDieselDto() {
		
		boolean gasolineTypeYes = true;
		boolean gasolineTypeNo = false;

		GasStationDto gsDtoYes = new GasStationDto();
		gsDtoYes.setHasPremiumDiesel(gasolineTypeYes);
		GasStationDto gsDtoNo = new GasStationDto();
		gsDtoNo.setHasPremiumDiesel(gasolineTypeNo);

		assertTrue(gsDtoYes.getHasPremiumDiesel() == gasolineTypeYes);
		assertTrue(gsDtoNo.getHasPremiumDiesel() == gasolineTypeNo);
		
	}
	
	
	@Test
	public void testCarSharingDto() {
		
		String carSharing = "Enjoy";
		
		GasStationDto gsDtoYes = new GasStationDto();
		gsDtoYes.setCarSharing(carSharing);
		
		assertTrue(gsDtoYes.getCarSharing() == carSharing);
		
	}

	
	@Test
	public void testLatitudeDto() {
		
		double latitude = 45.066667;

		GasStationDto gsDto = new GasStationDto();
		gsDto.setLat(latitude);
		
		assertTrue(gsDto.getLat() == latitude);
		
		
		/*
		 * These asserts will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * double latitudeOverMax = 245.066667;
		 * gsDto.setLat(latitudeOverMax);
		 * assertThrows(GPSDataException.class, ()-> { gsDto.getLat(); });
		 * 
		 * double latitudeBelowMin = -200.066667;
		 * gsDto.setLat(latitudeBelowMin);
		 * assertThrows(GPSDataException.class, ()-> { gsDto.getLat(); });
		 */
				
	}
	
	
	@Test
	public void testLongitudeDto() {
		
		double longitude = 7.666667;
		
		GasStationDto gsDto = new GasStationDto();
		gsDto.setLon(longitude);
		
		assertTrue(gsDto.getLon() == longitude);

		
		/*
		 * These asserts will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * double longitudeOverMax = 245.066667;
		 * gsDto.setLon(longitudeOverMax);
		 * assertThrows(GPSDataException.class, ()-> { gsDto.getLon(); });
		 * 
		 * double longitudeBelowMin = -200.066667;
		 * gsDto.setLon(longitudeBelowMin);
		 * assertThrows(GPSDataException.class, ()-> { gsDto.getLon(); });
		 */
		
	}
	
	
	@Test
	public void testDieselPriceDto() {
		
		Double dieselPricePositve = 1.37;

		GasStationDto gsDtoPositive = new GasStationDto();
		gsDtoPositive.setDieselPrice(dieselPricePositve);
		
		assertTrue(gsDtoPositive.getDieselPrice() == dieselPricePositve);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double dieselPriceNegative = -1.37;
		 * GasStationDto gsDtoNegative = new GasStationDto();
		 * gsDtoNegative.setDieselPrice(dieselPriceNegative);
		 * assertThrows(PriceException.class, () -> { gsDtoNegative.getDieselPrice(); });
		 */
		
	}
	
	
	@Test
	public void testSuperPriceDto() {
		
		Double superPricePositve = 1.37;
		
		GasStationDto gsDtoPositive = new GasStationDto();
		gsDtoPositive.setSuperPrice(superPricePositve);
		
		assertTrue(gsDtoPositive.getSuperPrice() == superPricePositve);

		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double superPriceNegative = -1.37;
		 * GasStationDto gsDtoNegative = new GasStationDto();
		 * gsDtoNegative.setSuperPrice(superPriceNegative);
		 * assertThrows(PriceException.class, () -> { gsDtoNegative.getSuperPrice(); });
		 */
		
	}
	
	
	@Test
	public void testSuperPlusPriceDto() {
		
		Double superPlusPricePositve = 1.37;

		GasStationDto gsDtoPositive = new GasStationDto();
		gsDtoPositive.setSuperPlusPrice(superPlusPricePositve);		
		
		assertTrue(gsDtoPositive.getSuperPlusPrice() == superPlusPricePositve);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double superPlusPriceNegative = -1.37;
		 * GasStationDto gsDtoNegative = new GasStationDto();
		 * gsDtoNegative.setSuperPlusPrice(superPlusPriceNegative);
		 * assertThrows(PriceException.class, () -> { gsDtoNegative.getSuperPlusPrice(); });
		 */
		
	}
	
	
	@Test
	public void testGasPriceDto() {
		
		Double gasPricePositve = 1.37;
		
		GasStationDto gsDtoPositive = new GasStationDto();
		gsDtoPositive.setGasPrice(gasPricePositve);
		
		assertTrue(gsDtoPositive.getGasPrice() == gasPricePositve);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double gasPriceNegative = -1.37;
		 * GasStationDto gsDtoNegative = new GasStationDto();
		 * gsDtoNegative.setGasPrice(gasPriceNegative);
		 * assertThrows(PriceException.class, () -> { gsDtoNegative.getGasPrice(); });
		 */
		
	}
	
	
	@Test
	public void testMethanePriceDto() {
		
		Double methanePricePositve = 1.37;

		GasStationDto gsDtoPositive = new GasStationDto();
		gsDtoPositive.setMethanePrice(methanePricePositve);		
		
		assertTrue(gsDtoPositive.getMethanePrice() == methanePricePositve);

		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double methanePriceNegative = -1.37;
		 * GasStationDto gsDtoNegative = new GasStationDto();
		 * gsDtoNegative.setMethanePrice(methanePriceNegative);
		 * assertThrows(PriceException.class, () -> { gsDtoNegative.getMethanePrice(); });
		 */
		
	}
	
	
	@Test
	public void testPremiumDieselPriceDto() {
		
		Double premiumDieselPricePositve = 1.37;
		
		GasStationDto gsDtoPositive = new GasStationDto();
		gsDtoPositive.setPremiumDieselPrice(premiumDieselPricePositve);
		
		assertTrue(gsDtoPositive.getPremiumDieselPrice() == premiumDieselPricePositve);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double premiumDieselPriceNegative = -1.37;
		 * GasStationDto gsDtoNegative = new GasStationDto();
		 * gsDtoNegative.setPremiumDieselPrice(premiumDieselPriceNegative);
		 * assertThrows(PriceException.class, () -> { gsDtoNegative.getPremiumDieselPrice(); });
		 */
		
	}
	
	
	@Test
	public void testUserDto() {
		
		UserDto uDto = new UserDto();

		GasStationDto gsDto = new GasStationDto();
		gsDto.setUserDto(uDto);
		
		assertTrue(gsDto.getUserDto() == uDto);
		
	}
	
	
	@Test
	public void testReportUserDto() {
		
		Integer reportUserPositive = 1;
	
		GasStationDto gsDtoPositive = new GasStationDto();
		gsDtoPositive.setReportUser(reportUserPositive);
		
		assertTrue(gsDtoPositive.getReportUser() == reportUserPositive);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer reportUserNegative = -1;
		 * GasStationDto gsDtoNegative = new GasStationDto();
		 * gsDtoNegative.setReportUser(reportUserNegative);
		 * assertThrows(PriceException.class, () -> { gsDtoNegative.getReportUser(); });
		 */
		
	}
	
	
	@Test
	public void testReportDependability() {
		
		Integer reportDependability = 75;

		GasStationDto gsDto = new GasStationDto();
		gsDto.setReportDependability(reportDependability);
		
		assertTrue(gsDto.getReportDependability() == reportDependability);
		

		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer reportDependability = -75;
		 * gsDto.setReportDependability(reportDependability);
		 * assertThrows(InvalidGasStationException.class, () -> { gsDto.getReportDependability(); });
		 */
		
	}
	
	
	@Test
	public void testReportTimeStamp() {
		
		String reportTimestamp = "2020-05-16";

		GasStationDto gsDto = new GasStationDto();
		gsDto.setReportTimestamp(reportTimestamp);
		
		assertTrue(gsDto.getReportTimestamp() == reportTimestamp);
		
	}
	
	
	@Test
	public void testGasStationDtoConstructor() {
		
		Integer gasStationId = 1;
		String gasStationName = "Torino 2";
		String gasStationAddress = "Porta Susa Torino Piemonte Italia";
		String carSharing = "Car2Go";
		boolean hasDiesel = false;
		boolean hasGas = true;
		boolean hasMethane = false;
		boolean hasSuper = true;
		boolean hasSuperPlus = false;
		boolean hasPremiumDiesel = false;
		double lat = 45.066667;
		double lon = 7.666667;
		Double dieselPrice = 0.0;
		Double superPrice = 1.57;
		Double superPlusPrice = 0.0;
		Double gasPrice = 0.97;
		Double methanePrice = 0.0;
		Double premiumDieselPrice = 0.0;
		Integer reportUser = 1;
		String reportTimestamp = "2020-05-20";
		double reportDependability = 50;
		
		GasStationDto gsDto = new GasStationDto(gasStationId, gasStationName, gasStationAddress, hasDiesel, 
				hasSuper, hasSuperPlus, hasGas, hasMethane, hasPremiumDiesel, carSharing, lat, lon,
				dieselPrice, superPrice, superPlusPrice, gasPrice, methanePrice, premiumDieselPrice, 
				reportUser, reportTimestamp, reportDependability);
		
		assertTrue(gsDto.getGasStationId() == gasStationId && gsDto.getGasStationName() == gasStationName 
				&& gsDto.getGasStationAddress() == gasStationAddress && gsDto.getHasDiesel() == hasDiesel 
				&& gsDto.getHasSuper() == hasSuper && gsDto.getHasSuperPlus() == hasSuperPlus
				&& gsDto.getHasGas() == hasGas && gsDto.getHasMethane() == hasMethane && gsDto.getCarSharing() == carSharing
				&& gsDto.getLat() == lat && gsDto.getLon() == lon && gsDto.getDieselPrice() == dieselPrice 
				&& gsDto.getSuperPrice() == superPrice && gsDto.getSuperPlusPrice() == superPlusPrice 
				&& gsDto.getGasPrice() == gasPrice && gsDto.getMethanePrice() == methanePrice 
				&& gsDto.getPremiumDieselPrice() == premiumDieselPrice && gsDto.getReportUser() == reportUser 
				&& gsDto.getReportTimestamp() == reportTimestamp && gsDto.getReportDependability() == reportDependability);		
		
	}

}
