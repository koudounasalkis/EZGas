package it.polito.ezgas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.GasStationServiceimpl;
import it.polito.ezgas.service.impl.UserServiceimpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PerformanceTesting {
	
	private GasStationServiceimpl service;
	private UserServiceimpl uService;

	private UserConverter converter = new UserConverter();
	private GasStationConverter gsConverter = new GasStationConverter();
	
	@Autowired
	private GasStationRepository gsRepository;
	@Autowired
	private UserRepository uRepository;
	@Autowired
	private TestEntityManager entityManager;

	
	@Test(timeout = 500)
	public void testGetGasStationById() throws InvalidGasStationException {
		
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, 
				 false, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs);
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", false, true, false, 
				 true, false, true, "Car2Go", 45.066667, 7.666667, 0.0, 1.41, 0.0, 1.61, 0.0, 2.0,-1, null, 0);
		entityManager.persist(gs1);
		GasStation gs2 = new GasStation ("Torino 3", "Via Vigone 333", true, true, true, 
				 true, true, true, "Enjoy", 55.066667, 9.666667, 1.31, 1.41, 1.66, 1.21, 0.99, 2.0, -1, null, 0);
		entityManager.persist(gs2);
		GasStation gs3 = new GasStation ("Torino 4", "Via Vigone 3", false, false, false, 
				 false, true, true, "Car2Go", 45.066667, 7.666667, 0.0, 0.0, 0.0, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs3);
		GasStation gs4 = new GasStation ("Torino 5", "Via Revello 3", true, false, false, 
				 false, false, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs4);
		GasStation gs5 = new GasStation ("Torino 6", "Via Revello 333", true, false, true, 
				 false, true, true, "Car2Go", 55.066667, 9.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs5);
		GasStation gs6 = new GasStation ("Torino 7", "Via Revello 333", true, true, true, 
				 true, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs6);
		GasStation gs7 = new GasStation ("Torino 8", "Via Frejus 3", true, false, true, 
				 false, true, true, "Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs7);
		GasStation gs8 = new GasStation ("Torino 9", "Via Frejus 33", false, false, true, 
				 false, true, true, "null", 55.066667, 9.666667, 0.0, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs8);
		GasStation gs9 = new GasStation ("Torino 10", "Via Frejus 333", true, true, true, 
				 true, true, true, "null", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs9);
		
		Integer gsId = gs.getGasStationId();
		
		GasStationDto gsDto = gsConverter.convertToDto(gs);
		
	    service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
	    // Test Normal Case: finding a Gas Station given its GasStationId
	    assertTrue(new ReflectionEquals(gsDto).matches(service.getGasStationById(gsId)));    
	}
	
	
	@Test(timeout = 500)
	public void testSaveGasStation() throws PriceException, GPSDataException {
		
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, 
				 false, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs);
		GasStationDto gsDto = gsConverter.convertToDto(gs);
	   
		// Test Normal Case: saving a Gas Station
		assertNotNull(service.saveGasStation(gsDto));
		 
		}
		
	
	@Test(timeout = 500)
	public void testGetAllGasStations() {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
		// Test Non-Empty List
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, 
				 false, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs);
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", false, true, false, 
				 true, false, true, "Car2Go", 45.066667, 7.666667, 0.0, 1.41, 0.0, 1.61, 0.0, 2.0, -1, null, 0);
		entityManager.persist(gs1);
		GasStation gs2 = new GasStation ("Torino 3", "Via Vigone 333", true, true, true, 
				 true, true, true, "Enjoy", 55.066667, 9.666667, 1.31, 1.41, 1.66, 1.21, 0.99, 2.0, -1, null, 0);
		entityManager.persist(gs2);
		GasStation gs3 = new GasStation ("Torino 4", "Via Vigone 3", false, false, false, 
				 false, true, true, "Car2Go", 45.066667, 7.666667, 0.0, 0.0, 0.0, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs3);
		GasStation gs4 = new GasStation ("Torino 5", "Via Revello 3", true, false, false, 
				 false, false, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs4);
		GasStation gs5 = new GasStation ("Torino 6", "Via Revello 333", true, false, true, 
				 false, true, true, "Car2Go", 55.066667, 9.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs5);
		GasStation gs6 = new GasStation ("Torino 7", "Via Revello 333", true, true, true, 
				 true, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs6);
		GasStation gs7 = new GasStation ("Torino 8", "Via Frejus 3", true, false, true, 
				 false, true, true, "Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs7);
		GasStation gs8 = new GasStation ("Torino 9", "Via Frejus 33", false, false, true, 
				 false, true, true, "null", 55.066667, 9.666667, 0.0, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs8);
		GasStation gs9 = new GasStation ("Torino 10", "Via Frejus 333", true, true, true, 
				 true, true, true, "null", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs9);
	
		assertFalse(service.getAllGasStations().isEmpty());
		
	}
	
	
	@Test(timeout = 500)
	public void testDeleteGasStation() throws InvalidGasStationException {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
	     // Test Normal Case: deleting a Gas Station given its GasStationId
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, false, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs);
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", false, true, false, true, false,  true,"Car2Go", 45.066667, 7.666667, 0.0, 1.41, 0.0, 1.61, 0.0, 2.0,  -1, null, 0);
		entityManager.persist(gs1);
		GasStation gs2 = new GasStation ("Torino 3", "Via Vigone 333", true, true, true, true, true,  true,"Enjoy", 55.066667, 9.666667, 1.31, 1.41, 1.66, 1.21, 0.99, 2.0,  -1, null, 0);
		entityManager.persist(gs2);
		GasStation gs3 = new GasStation ("Torino 4", "Via Vigone 3", false, false, false, false, true,  true,"Car2Go", 45.066667, 7.666667, 0.0, 0.0, 0.0, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs3);
		GasStation gs4 = new GasStation ("Torino 5", "Via Revello 3", true, false, false, false, false, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89,2.0,  -1, null, 0);
		entityManager.persist(gs4);
		GasStation gs5 = new GasStation ("Torino 6", "Via Revello 333", true, false, true, false, true, true, "Car2Go", 55.066667, 9.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs5);
		GasStation gs6 = new GasStation ("Torino 7", "Via Revello 333", true, true, true, true, true,  true,"Enjoy", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs6);
		GasStation gs7 = new GasStation ("Torino 8", "Via Frejus 3", true, false, true, false, true, true, "Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs7);
		GasStation gs8 = new GasStation ("Torino 9", "Via Frejus 33", false, false, true, false, true, true, "null", 55.066667, 9.666667, 0.0, 0.0, 1.56, 0.0, 0.89,2.0,  -1, null, 0);
		entityManager.persist(gs8);
		GasStation gs9 = new GasStation ("Torino 10", "Via Frejus 333", true, true, true, true, true, true, "null", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs9);
		
	     assertTrue(service.deleteGasStation(gs.getGasStationId()));
	     
	}
	
	
	@Test(timeout = 500)
	public void testGetGasStationsByGasolineType() throws InvalidGasTypeException {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
			
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, false, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89,2.0, -1, null, 0);
		entityManager.persist(gs);
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", false, true, false, true, false, true, "Car2Go", 45.066667, 7.666667, 0.0, 1.41, 0.0, 1.61, 0.0,2.0, -1, null, 0);
		entityManager.persist(gs1);
		GasStation gs2 = new GasStation ("Torino 3", "Via Vigone 333", true, true, true, true, true, true, "Enjoy", 55.066667, 9.666667, 1.31, 1.41, 1.66, 1.21, 0.99,2.0, -1, null, 0);
		entityManager.persist(gs2);
		GasStation gs3 = new GasStation ("Torino 4", "Via Vigone 3", false, false, false, false, true, true, "Car2Go", 45.066667, 7.666667, 0.0, 0.0, 0.0, 0.0, 0.89,2.0, -1, null, 0);
		entityManager.persist(gs3);
		GasStation gs4 = new GasStation ("Torino 5", "Via Revello 3", true, false, false, false, false,  true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89,2.0, -1, null, 0);
		entityManager.persist(gs4);
		GasStation gs5 = new GasStation ("Torino 6", "Via Revello 333", true, false, true, false, true,  true, "Car2Go", 55.066667, 9.666667, 1.41, 0.0, 1.56, 0.0, 0.89,2.0, -1, null, 0);
		entityManager.persist(gs5);
		GasStation gs6 = new GasStation ("Torino 7", "Via Revello 333", true, true, true, true, true, true,  "Enjoy", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89,2.0, -1, null, 0);
		entityManager.persist(gs6);
		GasStation gs7 = new GasStation ("Torino 8", "Via Frejus 3", true, false, true, false, true, true,  "Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89,2.0, -1, null, 0);
		entityManager.persist(gs7);
		GasStation gs8 = new GasStation ("Torino 9", "Via Frejus 33", false, false, true, false, true,  true, "null", 55.066667, 9.666667, 0.0, 0.0, 1.56, 0.0, 0.89,2.0, -1, null, 0);
		entityManager.persist(gs8);
		GasStation gs9 = new GasStation ("Torino 10", "Via Frejus 333", true, true, true, true, true,  true, "null", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs9);
		 
		// Test Diesel hit
		assertFalse(service.getGasStationsByGasolineType("Diesel").isEmpty());
					
	}
		
	
	@Test(timeout = 500)
	public void testGetGasStationsByProximity() throws GPSDataException {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
							
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, false, true, true,"Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0,-1, null, 0);
		entityManager.persist(gs);
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", false, true, false, true, false, true, "Car2Go", 45.066667, 7.666667, 0.0, 1.41, 0.0, 1.61, 0.0, 2.0,-1, null, 0);
		entityManager.persist(gs1);
		GasStation gs2 = new GasStation ("Torino 3", "Via Vigone 333", true, true, true, true, true,  true,"Enjoy", 55.066667, 9.666667, 1.31, 1.41, 1.66, 1.21, 0.99, 2.0,-1, null, 0);
		entityManager.persist(gs2);
		GasStation gs3 = new GasStation ("Torino 4", "Via Vigone 3", false, false, false, false, true, true, "Car2Go", 45.066667, 7.666667, 0.0, 0.0, 0.0, 0.0, 0.89, 2.0,-1, null, 0);
		entityManager.persist(gs3);
		GasStation gs4 = new GasStation ("Torino 5", "Via Revello 3", true, false, false, false, false, true,"Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0,-1, null, 0);
		entityManager.persist(gs4);
		GasStation gs5 = new GasStation ("Torino 6", "Via Revello 333", true, false, true, false, true, true, "Car2Go", 55.066667, 9.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0,-1, null, 0);
		entityManager.persist(gs5);
		GasStation gs6 = new GasStation ("Torino 7", "Via Revello 333", true, true, true, true, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0,-1, null, 0);
		entityManager.persist(gs6);
		GasStation gs7 = new GasStation ("Torino 8", "Via Frejus 3", true, false, true, false, true,  true,"Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0,-1, null, 0);
		entityManager.persist(gs7);
		GasStation gs8 = new GasStation ("Torino 9", "Via Frejus 33", false, false, true, false, true,  true,"null", 55.066667, 9.666667, 0.0, 0.0, 1.56, 0.0, 0.89, 2.0,-1, null, 0);
		entityManager.persist(gs8);
		GasStation gs9 = new GasStation ("Torino 10", "Via Frejus 333", true, true, true, true, true,  true,"null", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0,-1, null, 0);
		entityManager.persist(gs9);
		
		// Test Non-Empty List
		assertFalse(service.getGasStationsByProximity(45.066775, 7.666823, 1).isEmpty());
		
	}
	
	
	@Test(timeout = 500)
	public void testGetGasStationByCarSharing() {
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);	
		
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, false, true,  true,"Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs);
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", false, true, false, true, false, true, "Car2Go", 45.066667, 7.666667, 0.0, 1.41, 0.0, 1.61, 0.0, 2.0, -1, null, 0);
		entityManager.persist(gs1);
		GasStation gs2 = new GasStation ("Torino 3", "Via Vigone 333", true, true, true, true, true, true, "Enjoy", 55.066667, 9.666667, 1.31, 1.41, 1.66, 1.21, 0.99, 2.0, -1, null, 0);
		entityManager.persist(gs2);
		GasStation gs3 = new GasStation ("Torino 4", "Via Vigone 3", false, false, false, false, true, true, "Car2Go", 45.066667, 7.666667, 0.0, 0.0, 0.0, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs3);
		GasStation gs4 = new GasStation ("Torino 5", "Via Revello 3", true, false, false, false, false, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs4);
		GasStation gs5 = new GasStation ("Torino 6", "Via Revello 333", true, false, true, false, true, true, "Car2Go", 55.066667, 9.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs5);
		GasStation gs6 = new GasStation ("Torino 7", "Via Revello 333", true, true, true, true, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs6);
		GasStation gs7 = new GasStation ("Torino 8", "Via Frejus 3", true, false, true, false, true, true, "Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs7);
		GasStation gs8 = new GasStation ("Torino 9", "Via Frejus 33", false, false, true, false, true, true, "null", 55.066667, 9.666667, 0.0, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs8);
		GasStation gs9 = new GasStation ("Torino 10", "Via Frejus 333", true, true, true, true, true, true, "null", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89,2.0,  -1, null, 0);
		entityManager.persist(gs9);
		
		// Test Finding something with "Enjoy"
		assertFalse(service.getGasStationByCarSharing("Enjoy").isEmpty());
				
	}
	
	
	@Test(timeout = 500)
	public void getGasStationsWithoutCoordinates() throws InvalidGasTypeException, InvalidCarSharingException {
		
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
	
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, false, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs);
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", false, true, false, true, false, true, "Car2Go", 45.066667, 7.666667, 0.0, 1.41, 0.0, 1.61, 0.0, 2.0, -1, null, 0);
		entityManager.persist(gs1);
		GasStation gs2 = new GasStation ("Torino 3", "Via Vigone 333", true, true, true, true, true, true, "Enjoy", 55.066667, 9.666667, 1.31, 1.41, 1.66, 1.21, 0.99, 2.0, -1, null, 0);
		entityManager.persist(gs2);
		GasStation gs3 = new GasStation ("Torino 4", "Via Vigone 3", false, false, false, false, true, true, "Car2Go", 45.066667, 7.666667, 0.0, 0.0, 0.0, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs3);
		GasStation gs4 = new GasStation ("Torino 5", "Via Revello 3", true, false, false, false, false, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs4);
		GasStation gs5 = new GasStation ("Torino 6", "Via Revello 333", true, false, true, false, true, true, "Car2Go", 55.066667, 9.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs5);
		GasStation gs6 = new GasStation ("Torino 7", "Via Revello 333", true, true, true, true, true, true, "Enjoy", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs6);
		GasStation gs7 = new GasStation ("Torino 8", "Via Frejus 3", true, false, true, false, true, true, "Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs7);
		GasStation gs8 = new GasStation ("Torino 9", "Via Frejus 33", false, false, true, false, true, true, "null", 55.066667, 9.666667, 0.0, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs8);
		GasStation gs9 = new GasStation ("Torino 10", "Via Frejus 333", true, true, true, true, true, true, "null", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs9);
		
		assertFalse(service.getGasStationsWithoutCoordinates("Diesel", "Enjoy").isEmpty());
		
	}
	
	
	@Test(timeout = 500)
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
		
	}
	
	
	@Test(timeout = 500)
	public void testSetReport() throws InvalidGasStationException, PriceException, InvalidUserException {
		
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);		

		User u = new User("mario", "password", "name@ezgas.it", 4);
		entityManager.persist(u);
		Integer idU = u.getUserId();
		
		GasStation gs = new GasStation ("Torino 2", "Via Vigone 33", true , true, true, true, true, true, "Car2Go", 47.066667, 9.666667, 0.91, 1.46, 1.56, 0.79, 0.89, 2.0, 1, "06-05-2020", 0);
		entityManager.persist(gs);
		
		Integer id = gs.getGasStationId();
	
		// Test Normal Set Report 
		service.setReport(id, 1.41, 1.51, 1.61, 0.81, 0.91, 3.0, idU);

		assertTrue(gs.getGasStationId() == id
				&& gs.getDieselPrice() == 1.41
				&& gs.getSuperPrice() == 1.51
				&& gs.getSuperPlusPrice() == 1.61
				&& gs.getGasPrice() == 0.81
				&& gs.getMethanePrice() == 0.91
				&& gs.getPremiumDieselPrice() == 3.0
				&& gs.getReportUser() == idU
				&& gs.getUser() == u
				&& gs.getReportTimestamp().equals(LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))));
		
	}
	
	
	@Test(timeout = 500)
	public void testGetGasStationsWithCoordinates() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		service = new GasStationServiceimpl(gsConverter, gsRepository, uRepository);
		
		GasStation gs = new GasStation ("Torino 1", "Via Vigone 3", true, false, true, false, true, true,"Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs);
		GasStation gs1 = new GasStation ("Torino 2", "Via Vigone 33", false, true, false, true, false,  true,"Car2Go", 45.066667, 7.666667, 0.0, 1.41, 0.0, 1.61, 0.0, 2.0, -1, null, 0);
		entityManager.persist(gs1);
		GasStation gs2 = new GasStation ("Torino 3", "Via Vigone 333", true, true, true, true, true, true, "Enjoy", 55.066667, 9.666667, 1.31, 1.41, 1.66, 1.21, 0.99, 2.0, -1, null, 0);
		entityManager.persist(gs2);
		GasStation gs3 = new GasStation ("Torino 4", "Via Vigone 3", false, false, false, false, true, true,"Car2Go", 45.066667, 7.666667, 0.0, 0.0, 0.0, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs3);
		GasStation gs4 = new GasStation ("Torino 5", "Via Revello 3", true, false, false, false, false, true, "Enjoy", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs4);
		GasStation gs5 = new GasStation ("Torino 6", "Via Revello 333", true, false, true, false, true, true, "Car2Go", 55.066667, 9.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs5);
		GasStation gs6 = new GasStation ("Torino 7", "Via Revello 333", true, true, true, true, true,  true,"Enjoy", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs6);
		GasStation gs7 = new GasStation ("Torino 8", "Via Frejus 3", true, false, true, false, true, true, "Car2Go", 45.066667, 7.666667, 1.41, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs7);
		GasStation gs8 = new GasStation ("Torino 9", "Via Frejus 33", false, false, true, false, true,  true,"null", 55.066667, 9.666667, 0.0, 0.0, 1.56, 0.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs8);
		GasStation gs9 = new GasStation ("Torino 10", "Via Frejus 333", true, true, true, true, true, true, "null", 45.066667, 7.666667, 1.41, 1.0, 1.56, 1.0, 0.89, 2.0, -1, null, 0);
		entityManager.persist(gs9);
		
		// Test Normal Case
		assertFalse(service.getGasStationsWithCoordinates(45.066667, 7.666667, 1, "Diesel", "Enjoy").isEmpty());

	}
	
	
	@Test(timeout = 500)
	public void testGetUserById() throws InvalidUserException {
		User u = new User("test", "password", "test@email.it", 4);
		entityManager.persist(u);
		UserDto uDto = new UserDto();
		uDto.setEmail(u.getEmail());
		uDto.setPassword(u.getPassword());
		uDto.setReputation(u.getReputation());
		uDto.setUserId(u.getUserId());
		uDto.setUserName(u.getUserName());
		
		uService = new UserServiceimpl(uRepository, converter);
		
		// Test Normal Case: find User given his UserId 
		assertTrue(new ReflectionEquals(uDto).matches(uService.getUserById(u.getUserId())));
	
	}
	
	
	@Test(timeout = 500)
	public void testSaveUser() {
		
		 UserDto uDto = new UserDto(null, "Harry", "password", "harry@email.it", 2);
		 User u = new User();
		 u.setEmail("harry@email.it");
		 u.setPassword("password");
		 u.setReputation(2);
		 u.setUserId(null);
		 u.setUserName("Harry");
		 
		 uService = new UserServiceimpl(uRepository, converter);
		 
		 // Test Normal Case: saving a User 
		 assertTrue(uService.saveUser(uDto) != null);
		  
	}
	
	
	@Test(timeout = 500)
	public void testGetAllUsers() {
		
		User u1 = new User("mario", "password", "admin@ezgas.com", 5);
		entityManager.persist(u1);
		User u2 = new User("test", "password", "test@email.it", 4);
		entityManager.persist(u2);
		User u3 = new User("maria", "password", "maria@ezgas.com", 1);
		entityManager.persist(u3);
		User u4 = new User("francesco", "password", "francesco@email.it", 4);
		entityManager.persist(u4);
		User u5 = new User("mario", "password", "admin@ezgas.com", 5);
		entityManager.persist(u5);
		User u6 = new User("test", "password", "test@email.it", 4);
		entityManager.persist(u6);
		User u7 = new User("maria", "password", "maria@ezgas.com", 1);
		entityManager.persist(u7);
		User u8 = new User("francesco", "password", "francesco@email.it", 4);
		entityManager.persist(u8);
		
		uService = new UserServiceimpl(uRepository, converter);
		
		//test that returns non empty list
		assertFalse(uService.getAllUsers().isEmpty());
		
	}
	
	
	@Test(timeout = 500) 
	public void testDeleteUser() throws InvalidUserException {
		
		User u = new User("mario", "password", "admin@ezgas.com", 5);
		entityManager.persist(u);
			
		uService = new UserServiceimpl(uRepository,converter);
		
		//test normal delete
		assertTrue(uService.deleteUser(u.getUserId()));
		
	}
	
	
	@Test(timeout = 500)
	public void testLogin() throws InvalidLoginDataException {
		
		User u = new User("mario", "password", "name@ezgas.it", 4);
		entityManager.persist(u);
		IdPw idpw = new IdPw("name@ezgas.it","password");
		LoginDto log = new LoginDto(u.getUserId(), "mario", null, "name@ezgas.it", 4);
		
		uService = new UserServiceimpl(uRepository,converter);
		
		//test normal login
		assertTrue( new ReflectionEquals(log).matches(uService.login(idpw)));	
		
	}
	
	
	@Test(timeout = 500)
	public void testIncreaseUserReputation() throws InvalidUserException {
		
		Integer reputationValid = 4;
		
		User u = new User("mario","password","name@ezgas.it",reputationValid);
		entityManager.persist(u);
		
		uService = new UserServiceimpl(uRepository,converter);
		
		//Test normal
		assertSame(uService.increaseUserReputation(u.getUserId()), reputationValid+1); 
	  	
	}
	
	
	@Test(timeout = 500)
	public void testDecreaseUserReputation() throws InvalidUserException {
		
		Integer reputationValid = 4;
		
		User u = new User("mario","password","name@ezgas.it",reputationValid);
		entityManager.persist(u);
		
		uService = new UserServiceimpl(uRepository,converter);
		
		//Test normal
		assertSame(uService.decreaseUserReputation(u.getUserId()), reputationValid-1);
		
	}
		
}
