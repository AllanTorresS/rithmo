package br.com.rhitmo.hotel.service.impl;

import br.com.rhitmo.hotel.controller.dto.RoomRequestDTO;
import br.com.rhitmo.hotel.controller.dto.RoomResponseDTO;
import br.com.rhitmo.hotel.model.Room;
import br.com.rhitmo.hotel.repository.RoomRepository;
import br.com.rhitmo.hotel.service.RoomService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public RoomResponseDTO create(RoomRequestDTO requestDTO) {
        var entity = mapToEntity(requestDTO);
        repository.save(entity);
        return mapToDto(entity);
    }

    @Override
    public List<RoomResponseDTO> findAll() {
        var rooms = repository.findAll();

        return  rooms.stream().
                map(element -> mapper.map(element, RoomResponseDTO.class))
                .toList();
    }

    private Room mapToEntity(RoomRequestDTO requestDTO){

        var entity = mapper.map(requestDTO, Room.class);
        entity.setStatus(requestDTO.getStatus().getValue());
        return entity;
    }

    private RoomResponseDTO mapToDto(Room room){
        return mapper.map(room, RoomResponseDTO.class);
    }
}
