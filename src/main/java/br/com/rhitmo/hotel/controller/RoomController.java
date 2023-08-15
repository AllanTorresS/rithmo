package br.com.rhitmo.hotel.controller;

import br.com.rhitmo.hotel.controller.dto.RoomRequestDTO;
import br.com.rhitmo.hotel.controller.dto.RoomResponseDTO;
import br.com.rhitmo.hotel.model.Room;
import br.com.rhitmo.hotel.repository.RoomRepository;
import br.com.rhitmo.hotel.service.RoomService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/room", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@RequestBody @Valid RoomRequestDTO requestDTO) {

        var response = roomService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> listAll() {

        var rooms = roomService.findAll();

        return ResponseEntity.status(rooms.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(rooms);
    }

}
