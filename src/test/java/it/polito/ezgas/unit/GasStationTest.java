package it.polito.ezgas.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;


public class GasStationTest {
	
	@Test
	public void testGasStationId() {
		
		Integer gasStationId = 1;

		GasStation gs = new GasStation();
		gs.setGasStationId(gasStationId);
		
		assertTrue(gs.getGasStationId() == gasStationId);
		
		
		/*
		 * These asserts will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer gasStationIdNegative = -1;
		 * GasStation gsNegative = new GasStation();
		 * gsNegative.setGasStationId(gasStationIdNegative);
		 * assertThrows(InvalidGasStationException.class, () -> { gsNegative.getGasStationId(); });
		 * 
		 * 
		 * Integer gasStationIdNull = null;
		 * GasStation gsNull = new GasStation();
		 * gs.setGasStationId(gasStationIdNull);
		 * assertThrows(InvalidGasStationException.class, () -> { gsNull.getGasStationId(); });
		 */
		
	}

	
	@Test
	public void testGasStationName() {
		
		String gasStationName = "Torino 1";

		GasStation gs = new GasStation();
		gs.setGasStationName(gasStationName);
		
		assertTrue(gs.getGasStationName() == gasStationName);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * String gasStationNameNull = null;
		 * GasStation gsNull = new GasStation();
		 * gs.setGasStationName(gasStationNameNull);
		 * assertThrows(InvalidGasStationException.class, () -> { gsNull.getGasStationName(); });
		 */
		
	}
	
	
	@Test
	public void testGasStationAddress() {
		
		String gasStationAddress = "Porta Nuova Torino Piemonte Italia";

		GasStation gs = new GasStation();
		gs.setGasStationAddress(gasStationAddress);
		
		assertTrue(gs.getGasStationAddress() == gasStationAddress);
		
	}
	
	
	@Test
	public void testHasDieselTrue() {
		
		boolean gasolineType = true;

		GasStation gs = new GasStation();
		gs.setHasDiesel(gasolineType);
		
		assertTrue(gs.getHasDiesel() == gasolineType);
		
	}
	
	
	@Test
	public void testHasDieselFalse() {
		
		boolean gasolineType = false;

		GasStation gs = new GasStation();
		gs.setHasDiesel(gasolineType);
		
		assertTrue(gs.getHasDiesel() == gasolineType);
		
	}
	
	
	@Test
	public void testHasPremiumDiesel() {
		
		boolean gasolineType = true;

		GasStation gs = new GasStation();
		gs.setHasPremiumDiesel(gasolineType);
		
		assertTrue(gs.getHasPremiumDiesel() == gasolineType);
		
	}
	
	
	@Test
	public void testLatitude() {
		
		double latitude = 45.066667;

		GasStation gs = new GasStation();
		gs.setLat(latitude);
		
		assertTrue(gs.getLat() == latitude);
		
		
		/*
		 * These asserts will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * double latitudeOverMax = 245.066667;
		 * gs.setLat(latitudeAboveMax);
		 * assertThrows(GPSDataException.class, ()-> { gs.getLat(); });
		 * 
		 * double latitudeBelowMin = -200.066667;
		 * gs.setLat(latitudeBelowMin);
		 * assertThrows(GPSDataException.class, ()-> { gs.getLat(); });
		 */
		
	}
	
	
	@Test
	public void testLongitude() {
		
		double longitude = 7.666667;

		GasStation gs = new GasStation();
		gs.setLon(longitude);
		
		assertTrue(gs.getLon() == longitude);
		
		
		/*
		 * These asserts will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * double longitudeOverMax = 245.066667;
		 * gs.setLon(longitudeOverMax);
		 * assertThrows(GPSDataException.class, ()-> { gs.getLon(); });
		 * 
		 * double longitudeBelowMin = -200.066667;
		 * gs.setLon(longitudeBelowMin);
		 * assertThrows(GPSDataException.class, ()-> { gs.getLon(); });
		 */
		
	}
	
	
	@Test
	public void testDieselPrice() {
		
		Double dieselPrice = 1.37;

		GasStation gs = new GasStation();
		gs.setDieselPrice(dieselPrice);
		
		assertTrue(gs.getDieselPrice() == dieselPrice);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double dieselPriceNegative = -1.37;
		 * GasStation gsNegative = new GasStation();
		 * gsNegative.setDieselPrice(dieselPriceNegative);
		 * assertThrows(PriceException.class, () -> { gsNegative.getDieselPrice(); });
		 */
		
	}
	
	@Test
	public void testPremiumDieselPrice() {
		
		
		Double premiumDieselPrice = 1.37;

		GasStation gs = new GasStation();
		gs.setPremiumDieselPrice(premiumDieselPrice);
		
		assertTrue(gs.getPremiumDieselPrice() == premiumDieselPrice);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Double premiumDieselPrice = -1.37;
		 * GasStation gsNegative = new GasStation();
		 * gsNegative.setPremiumDieselPrice(premiumDieselPrice);
		 * assertThrows(PriceException.class, () -> { gsNegative.getPremiumDieselPrice(); });
		 */
		
	}
	
	
	
	@Test
	public void testUser() {
		
		User u = new User();

		GasStation gs = new GasStation();
		gs.setUser(u);
		
		assertTrue(gs.getUser() == u);
		
	}
	
	
	@Test
	public void testReportUser() {
		
		Integer reportUser = 1;

		GasStation gs = new GasStation();
		gs.setReportUser(reportUser);
		
		assertTrue(gs.getReportUser() == reportUser);
		
	}
	
	
	@Test
	public void testReportDependability() {
		
		Integer reportDependability = 75;

		GasStation gs = new GasStation();
		gs.setReportDependability(reportDependability);
		
		assertTrue(gs.getReportDependability() == reportDependability);
		
	}
	
	
	@Test
	public void testReportTimeStamp() {
		
		String reportTimestamp = "2020-05-16";

		GasStation gs = new GasStation();
		gs.setReportTimestamp(reportTimestamp);
		
		assertTrue(gs.getReportTimestamp() == reportTimestamp);
		
	}
	
	
	@Test
	public void testGasStationConstructor() {
		
		String gasStationName = "Torino 1";
		String gasStationAddress = "Porta Nuova Torino Piemonte Italia";
		String carSharing = "Enjoy";
		boolean hasDiesel = true;
		boolean hasGas = false;
		boolean hasMethane = true;
		boolean hasSuper = false;
		boolean hasSuperPlus = true;
		boolean hasPremiumDiesel = true;
		double lat = 45.066667;
		double lon = 7.666667;
		Double dieselPrice = 1.27;
		Double superPrice = 0.0;
		Double superPlusPrice = 1.57;
		Double gasPrice = 0.0;
		Double methanePrice = 0.97;
		Double premiumDieselPrice = 1.0;
		Integer reportUser = 1;
		String reportTimestamp = "2020-05-16";
		double reportDependability = 0;
		
		GasStation gs = new GasStation(gasStationName, gasStationAddress, hasDiesel, 
				hasSuper, hasSuperPlus, hasGas, hasMethane, hasPremiumDiesel, carSharing, lat, lon,
				dieselPrice, superPrice, superPlusPrice, gasPrice, methanePrice, premiumDieselPrice, 
				reportUser, reportTimestamp, reportDependability);
		
		assertTrue(gs.getGasStationName() == gasStationName && gs.getGasStationAddress() == gasStationAddress
				&& gs.getHasDiesel() == hasDiesel && gs.getHasSuper() == hasSuper && gs.getHasSuperPlus() == hasSuperPlus
				&& gs.getHasGas() == hasGas && gs.getHasMethane() == hasMethane && gs.getCarSharing() == carSharing
				&& gs.getLat() == lat && gs.getLon() == lon && gs.getDieselPrice() == dieselPrice 
				&& gs.getSuperPrice() == superPrice && gs.getSuperPlusPrice() == superPlusPrice 
				&& gs.getGasPrice() == gasPrice && gs.getMethanePrice() == methanePrice 
				&& gs.getPremiumDieselPrice() == premiumDieselPrice && gs.getReportUser() == reportUser 
				&& gs.getReportTimestamp() == reportTimestamp && gs.getReportDependability() == reportDependability);
		
	}
	
}
