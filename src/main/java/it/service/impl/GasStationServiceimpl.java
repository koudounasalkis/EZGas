package it.polito.ezgas.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
import it.polito.ezgas.service.GasStationService;


/**
 * Created by softeng on 27/4/2020.
 */

@Service
public class GasStationServiceimpl implements GasStationService {
	
	private GasStationConverter gsConverter;
	
	private GasStationRepository repository;
	
	private UserRepository uRepository;
	
	public GasStationServiceimpl(GasStationConverter gsconv, GasStationRepository gsrepo, UserRepository urepo) {
		this.gsConverter = gsconv;
		this.repository = gsrepo;
		this.uRepository = urepo;
	}
	
	
	@Override
	public GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException {
		
		if (gasStationId == null)
			return null;
		
		if (gasStationId < 0)
			throw new InvalidGasStationException("Gas Station ID non-valid");
		
		if (repository.exists(gasStationId)) 
			return gsConverter.convertToDto(updateDependability(repository.findOne(gasStationId)));
		else
			return null;
		
	}

	
	@Override
	public GasStationDto saveGasStation(GasStationDto gasStationDto) throws PriceException, GPSDataException {
		
		if (gasStationDto.getGasStationName() == null || gasStationDto.getGasStationAddress() == null)
			return null;

		
		if (gasStationDto.getCarSharing().equals("null"))
			gasStationDto.setCarSharing(null);
		
		if (gasStationDto.getReportUser() != null && gasStationDto.getReportUser() != -1) {
			if (gasStationDto.getPremiumDieselPrice() < 0 || gasStationDto.getDieselPrice() < 0 || gasStationDto.getGasPrice() < 0 || gasStationDto.getMethanePrice() < 0 
					|| gasStationDto.getSuperPlusPrice() < 0 || gasStationDto.getSuperPrice() < 0)
				throw new PriceException("Invalid Negative Price");
		}
		
		if (gasStationDto.getLat() < -90 || gasStationDto.getLat() >= 90)
			throw new GPSDataException("Wrong latitude");
		
		if (gasStationDto.getLon() < -180 || gasStationDto.getLon() >= 180)
			throw new GPSDataException("Wrong longitude");
		
		//Here we set default value if the price is not yet inserted:
		if(gasStationDto.getHasDiesel() && gasStationDto.getDieselPrice()==null)
			gasStationDto.setDieselPrice(0.0);
		if(gasStationDto.getHasMethane() && gasStationDto.getMethanePrice()==null)
			gasStationDto.setMethanePrice(0.0);
		if(gasStationDto.getHasPremiumDiesel() && gasStationDto.getPremiumDieselPrice()==null)
			gasStationDto.setPremiumDieselPrice(0.0);
		if(gasStationDto.getHasGas() && gasStationDto.getGasPrice()==null)
			gasStationDto.setGasPrice(0.0);
		if(gasStationDto.getHasSuper() && gasStationDto.getSuperPrice()==null)
			gasStationDto.setSuperPrice(0.0);
		if(gasStationDto.getHasSuperPlus() && gasStationDto.getSuperPlusPrice()==null)
			gasStationDto.setSuperPlusPrice(0.0);
		
		return gsConverter.convertToDto(repository.save(gsConverter.convertToEntity(gasStationDto)));
		
	}

	
	@Override
	public List<GasStationDto> getAllGasStations() {
		return repository.findAll().stream()
				.map(this::updateDependability)
				.map(gsConverter::convertToDto)
				.collect(Collectors.toList());
	}

	
	@Override
	public Boolean deleteGasStation(Integer gasStationId) throws InvalidGasStationException {
		
		if (gasStationId == null)
			return false;
		
		if (gasStationId < 0)
			throw new InvalidGasStationException("Gas Station ID non-valid");
		
		if (repository.exists(gasStationId)) {
			repository.delete(gasStationId);
			return true;
		} else
			return false;
		
	}
	
	
	@Override
	public List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) throws InvalidGasTypeException {
		
		if (gasolinetype == null)
			return new ArrayList<GasStationDto>();
		
		List<GasStation> list;
		
		switch(gasolinetype) {
			case("Diesel"):
				list = repository.findByHasDieselTrueOrderByDieselPrice();
			break;
			case("Gas"): 
				list = repository.findByHasGasTrueOrderByGasPrice();
			break;
			case("Methane"): 
				list = repository.findByHasMethaneTrueOrderByMethanePrice();
			break;
			case("Super"):
				list = repository.findByHasSuperTrueOrderBySuperPrice();
			break;
			case("SuperPlus"): 
				list = repository.findByHasSuperPlusTrueOrderBySuperPlusPrice();
			break;
			case("PremiumDiesel"): 
			    list = repository.findByHasPremiumDieselTrueOrderByPremiumDieselPrice();
			break;
			default:
				throw new InvalidGasTypeException("Gasoline type non-valid");
		}
			return list.stream().map(this::updateDependability)
					.map(gsConverter::convertToDto)
					.collect(Collectors.toList());
			
	}
	
	
	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon) throws GPSDataException {
		
		if (lat < -90 || lat >= 90)
			throw new GPSDataException("Wrong latitude");
		
		if (lon < -180 || lon >= 180)
			throw new GPSDataException("Wrong longitude");
		
		return repository.findByProximity(lat, lon, 1)
					.stream().map(this::updateDependability)
					.map(gsConverter::convertToDto)
					.collect(Collectors.toList());
	}
	
	
	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon, int radius) throws GPSDataException {
		
		if (lat < -90 || lat >= 90)
			throw new GPSDataException("Wrong latitude");
		
		if (lon < -180 || lon >= 180)
			throw new GPSDataException("Wrong longitude");
		
		if (radius <= 0)
			return this.getGasStationsByProximity(lat, lon);
		else
			return repository.findByProximity(lat, lon, radius)
					.stream().map(this::updateDependability)
					.map(gsConverter::convertToDto)
					.collect(Collectors.toList());
	}


	
	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, int radius, String gasolinetype,
			String carsharing) throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		if (gasolinetype == null || carsharing == null)
			return new ArrayList<GasStationDto>();

		List<GasStationDto> listByProximity = this.getGasStationsByProximity(lat, lon, radius);

		if (!gasolinetype.equals("null") && !carsharing.contentEquals("null"))
			listByProximity.retainAll(this.getGasStationsWithoutCoordinates(gasolinetype, carsharing));

		else if (!gasolinetype.equals("null")) {
			List<GasStationDto> gasolineTypeFiltered = this.getGasStationsByGasolineType(gasolinetype);
			listByProximity.retainAll(gasolineTypeFiltered);
		} 
		
		else if (!carsharing.equals("null")) {
			if (!carsharing.equals("Enjoy") && !carsharing.equals("Car2Go"))
				throw new InvalidCarSharingException("Car Sharing name non-valid");
			List<GasStationDto> carSharingFiltered = this.getGasStationByCarSharing(carsharing);
			listByProximity.retainAll(carSharingFiltered);
		}
		
		return listByProximity;
		
	}
	
	
	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing)
			throws InvalidGasTypeException, InvalidCarSharingException {
		
		if (gasolinetype == null || carsharing == null)
			return new ArrayList<GasStationDto>();
		
		List<GasStationDto> gasolineTypeFiltered = getGasStationsByGasolineType(gasolinetype);
		
		if (!carsharing.equals("Enjoy") && !carsharing.equals("Car2Go"))
			throw new InvalidCarSharingException("Car Sharing name non-valid");
		
		List<GasStationDto> carSharingFiltered = getGasStationByCarSharing(carsharing);		
		
		// If both the lists aren't empty, return the intersection between the two of them
		gasolineTypeFiltered.retainAll(carSharingFiltered);
			return gasolineTypeFiltered;
			
	}

	
	@Override
	public void setReport(Integer gasStationId, Double dieselPrice, Double superPrice, Double superPlusPrice, Double gasPrice, Double methanePrice, Double premiumDieselPrice, Integer userId)
			throws InvalidGasStationException, PriceException, InvalidUserException {
		
		if (gasStationId == null || userId == null)
		 	return; 
		 
		if (gasStationId < 0) 
			throw new InvalidGasStationException("GasStation ID non-valid");
		
		if (userId < 0) 
			throw new InvalidUserException("User ID non-valid");
		
		if (repository.exists(gasStationId) && uRepository.exists(userId)) {
		
			GasStation gs = repository.findOne(gasStationId);
			
			if ((gs.getHasPremiumDiesel() && premiumDieselPrice < 0) ||
				(gs.getHasDiesel() && dieselPrice < 0)|| 
				(gs.getHasSuper() && superPrice < 0) || 
				(gs.getHasSuperPlus() && superPlusPrice < 0) ||
				(gs.getHasGas() && gasPrice < 0) || 
				(gs.getHasMethane() && methanePrice < 0))
					throw new PriceException("Invalid Negative Price");
			
			LocalDate date = LocalDate.now();
			User u = uRepository.findOne(userId);
			if (gs.getReportUser() != null && gs.getReportUser() != -1) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
				LocalDate date2 = LocalDate.parse(gs.getReportTimestamp(), dtf);
				if ((int)ChronoUnit.DAYS.between(date2, date) <= 4) {
					if (u.getReputation() >= gs.getUser().getReputation()) {
						gs.setDieselPrice(dieselPrice);
						gs.setGasPrice(gasPrice);
						gs.setSuperPrice(superPrice);
						gs.setSuperPlusPrice(superPlusPrice);
						gs.setMethanePrice(methanePrice);
						gs.setPremiumDieselPrice(premiumDieselPrice);
						gs.setReportUser(userId);
						gs.setReportTimestamp(date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
						gs.setUser(u);
						repository.save(gs);
						return;
					} else {
						return;
					}
				} 
			}
			gs.setDieselPrice(dieselPrice);
			gs.setGasPrice(gasPrice);
			gs.setSuperPrice(superPrice);
			gs.setSuperPlusPrice(superPlusPrice);
			gs.setMethanePrice(methanePrice);
			gs.setPremiumDieselPrice(premiumDieselPrice);
			gs.setReportUser(userId);
			gs.setReportTimestamp(date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
			gs.setUser(u);
			repository.save(gs);
		}
		
	}

	
	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {
		return repository.findByCarSharing(carSharing)
				.stream().map(this::updateDependability)
				.map(gsConverter::convertToDto)
				.collect(Collectors.toList());
		
	}		
	
	
	// Util function for updating Report Dependability
	public GasStation updateDependability(GasStation gasStation) {
		if (gasStation.getReportTimestamp() == null || gasStation.getUser() == null)
			return gasStation;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = LocalDate.parse(gasStation.getReportTimestamp(), dtf);
		Integer daysDiff = (int) ChronoUnit.DAYS.between(date2, date1);
		
		double newReportDependability;
		if (daysDiff > 7) 
			newReportDependability = 50*(gasStation.getUser().getReputation()+5)/10;
		else 
			newReportDependability = 50*(gasStation.getUser().getReputation()+5)/10 + 50*(1-daysDiff*1.0/7);
		
		gasStation.setReportDependability(Math.round(newReportDependability*100.0)/100.0);
		repository.flush();
		return gasStation;
		
	}

}


