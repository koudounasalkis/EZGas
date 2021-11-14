package it.polito.ezgas.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import exception.GPSDataException;
import exception.InvalidCarSharingException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.PriceException;
import it.polito.ezgas.converter.*;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.*;
import it.polito.ezgas.service.impl.GasStationServiceimpl;

public class GasStationServiceConverterTest {
	
	private GasStationServiceimpl service;
	private GasStationConverter gsConverter = new GasStationConverter();
	private UserConverter uConverter = new UserConverter();

	@Mock
	private GasStationRepository gsRepository;
	@Mock
	private UserRepository uRepository;
	
	@Test
	public void testGetGasStationById() throws InvalidGasStationException {
		
		GasStation gs = new GasStation("Torino 1", "Via Vigone 3", true, false, true, false, true, false,
				"Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0);
		gs.setGasStationId(1);
		
		GasStationDto gsDto = new GasStationDto (1, "Torino 1", "Via Vigone 3", true, false, true, 
				 false, true, false, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0);
		
	    gsRepository = mock(GasStationRepository.class);
	    when(gsRepository.exists(anyInt())).thenReturn(true);
	    when(gsRepository.findOne(anyInt())).thenReturn(gs);
	    service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
	    
	    // Test Normal Case: finding a Gas Station given its GasStationId	   
	    assertTrue(new ReflectionEquals(gsDto).matches(service.getGasStationById(1)));
	    
	    User u = new User("mario", "password", "name@ezgas.it", 4);
		u.setUserId(1);
		gs.setUser(u);
		gsDto.setUserDto(uConverter.convertToDto(u));
	    
	    // Test with a Non NUll User	   
	    assertTrue(new ReflectionEquals(gsDto.getUserDto()).matches(service.getGasStationById(1).getUserDto()));
	    
	    // Test Negative GasStationId
	    assertThrows(InvalidGasStationException.class, () -> { service.getGasStationById(-1); });
	    
	    // Test Null GasStationId
	    assertNull(service.getGasStationById(null));

		when(gsRepository.exists(anyInt())).thenReturn(false);
	    service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
	    
	    // Test GasStationId not present in the DB
	    assertNull(service.getGasStationById(10));
	    
	}
	
	
	@Test
	public void testSaveGasStation() throws PriceException, GPSDataException {
		
		 GasStationDto gsDto = new GasStationDto (1, "Torino 1", "Via Vigone 3", true, false, true, 
				 false, true, false, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		 
		 gsRepository = mock(GasStationRepository.class);
		 
		 when(gsRepository.save(any(GasStation.class))).then(returnsFirstArg());
		 service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		 
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
		
		gsRepository = mock(GasStationRepository.class);
		
		when(gsRepository.findAll()).thenReturn(new ArrayList<GasStation>());
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);

		// Test Empty List
		assertTrue(service.getAllGasStations().isEmpty());
		
		ArrayList<GasStation> list = new ArrayList<GasStation>();
		list.add(new GasStation());
		
		when(gsRepository.findAll()).thenReturn(list);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);

		// Test Non-Empty List
		assertFalse(service.getAllGasStations().isEmpty());
		
	}
	
	
	@Test
	public void testGetGasStationsByGasolineType() throws InvalidGasTypeException {
		
		gsRepository = mock(GasStationRepository.class);
		
		List<GasStation> list = new ArrayList<GasStation>();
		list.add(new GasStation());
		
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(list);
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(list);
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(list);
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(list);
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(list);
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(list);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);

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
		
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(new ArrayList<GasStation>());
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
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
		
		// Test Exception (Invalid GasolineType)
		assertThrows(InvalidGasTypeException.class, () -> {service.getGasStationsByGasolineType("not-valid"); });
		
		// Test Null GasolineType
		assertTrue(service.getGasStationsByGasolineType(null).isEmpty());
		
	}
	
	
	@Test
	public void testGetGasStationsByProximity() throws GPSDataException {
		
		gsRepository = mock(GasStationRepository.class);
		
		List<GasStation> list = new ArrayList<GasStation>();
		list.add(new GasStation());
		
		when(gsRepository.findByProximity(anyDouble(), anyDouble(), anyInt())).thenReturn(list);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
				
		// Test Non-Empty List with negative radius (-1 converted to 1)
		assertFalse(service.getGasStationsByProximity(67.35, -123.12, -1).isEmpty());
				
		// Test Non-Empty List with default radius (= 1 km)
		assertFalse(service.getGasStationsByProximity(67.35, -123.12).isEmpty());

		// Test Non-Empty List with radius >1 (=5 km)
		assertFalse(service.getGasStationsByProximity(67.35, -123.12, 5).isEmpty());
		
		when(gsRepository.findByProximity(anyDouble(), anyDouble(), anyInt())).thenReturn(new ArrayList<GasStation>());
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test Empty List with negative radius (-1 converted to 1)
		assertTrue(service.getGasStationsByProximity(67.35, -123.12, -1).isEmpty());
		
		// Test Empty List with default radius (= 1 km)
		assertTrue(service.getGasStationsByProximity(67.35, -123.12).isEmpty());
		
		// Test Empty List with radius >1 (=5 km)
		assertTrue(service.getGasStationsByProximity(67.35, -123.12, 5).isEmpty());
				
		// Test Exception with Latitude
		assertThrows(GPSDataException.class, ()->{ service.getGasStationsByProximity(-100,43.25,1); });
		
		// Test Exception with Longitude
		assertThrows(GPSDataException.class, ()->{ service.getGasStationsByProximity(43.25, 200,1); });
		
	}
	
	
	@Test
	public void testGetGasStationByCarSharing() {
		
		gsRepository = mock(GasStationRepository.class);
		
		List<GasStation> list = new ArrayList<GasStation>();
		list.add(new GasStation());
		
		when(gsRepository.findByCarSharing(anyString())).thenReturn(list);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);		
		
		// Test Finding something with "Enjoy"
		assertFalse(service.getGasStationByCarSharing("Enjoy").isEmpty());
		
		// Test Finding something with "Car2Go"
		assertFalse(service.getGasStationByCarSharing("Car2Go").isEmpty());
		
		when(gsRepository.findByCarSharing(anyString())).thenReturn(new ArrayList<GasStation>());
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);		
		
		// Test Finding nothing with "Enjoy"
		assertTrue(service.getGasStationByCarSharing("Enjoy").isEmpty());
		
		// Test Finding nothing with "Car2Go"
		assertTrue(service.getGasStationByCarSharing("Car2Go").isEmpty());
		
	}
	
	
	@Test
	public void getGasStationsWithoutCoordinates() throws InvalidGasTypeException, InvalidCarSharingException {
		
		gsRepository = mock(GasStationRepository.class);
		
		List<GasStation> list = new ArrayList<GasStation>();
		list.add(new GasStation());
		
		when(gsRepository.findByCarSharing(anyString())).thenReturn(list);
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(list);
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(list);
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(list);
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(list);
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(list);
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(list);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
		// Test Null GasolineType
		assertTrue(service.getGasStationsWithoutCoordinates(null, "Enjoy").isEmpty());

		// Test Null CarSharing
		assertTrue(service.getGasStationsWithoutCoordinates("Methane", null).isEmpty());
		
		// Test Null GasolineType and Null CarSharing
		assertTrue(service.getGasStationsWithoutCoordinates(null, null).isEmpty());

		// Test Enjoy and Car2Go CarSharing (and all GasolineTypes)
		String[] gTypes = {"Diesel", "Super", "SuperPlus", "Gas", "Methane", "PremiumDiesel"};
		for (String gasolineType : gTypes) {
			assertFalse(service.getGasStationsWithoutCoordinates(gasolineType, "Enjoy").isEmpty());
			assertFalse(service.getGasStationsWithoutCoordinates(gasolineType, "Car2Go").isEmpty());
		}
				
		// Test Invalid GasolineType
		assertThrows(InvalidGasTypeException.class, ()-> { service.getGasStationsWithoutCoordinates("methan", "Enjoy"); });

		// Test Invalid CarSharing
		assertThrows(InvalidCarSharingException.class, ()-> { service.getGasStationsWithoutCoordinates("Methane", "Enyoj"); });

		List<GasStation> emptyList = new ArrayList<GasStation>();
		
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(emptyList);
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(emptyList);
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(emptyList);
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(emptyList);
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(emptyList);
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(emptyList);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test Empty List filtered by GasolineType
		for (String gasolineType : gTypes) {
			assertTrue(service.getGasStationsWithoutCoordinates(gasolineType, "Enjoy").isEmpty());
			assertTrue(service.getGasStationsWithoutCoordinates(gasolineType, "Car2Go").isEmpty());
		}
		
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(list);
		when(gsRepository.findByCarSharing(anyString())).thenReturn(emptyList);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test Empty List filtered by CarSharing
		assertTrue(service.getGasStationsWithoutCoordinates("Diesel", "Car2Go").isEmpty());
		
		// Test both Empty Lists
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(emptyList);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		assertTrue(service.getGasStationsWithoutCoordinates("Diesel", "Enjoy").isEmpty());
		
	}
	
	
	@Test
	public void testGetGasStationsWithCoordinates() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		gsRepository = mock(GasStationRepository.class);
		
		when(gsRepository.findByProximity(anyDouble(), anyDouble(), anyInt())).thenReturn(new ArrayList<GasStation>());
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
			
		// Test Empty List filtered by proximity
		assertTrue(service.getGasStationsWithCoordinates(45.666667, 7.080660, 1, "Methane", "Enjoy").isEmpty());
		
		// Test Exception with Latitude
		assertThrows(GPSDataException.class, ()->{ service.getGasStationsWithCoordinates(-100, 43.25, 1,"Methane", "Enjoy"); });
						
		// Test Exception with Longitude
		assertThrows(GPSDataException.class, ()->{ service.getGasStationsWithCoordinates(43.25, 200, 1,"Methane", "Enjoy"); });

		/*
		 * CASE: GasolineType NOT EQUAL TO "NULL"
		 */
		List<GasStation> list = new ArrayList<GasStation>();
		list.add(new GasStation("Torino 1", "Via Vigone 3", true, false, true, false, true, false,
				"Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0));
				
		when(gsRepository.findByProximity(anyDouble(), anyDouble(), anyInt())).thenReturn(list);	
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(new ArrayList<GasStation>());
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test GasolineType == null (careful: not "null"!)
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, null, "Car2Go").isEmpty());
				
		// Test CarSharing == null (careful: not "null"!)
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "Diesel", null).isEmpty());
				
		// Test CarSharing == null and GasolineType == null (careful: not "null"!)
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, null, null).isEmpty());
		
		// Test all gasolineTypes misses: Empty Lists
		String[] gTypes = {"Diesel", "Super", "SuperPlus", "Gas", "Methane", "PremiumDiesel"};
		for (String gasolineType : gTypes) {
			assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, gasolineType, "null").isEmpty());
		}
		
		// Test Invalid GasolineType
		assertThrows(InvalidGasTypeException.class, ()-> { service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "methan", "Enjoy"); });

		List<GasStation> listGTMatching = new ArrayList<GasStation>();
		listGTMatching.add(list.get(0));

		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(listGTMatching);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test all gasolineTypes hits: MATCHING between List filtered by Proximity and List filtered by GasolineType
		for (String gasolineType : gTypes) {
			assertFalse(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, gasolineType, "null").isEmpty());
		}
		
		List<GasStation> listGTNoMatching = new ArrayList<GasStation>();
		listGTNoMatching.add(new GasStation("Torino 2", "Via Vigone 33", true, false, true, false, true, false,
				"Car2Go", 47.066667, 5.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0));
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(listGTNoMatching);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);

		// Test all gasolineTypes hits: NO MATCHING between List filtered by Proximity and List filtered by GasolineType
		for (String gasolineType : gTypes) {
			list.retainAll(listGTNoMatching);
			assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1,  gasolineType, "null").isEmpty());
		}
		
		/*
		 * CASE: CarSharing NOT EQUAL TO "NULL"
		 */
		list.add(new GasStation("Torino 1", "Via Vigone 3", true, false, true, false, true, false,
				"Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0));
		when(gsRepository.findByCarSharing(anyString())).thenReturn(new ArrayList<GasStation>());
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);		
		
		// Test Empty Lists filtered by CarSharing
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "Enjoy").isEmpty());
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "Car2Go").isEmpty());

		// Test Invalid CarSharing
		assertThrows(InvalidCarSharingException.class, ()-> { service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "enyoj"); });
		
		List<GasStation> listCSMatching = new ArrayList<GasStation>();
		listCSMatching.add(list.get(0));
		
		when(gsRepository.findByCarSharing(anyString())).thenReturn(listCSMatching);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);		
		
		// Test all CarSharing hits: MATCHING between List filtered by Proximity and List filtered by CarSharing
		assertFalse(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "Enjoy").isEmpty());
		assertFalse(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "Car2Go").isEmpty());

		List<GasStation> listCSNoMatching = new ArrayList<GasStation>();
		listCSNoMatching.add(new GasStation());
		
		when(gsRepository.findByCarSharing(anyString())).thenReturn(listCSNoMatching);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);		
		
		// Test all CarSharing hits: NO MATCHING between List filtered by Proximity and List filtered by CarSharing
		list.retainAll(listCSNoMatching);
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "Enjoy").isEmpty());
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "Car2Go").isEmpty());
		
		/*
		 * CASE: BOTH (GasolineType and CarSharing) NOT EQUAL TO "NULL"
		 */
		list.add(new GasStation("Torino 1", "Via Vigone 3", true, false, true, false, true, false,
				"Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0));
		
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(new ArrayList<GasStation>());
		when(gsRepository.findByCarSharing(anyString())).thenReturn(new ArrayList<GasStation>());
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test with one empty list filtered by GasolineType or CarSharing 
		for (String gasolineType : gTypes) {
			assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, gasolineType, "Enjoy").isEmpty());
			assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, gasolineType, "Car2Go").isEmpty());
		}
		
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(listGTMatching);
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(listGTMatching);
		when(gsRepository.findByCarSharing(anyString())).thenReturn(listCSMatching);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test with MATCHING between list filtered by Proximity and list filtered by GasolineType and CarSharing 
		for (String gasolineType : gTypes) {
			assertFalse(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, gasolineType, "Enjoy").isEmpty());
			assertFalse(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, gasolineType, "Car2Go").isEmpty());
		}
		
		when(gsRepository.findByHasDieselTrueOrderByDieselPrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasSuperTrueOrderBySuperPrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasSuperPlusTrueOrderBySuperPlusPrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasMethaneTrueOrderByMethanePrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasGasTrueOrderByGasPrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice()).thenReturn(listGTNoMatching);
		when(gsRepository.findByCarSharing(anyString())).thenReturn(listCSNoMatching);
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		// Test with NO MATCHING between list filtered by Proximity and list filtered by GasolineType and CarSharing 
		list.retainAll(listGTNoMatching);
		list.retainAll(listCSNoMatching);
		for (String gasolineType : gTypes) {
			assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, gasolineType, "Enjoy").isEmpty());
			assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, gasolineType, "Car2Go").isEmpty());
		}
		
		/*
		 * CASE: GasolineType EQUAL TO "NULL" AND CarSharing EQUAL TO "NULL"
		 */
		// Test Empty List filtered by Proximity
		when(gsRepository.findByProximity(anyDouble(), anyDouble(), anyInt())).thenReturn(new ArrayList<GasStation>());	
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		assertTrue(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "null").isEmpty());
		
		// Test Non-Empty List filtered by Proximity
		list.add(new GasStation("Torino 1", "Via Vigone 3", true, false, true, false, true, false,
				"Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, null, null, 0));
		
		when(gsRepository.findByProximity(anyDouble(), anyDouble(), anyInt())).thenReturn(list);	
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		assertFalse(service.getGasStationsWithCoordinates(45.06667, 7.07890, 1, "null", "null").isEmpty());
		
	}

	
}
