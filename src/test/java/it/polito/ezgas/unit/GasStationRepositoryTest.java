package it.polito.ezgas.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.repository.GasStationRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
public class GasStationRepositoryTest {

    @Autowired
    private GasStationRepository repository;
      
    
    @Test
   	public void testFindByHasDieselTrueOrderByDieselPrice() { 
    	
   		double dieselPrice = 1.57;

     	GasStation gs = new GasStation();
   		gs.setDieselPrice(dieselPrice);
   		gs.setHasDiesel(true);
   		repository.save(gs);
   		
   		assertTrue(repository.findByHasDieselTrueOrderByDieselPrice().contains(gs));
   	}
    
    
    @Test
   	public void testFindByHasGasTrueOrderByGasPrice() { 
    	
   		double gasPrice = 1.27;

     	GasStation gs = new GasStation();
   		gs.setGasPrice(gasPrice);
   		gs.setHasGas(true);
   		repository.save(gs);
   		
   		assertTrue(repository.findByHasGasTrueOrderByGasPrice().contains(gs));
   	}
    
    
    @Test
   	public void testFindByHasMethaneTrueOrderByMethanePrice() { 
    	
   		double methanePrice = 1.07;

     	GasStation gs = new GasStation();
   		gs.setMethanePrice(methanePrice);
   		gs.setHasMethane(true);
   		repository.save(gs);
   		
   		assertTrue(repository.findByHasMethaneTrueOrderByMethanePrice().contains(gs));
   	}
    
    
    @Test
   	public void testFindByHasSuperTrueOrderBySuperPrice() { 
    	
   		double superPrice = 1.67;

     	GasStation gs = new GasStation();
   		gs.setSuperPrice(superPrice);
   		gs.setHasSuper(true);
   		repository.save(gs);
   		
   		assertTrue(repository.findByHasSuperTrueOrderBySuperPrice().contains(gs));
   	}
    
    
    @Test
   	public void testFindByHasSuperPlusTrueOrderBySuperPlusPrice() { 
    	
   		double superPlusPrice = 1.67;

     	GasStation gs = new GasStation();
   		gs.setSuperPlusPrice(superPlusPrice);
   		gs.setHasSuperPlus(true);
   		repository.save(gs);
   		
   		assertTrue(repository.findByHasSuperPlusTrueOrderBySuperPlusPrice().contains(gs));
   	}
    
    
    @Test
   	public void testFindByHasPremiumDieselTrueOrderByPremiumDieselPrice() { 
    	
   		double premiumDieselPrice = 1.67;

     	GasStation gs = new GasStation();
   		gs.setPremiumDieselPrice(premiumDieselPrice);
   		gs.setHasPremiumDiesel(true);
   		repository.save(gs);
   		
   		assertTrue(repository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice().contains(gs));
   	}

    
    @Test
	public void testFindByProximity() {
    	
    	// Reference latitude and longitude (Porta Susa - Turin)
		double latitude = 45.0726717;
		double longitude = 7.6658872;
			
		// Test < 1km (Corso Giacomo Matteotti, 950m from Porta Susa according to Google Maps)
		GasStation gs = new GasStation();
		gs.setLat(45.0651084);
		gs.setLon(7.6784244);
		repository.save(gs);
		assertTrue(repository.findByProximity(latitude, longitude, 1).contains(gs));
		
		// Test > 1km (Corso Re Umberto, 2100m from Porta Susa according to Google Maps)
		GasStation gs1 = new GasStation();
		gs1.setLat(45.0523283);
		gs1.setLon(7.684920476073063);
		repository.save(gs1);
		assertFalse(repository.findByProximity(latitude, longitude, 1).contains(gs1));
		assertTrue(repository.findByProximity(latitude, longitude, 2).contains(gs1));

		
		/*
		 * All these asserts will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * GasStation gs3 = new GasStation();
		 * gs3.setLat(200.010101);
		 * gs3.setLon(7.666671);
		 * repository.save(gs3);
		 * assertThrows(GPSDataException.class, () -> { repository.findByProximity(latitude, longitude, 1).contains(gs3); });
		 * 
		 * gs3.setLat(-200.010101);
		 * repository.save(gs3);
		 * assertThrows(GPSDataException.class, () -> { repository.findByProximity(latitude, longitude, 1).contains(gs3); });
		 * 
		 * gs3.setLat(45.06661);
		 * gs3.setLon(200.010101);
		 * repository.save(gs3);
		 * assertThrows(GPSDataException.class, () -> { repository.findByProximity(latitude, longitude, 1).contains(gs3); });
		 * 
		 * gs3.setLon(-200.010101);
		 * repository.save(gs3);
		 * assertThrows(GPSDataException.class, () -> { repository.findByProximity(latitude, longitude, 1).contains(gs3); });
		 */
		
	}
    
    
    @Test
	public void testFindByCarSharing() {
		
		GasStation gs = new GasStation();
		
		String carsharingEnjoy = "Enjoy";
		gs.setCarSharing(carsharingEnjoy);
		repository.save(gs);
		
		assertTrue(repository.findByCarSharing(carsharingEnjoy).contains(gs));
		
		String carsharingCar2Go = "Car2Go";
		gs.setCarSharing(carsharingCar2Go);
		repository.save(gs);
		
		assertTrue(repository.findByCarSharing(carsharingCar2Go).contains(gs));
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * String carsharingToBike = "ToBike";
		 * gs.setCarSharing(carsharingToBike);
		 * repository.save(gs);
		 * assertThrows(InvalidCarSharingException.class, () -> { repository.findByCarSharing(carsharingToBike).contains(gs); });
		 */
		
	}    

}

