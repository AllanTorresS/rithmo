package br.com.rhitmo.hotel.controller;

import br.com.rhitmo.hotel.controller.dto.RoomRequestDTO;
import br.com.rhitmo.hotel.controller.dto.RoomResponseDTO;
import br.com.rhitmo.hotel.service.RoomService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/v1/room", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@RequestBody @Valid RoomRequestDTO requestDTO) {

        log.info("Tentando salvar um quarto: {}", requestDTO.toString());

        var response = roomService.create(requestDTO);

        log.info("Sucesso ao salvar um quarto: {}", response.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> listAll() {

        log.info("Tentando buscar todos os quartos");

        var rooms = roomService.findAll();

        log.info("Sucesso ao buscar todos os quartos: {}", rooms.toString());

        return ResponseEntity.status(rooms.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(rooms);
    }

}
