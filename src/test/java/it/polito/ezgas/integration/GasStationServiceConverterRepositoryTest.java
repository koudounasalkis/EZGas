package it.polito.ezgas.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import exception.GPSDataException;
import exception.InvalidCarSharingException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.GasStationServiceimpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GasStationServiceConverterRepositoryTest {
	
	private GasStationServiceimpl service;

	@Autowired
	private GasStationRepository gsRepository;
	@Autowired
	private UserRepository uRepository;
	@Autowired
	private TestEntityManager entityManager;

	private GasStationConverter gsConverter = new GasStationConverter();	
	
	@Test
	public void testGetGasStationById() throws InvalidGasStationException {
		
		GasStation gs = new GasStation();
		entityManager.persist(gs);
		Integer gsId = gs.getGasStationId();
		
		GasStationDto gsDto = gsConverter.convertToDto(gs);
		
	    service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
	    // Test Normal Case: finding a Gas Station given its GasStationId
	    assertTrue(new ReflectionEquals(gsDto).matches(service.getGasStationById(gsId)));
	    	    
	    // Test Negative GasStationId
	    assertThrows(InvalidGasStationException.class, () -> { service.getGasStationById(-1); });
	    
	    // Test Null GasStationId
	    assertNull(service.getGasStationById(null));
	    
	    // Test GasStationId not present in the DB
	    assertNull(service.getGasStationById(10));	
	    
	}
	
	
	@Test
	public void testSaveGasStation() throws PriceException, GPSDataException {
		
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, 
				 false, true, false, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs);
		GasStationDto gsDto = gsConverter.convertToDto(gs);
	   
		// Test Normal Case: saving a Gas Station
		assertNotNull(service.saveGasStation(gsDto));
		 
		// Test Invalid (Negative) Price
		gsDto.setReportUser(1);
		gsDto.setDieselPrice(-0.49);
		assertThrows(PriceException.class, () -> { service.saveGasStation(gsDto); });
		 
		// Test Latitude over limit (> 90)
		gsDto.setDieselPrice(1.49);
		gsDto.setLat(100.000124);
		assertThrows(GPSDataException.class, () -> { service.saveGasStation(gsDto); });
		 
		// Test Longitude over limit (< -180)
		gsDto.setLat(45.066667);
		gsDto.setLon(-200.000124);
		assertThrows(GPSDataException.class, () -> { service.saveGasStation(gsDto); });
		 
		// Test Null field
		gsDto.setGasStationId(1);
		gsDto.setGasStationName(null);
		assertNull(service.saveGasStation(gsDto));
		
		gsDto.setGasStationName("Torino 1");
		gsDto.setGasStationAddress(null);
		assertNull(service.saveGasStation(gsDto));
		
	}
		
	
	@Test
	public void testGetAllGasStations() {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
		// Test Empty List
		assertTrue(service.getAllGasStations().isEmpty());
		
		// Test Non-Empty List
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, 
				 false, true, false, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0);
		entityManager.persist(gs);
		
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", true, false, true, 
				 false, true, false, "Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0);
		entityManager.persist(gs1);
	
		assertFalse(service.getAllGasStations().isEmpty());
		
	}
	
	
	@Test
	public void testDeleteGasStation() throws InvalidGasStationException {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
	     // Test Normal Case: deleting a Gas Station given its GasStationId
		 GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, 
				 false, true, false, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0);
		 entityManager.persist(gs);
		
	     assertTrue(service.deleteGasStation(gs.getGasStationId()));
	     
	     // Test Negative GasStationId
	     assertThrows(InvalidGasStationException.class, () -> {service.deleteGasStation(-1); });
	     
	     // Test Null GasStationId
	     assertFalse(service.deleteGasStation(null));
	     
	     // Test GasStationId not present in the DB
	     assertFalse(service.deleteGasStation(20));
	     
	}
	
	
	@Test
	public void testGetGasStationsByGasolineType() throws InvalidGasTypeException {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test Exception (Invalid GasolineType)
		assertThrows(InvalidGasTypeException.class, () -> {service.getGasStationsByGasolineType("not-valid"); });
		
		// Test Exception (Null)
		assertTrue(service.getGasStationsByGasolineType(null).isEmpty());
		
		// Test Diesel miss
		assertTrue(service.getGasStationsByGasolineType("Diesel").isEmpty());
		
		// Test Gas miss
		assertTrue(service.getGasStationsByGasolineType("Gas").isEmpty());
		
		// Test Methane miss
		assertTrue(service.getGasStationsByGasolineType("Methane").isEmpty());
		
		// Test Super miss
		assertTrue(service.getGasStationsByGasolineType("Super").isEmpty());	
		
		// Test SuperPlus miss
		assertTrue(service.getGasStationsByGasolineType("SuperPlus").isEmpty());
		
		// Test PremiumDiesel miss
		assertTrue(service.getGasStationsByGasolineType("PremiumDiesel").isEmpty());
			
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, true, true, 
				 true, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 1.51, 1.61, 0.79, 0.89, 2.0, null, null, 0);
		entityManager.persist(gs);
		 
		// Test Diesel hit
		assertFalse(service.getGasStationsByGasolineType("Diesel").isEmpty());
		
		// Test Gas hit
		assertFalse(service.getGasStationsByGasolineType("Gas").isEmpty());	
		
		// Test Methane hit
		assertFalse(service.getGasStationsByGasolineType("Methane").isEmpty());
		
		// Test Super hit
		assertFalse(service.getGasStationsByGasolineType("Super").isEmpty());	
		
		// Test SuperPlus hit
		assertFalse(service.getGasStationsByGasolineType("SuperPlus").isEmpty());
		
		// Test PremiumDiesel hit
		assertFalse(service.getGasStationsByGasolineType("PremiumDiesel").isEmpty());
		
	}
		
	
	@Test
	public void testGetGasStationsByProximity() throws GPSDataException {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
		// Test Exception with Latitude
		assertThrows(GPSDataException.class, () -> { service.getGasStationsByProximity(-100, 43.25); });
				
		// Test Exception with Longitude
		assertThrows(GPSDataException.class, () -> { service.getGasStationsByProximity(43.25, 200); });
		
		// Test Empty List with negative radius (-1 converted to 1)
		assertTrue(service.getGasStationsByProximity(45.066775, 7.666823, -1).isEmpty());
		
		// Test Empty List with default radius (= 1 km)
		assertTrue(service.getGasStationsByProximity(45.066775, 7.666823).isEmpty());
		
		// Test Empty List with radius >1 (=5 km)
		assertTrue(service.getGasStationsByProximity(45.066775, 7.666823, 5).isEmpty());
				
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, true, true, 
				 true, true, false, "Enjoy", 45.066667, 7.666667, 1.41, 1.51, 1.61, 0.79, 0.89, 2.0, null, null, 0);
		entityManager.persist(gs);
		
		// Test Non-Empty List with negative radius (-1 converted to 1)
		assertFalse(service.getGasStationsByProximity(45.066775, 7.666823, -1).isEmpty());
		
		// Test Non-Empty List with default radius (= 1 km)
		assertFalse(service.getGasStationsByProximity(45.066775, 7.666823).isEmpty());

		// Test Non-Empty List with radius >1 (=5 km)
		assertFalse(service.getGasStationsByProximity(45.066775, 7.666823, 5).isEmpty());
		
	}
	
	
	@Test
	public void testGetGasStationByCarSharing() {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
		// Test Finding nothing with "Enjoy"
		assertTrue(service.getGasStationByCarSharing("Enjoy").isEmpty());
				
		// Test Finding nothing with "Car2Go"
		assertTrue(service.getGasStationByCarSharing("Car2Go").isEmpty());

		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, true, true, 
				 true, true, false, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0);
		entityManager.persist(gs);
		
		// Test Finding something with "Enjoy"
		assertFalse(service.getGasStationByCarSharing("Enjoy").isEmpty());
		
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", true, true, true, 
				 true, true, false, "Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0);
		entityManager.persist(gs1);
		
		// Test Finding something with "Car2Go"
		assertFalse(service.getGasStationByCarSharing("Car2Go").isEmpty());
				
	}
	
	
	@Test
	public void getGasStationsWithoutCoordinates() throws InvalidGasTypeException, InvalidCarSharingException {
		
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
		// Test Null GasolineType
		assertTrue(service.getGasStationsWithoutCoordinates(null, "Enjoy").isEmpty());

		// Test Null CarSharing
		assertTrue(service.getGasStationsWithoutCoordinates("Methane", null).isEmpty());

		// Test Invalid GasolineType
		assertThrows(InvalidGasTypeException.class, ()-> { service.getGasStationsWithoutCoordinates("methan", "Enjoy"); });

		// Test Invalid CarSharing
		assertThrows(InvalidCarSharingException.class, ()-> { service.getGasStationsWithoutCoordinates("Methane", "Enyoj"); });
				
		// Test both Empty Lists
		assertTrue(service.getGasStationsWithoutCoordinates("Diesel", "Enjoy").isEmpty());

		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", false, true, true, 
				 true, true, true, "Enjoy", 45.066667, 7.666667, 0.0, 1.46, 1.56, 0.79, 0.89, 2.0, null, null, 0);
		entityManager.persist(gs);
		
		// Test Empty List filtered by GasolineType
		assertTrue(service.getGasStationsWithoutCoordinates("Diesel", "Enjoy").isEmpty());
		
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", false, true, true, 
				 true, true, true, "Car2Go", 47.066667, 9.666667, 0.0, 1.46, 1.56, 0.79, 0.89, 2.0, null, null, 0);
		entityManager.persist(gs1);
		
		// Test Empty List filtered by CarSharing
		assertTrue(service.getGasStationsWithoutCoordinates("Diesel", "Car2Go").isEmpty());
	
		gs.setHasDiesel(true);
		gs.setDieselPrice(1.41);
		
		gs1.setHasDiesel(true);
		gs1.setDieselPrice(1.41);
		
		// Test Enjoy and Car2Go CarSharing (and all GasolineTypes)
		String[] gTypes = {"Diesel", "Super", "SuperPlus", "Gas", "Methane", "PremiumDiesel"};
		for (String gasolineType : gTypes) {
			assertFalse(service.getGasStationsWithoutCoordinates(gasolineType, "Enjoy").isEmpty());
			assertFalse(service.getGasStationsWithoutCoordinates(gasolineType, "Car2Go").isEmpty());
		}
		
	}
	
	
	@Test
	public void testUpdateDependability() {
		
		User u = new User();
		u.setReputation(2);
		
		GasStation gs = new GasStation();
		LocalDate date = LocalDate.now().minusDays(3);
		gs.setReportTimestamp(date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
		gs.setUser(u);
		
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);		

		// Test within 7 days (3 days before today)
		assertTrue(service.updateDependability(gs).getReportDependability() == 63.57);
		
		// Test after 7 days (10 days before today)
		gs.setReportTimestamp(date.minusDays(7).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
		assertTrue(service.updateDependability(gs).getReportDependability() == 35);
		
		// Test No Report (Null Timestamp)
		gs.setReportTimestamp(null);
		assertTrue(service.updateDependability(gs).equals(gs));

		// Test No Report (Null User)
		gs.setReportTimestamp("05-20-2020");
		gs.setUser(null);
		assertTrue(service.updateDependability(gs).equals(gs));
		
	}
	
	
	@Test
	public void testSetReport() throws InvalidGasStationException, PriceException, InvalidUserException {
		
		User u2 = new User("mario", "password", "mario@ezgas.it", 0);
		entityManager.persist(u2);
		Integer u2Id = u2.getUserId();
		
		User u1 = new User("luigi", "password", "luigi@ezgas.it", 2);
		entityManager.persist(u1);
		
		GasStation gs = new GasStation ("Torino 2", "Via Vigone 33", true , true, true, true, true, true, "Car2Go", 47.066667, 9.666667, 
				0.91, 1.46, 1.56, 0.79, 0.89, 2.0, u1.getUserId(), LocalDate.now().minusDays(3).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")), 0);
		entityManager.persist(gs);
		gs.setUser(u1);
		
		Integer id = gs.getGasStationId();
		
		// Test GasStationId not present in the DB
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		service.setReport(100, 1.41, 1.51, 1.61, 0.81, 0.91, 3.0, u2.getUserId());
		assertTrue(id != 100);
		
		// Test UserId non present in the DB
		service.setReport(id, 1.41, 1.51, 1.61, 0.81, 0.91, 3.0, 100);
		assertTrue(gs.getUser().getUserId() != 100);
	
		// Test Set Report with DateDiff <= 4 days and U2 reputation < U1 reputation
		service.setReport(id, 1.41, 1.51, 1.61, 0.81, 0.91, 1.1, u2Id);
				
		assertTrue(gs.getReportUser() == u1.getUserId() && gs.getUser() == u1);
		
		// Test Set Report with DateDiff <= 4 days and U2 reputation >= U1 reputation
		u2.setReputation(2);
		service.setReport(id, 1.41, 1.51, 1.61, 0.81, 0.91, 3.0, u2.getUserId());

		assertTrue(gs.getGasStationId() == id
				&& gs.getDieselPrice() == 1.41
				&& gs.getSuperPrice() == 1.51
				&& gs.getSuperPlusPrice() == 1.61
				&& gs.getGasPrice() == 0.81
				&& gs.getMethanePrice() == 0.91
				&& gs.getPremiumDieselPrice() == 3.0
				&& gs.getReportUser() == u2.getUserId()
				&& gs.getUser() == u2
				&& gs.getReportTimestamp().equals(LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))));
		
		// Test Set Report with DateDiff > 4 days
		gs.setReportTimestamp(LocalDate.now().minusDays(5).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
		gs.setUser(u1);
			
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		service.setReport(id, 1.41, 1.51, 1.61, 0.81, 0.91, 1.1, u2.getUserId());

		assertTrue(gs.getGasStationId() == id
				&& gs.getDieselPrice() == 1.41
				&& gs.getSuperPrice() == 1.51
				&& gs.getSuperPlusPrice() == 1.61
				&& gs.getGasPrice() == 0.81
				&& gs.getMethanePrice() == 0.91
				&& gs.getPremiumDieselPrice() == 1.1
				&& gs.getReportUser() == u2.getUserId()
				&& gs.getUser() == u2
				&& gs.getReportTimestamp().equals(LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))));
		
		// Test Negative GasStationId
		assertThrows(InvalidGasStationException.class, ()-> { service.setReport(-1, 1.41, 1.51, 1.61, 0.81, 0.91, 3.0, 1); });

		// Test Negative UserId
		assertThrows(InvalidUserException.class, ()-> { service.setReport(1, 1.41, 1.51, 1.61, 0.81, 0.91, 3.0, -1); });

		// Test Negative DieselPrice 
		gs.setHasDiesel(true);
		assertThrows(PriceException.class, ()-> { service.setReport(id, -1.41, 1.51, 1.61, 0.81, 0.91, 3.0, u2.getUserId()); });

		// Test Negative SuperPrice
		gs.setHasDiesel(false);
		gs.setHasSuper(true);
		assertThrows(PriceException.class, ()-> { service.setReport(id, 1.41, -1.51, 1.61, 0.81, 0.91, 3.0, u2.getUserId()); });

		// Test Negative SuperPlusPrice
		gs.setHasSuper(false);
		gs.setHasSuperPlus(true);
		assertThrows(PriceException.class, ()-> { service.setReport(id, 1.41, 1.51, -1.61, 0.81, 0.91, 3.0, u2.getUserId()); });

		// Test Negative GasPrice
		gs.setHasSuperPlus(false);
		gs.setHasGas(true);
		assertThrows(PriceException.class, ()-> { service.setReport(id, 1.41, 1.51, 1.61, -0.81, 0.91, 3.0, u2.getUserId()); });

		// Test Negative MethanePrice
		gs.setHasGas(false);
		gs.setHasMethane(true);
		assertThrows(PriceException.class, ()-> { service.setReport(id, 1.41, 1.51, 1.61, 0.81, -0.91, 3.0, u2.getUserId()); });
		
		// Test Negative PremiumDieselPrice
		gs.setHasMethane(false);
		gs.setHasPremiumDiesel(true);
		assertThrows(PriceException.class, ()-> { service.setReport(id, 1.41, 1.51, 1.61, 0.81, 0.91, -3.0, u2.getUserId()); });
		
		// Test Null GasStationId
		// This will always fail
		// assertThrows(InvalidGasStationException.class, ()-> { service.setReport(null, 1.41, 1.51, 1.61, 0.81, 0.91, 1); });

		// Test Null UserId
		// This will always fail
		// assertThrows(InvalidGasStationException.class, ()-> { service.setReport(1, 1.41, 1.51, 1.61, 0.81, 0.91, null); });	
		
	}
	
	@Test
	public void testGetGasStationsWithCoordinates() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test Empty List filtered by proximity
		assertTrue(service.getGasStationsWithCoordinates(45.666667, 7.080660, 1, "Methane", "Enjoy").isEmpty());
		
		// Test Exception with Latitude
		assertThrows(GPSDataException.class, ()->{ service.getGasStationsWithCoordinates(-100, 43.25, 1, "Methane", "Enjoy"); });
	
		// Test Exception with Longitude
		assertThrows(GPSDataException.class, ()->{ service.getGasStationsWithCoordinates(43.25, 200, 1, "Methane", "Enjoy"); });
		
		// Test GasolineType == null (careful: not "null"!)
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, null, "Car2Go").isEmpty());
		
		// Test CarSharing == null (careful: not "null"!)
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "Diesel", null).isEmpty());
		
		//Test both null:
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, null, null).isEmpty());

		// Test all gasolineTypes misses: Empty Lists
		String[] gTypes = {"Diesel", "Super", "SuperPlus", "Gas", "Methane", "PremiumDiesel"};
		for (String gasolineType : gTypes) {
			assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, gasolineType, "null").isEmpty());
		}
		
		// Test Invalid GasolineType
		assertThrows(InvalidGasTypeException.class, ()-> { service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "methan", "Enjoy"); });

		
		GasStation gs = new GasStation("Torino 1", "Via Vigone 3", true, true, true, true , true, true, "null", 45.066667, 7.666667, 1.41, 0.342, 1.56, 0.234, 0.89, 2.0, null, null, 0);
		entityManager.persist(gs);

		// Test all gasolineTypes hits: MATCHING between List filtered by Proximity and List filtered by GasolineType
		for (String gasolineType : gTypes) {
			assertFalse(service.getGasStationsWithCoordinates(45.066667, 7.666667, 1, gasolineType, "null").isEmpty());
		}
		
		// Test all gasolineTypes hits: NO MATCHING between List filtered by Proximity and List filtered by GasolineType
		for (String gasolineType : gTypes) {
			assertTrue(service.getGasStationsWithCoordinates(62.06667, 37.07890, 1, gasolineType, "null").isEmpty());
		}
		
		// Test Empty Lists filtered by CarSharing
		assertTrue(service.getGasStationsWithCoordinates(45.066667, 7.666667, 1, "null", "Enjoy").isEmpty());
		assertTrue(service.getGasStationsWithCoordinates(45.066667, 7.666667, 1, "null", "Car2Go").isEmpty());

		// Test Invalid CarSharing
		assertThrows(InvalidCarSharingException.class, ()-> { service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "enyoj"); });
		
		GasStation gsCS1 = new GasStation("Torino 2", "Via Vigone 4", true, true, true, true , true, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.342, 1.56, 0.234, 0.89, 2.0, null, null, 0);
		entityManager.persist(gsCS1);
		GasStation gsCS2 = new GasStation("Torino 3", "Via Vigone 5", true, true, true, true , true, true, "Car2Go", 45.066667, 7.666667, 1.41, 0.342, 1.56, 0.234, 0.89, 2.0, null, null, 0);
		entityManager.persist(gsCS2);
		
		//Test all CarSharing hits: MATCHING between List filtered by Proximity and List filtered by CarSharing
		assertFalse(service.getGasStationsWithCoordinates(45.066667, 7.666667, 1, "null", "Enjoy").isEmpty());
		assertFalse(service.getGasStationsWithCoordinates(45.066667, 7.666667, 1, "null", "Car2Go").isEmpty());

		// Test all CarSharing hits: NO MATCHING between List filtered by Proximity and List filtered by CarSharing
		assertTrue(service.getGasStationsWithCoordinates(62.06667, 37.07890, 1, "null", "Enjoy").isEmpty());
		assertTrue(service.getGasStationsWithCoordinates(62.06667, 37.07890, 1, "null", "Car2Go").isEmpty());
		
		// Test with one empty list filtered by GasolineType or CarSharing 
		for (String gasolineType : gTypes) {
			assertTrue(service.getGasStationsWithCoordinates(62.06667, 37.07890, 1, gasolineType, "Enjoy").isEmpty());
			assertTrue(service.getGasStationsWithCoordinates(62.06667, 37.07890, 1, gasolineType, "Car2Go").isEmpty());
		}
				
		// Test with MATCHING between list filtered by Proximity and list filtered by GasolineType and CarSharing 
		for (String gasolineType : gTypes) {
			assertFalse(service.getGasStationsWithCoordinates(45.066667, 7.666667, 1, gasolineType, "Enjoy").isEmpty());
			assertFalse(service.getGasStationsWithCoordinates(45.066667, 7.666667, 1, gasolineType, "Car2Go").isEmpty());
		}
		
		// Test with NO MATCHING between list filtered by Proximity and list filtered by GasolineType and CarSharing 
		for (String gasolineType : gTypes) {
			assertTrue(service.getGasStationsWithCoordinates(62.06667, 37.07890, 1, gasolineType, "Enjoy").isEmpty());
			assertTrue(service.getGasStationsWithCoordinates(62.06667, 37.07890, 1, gasolineType, "Car2Go").isEmpty());
		}
	
	}
	
}
