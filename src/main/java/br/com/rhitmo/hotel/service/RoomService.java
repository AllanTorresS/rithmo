package br.com.rhitmo.hotel.service;

import br.com.rhitmo.hotel.controller.dto.RoomRequestDTO;
import br.com.rhitmo.hotel.controller.dto.RoomResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface RoomService {

    public RoomResponseDTO create(RoomRequestDTO requestDTO);

    List<RoomResponseDTO> findAll();
}
