package it.polito.ezgas.converter;

import org.springframework.stereotype.Component;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;

@Component
public class GasStationConverter {
	
	private UserConverter uConverter = new UserConverter();
	
	
	public GasStationDto convertToDto(GasStation gasStation) {
		GasStationDto gs = new GasStationDto(
				gasStation.getGasStationId(),
				gasStation.getGasStationName(),
				gasStation.getGasStationAddress(),
				gasStation.getHasDiesel(),
				gasStation.getHasSuper(),
				gasStation.getHasSuperPlus(),
				gasStation.getHasGas(),
				gasStation.getHasMethane(),
				gasStation.getHasPremiumDiesel(),
				gasStation.getCarSharing(),
				gasStation.getLat(),
				gasStation.getLon(),
				gasStation.getDieselPrice(),
				gasStation.getSuperPrice(),
				gasStation.getSuperPlusPrice(),
				gasStation.getGasPrice(),
				gasStation.getMethanePrice(),
				gasStation.getPremiumDieselPrice(),
				gasStation.getReportUser(),
				gasStation.getReportTimestamp(),
				gasStation.getReportDependability()
				);
		if (gasStation.getUser() != null)
			gs.setUserDto(uConverter.convertToDto(gasStation.getUser()));
		return gs;
	}
	
	
	public GasStation convertToEntity(GasStationDto gasStationDto) {
		GasStation gs = new GasStation(
				gasStationDto.getGasStationName(),
				gasStationDto.getGasStationAddress(),
				gasStationDto.getHasDiesel(),
				gasStationDto.getHasSuper(),
				gasStationDto.getHasSuperPlus(),
				gasStationDto.getHasGas(),
				gasStationDto.getHasMethane(),
				gasStationDto.getHasPremiumDiesel(),
				gasStationDto.getCarSharing(),
				gasStationDto.getLat(),
				gasStationDto.getLon(),
				gasStationDto.getDieselPrice(),
				gasStationDto.getSuperPrice(),
				gasStationDto.getSuperPlusPrice(),
				gasStationDto.getGasPrice(),
				gasStationDto.getMethanePrice(),
				gasStationDto.getPremiumDieselPrice(),
				gasStationDto.getReportUser(),
				gasStationDto.getReportTimestamp(),
				gasStationDto.getReportDependability());
		gs.setGasStationId(gasStationDto.getGasStationId());
		
		if (gasStationDto.getUserDto() != null)
			gs.setUser(uConverter.convertToEntity(gasStationDto.getUserDto()));
		return gs;
	}	
}
