package it.polito.ezgas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.polito.ezgas.entity.GasStation;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Integer> {
	
	List<GasStation> findByHasDieselTrueOrderByDieselPrice();
	
	List<GasStation> findByHasGasTrueOrderByGasPrice();
	
	List<GasStation> findByHasMethaneTrueOrderByMethanePrice();
	
	List<GasStation> findByHasSuperTrueOrderBySuperPrice();
	
	List<GasStation> findByHasSuperPlusTrueOrderBySuperPlusPrice();
	
	List<GasStation> findByHasPremiumDieselTrueOrderByPremiumDieselPrice();

	@Query(value = "SELECT * "
		+ " FROM Gas_Station"
		+ " WHERE (6372.797 * 0.017453300000075403 * atan2( sqrt(power(sin((lat - ?1)/2),2) "
		+ " + cos(lat) * cos(?1) * power(sin((lon - ?2)/2),2)), sqrt(1-power(sin((lat - ?1)/2),2) "
		+ " + cos(lat) * cos(?1) * power(sin((lon - ?2)/2),2)) )) <= ?3", nativeQuery = true)
	List<GasStation> findByProximity(double latitude, double longitude, int radius);
	
	List<GasStation> findByCarSharing(String carSharing);
}
