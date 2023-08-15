package br.com.rhitmo.hotel.configuration;


import br.com.rhitmo.hotel.controller.dto.RoomRequestDTO;
import br.com.rhitmo.hotel.model.Room;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {

		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(RoomRequestDTO.class, Room.class)
				.addMappings(mapper -> mapper.skip(Room::setStatus));

		return  modelMapper;
	}
	
}
