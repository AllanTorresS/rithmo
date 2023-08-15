package br.com.rhitmo.hotel.controller;

import br.com.rhitmo.hotel.controller.dto.ReservationRequestDTO;
import br.com.rhitmo.hotel.controller.dto.ReservationResponseDTO;
import br.com.rhitmo.hotel.service.ReservationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/v1/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    @Autowired
    private ReservationService service;

    @PostMapping("/room/{roomId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponseDTO> create(@RequestBody @Valid ReservationRequestDTO requestDTO, @PathVariable Long roomId) {

        log.info("Tentando fazer uma reservar: {}", requestDTO.toString());

        var response = service.create(requestDTO, roomId);

        log.info("Sucesso ao fazer uma reserva: {}", response.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long reservationId) {

        log.info("Tentando cancelar a reservar de id: {}", reservationId);

        service.cancel(reservationId);

        log.info("Sucesso ao cancelar a reservar de id: {}", reservationId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{reservationId}/room/{roomId}")
    public ResponseEntity<Void> update(@RequestBody @Valid ReservationRequestDTO requestDTO,
                                       @PathVariable Long reservationId, @PathVariable Long roomId) {

        log.info("Tentando atualizar a reserva de id: {} , do quarto de id: {} com os dados: {}", roomId, reservationId, requestDTO.toString());

        service.update(requestDTO, reservationId, roomId);

        log.info("Sucesso em  atualizar a reserva de id: {} , do quarto de id: {} com os dados: {}", roomId, reservationId, requestDTO.toString());

        return ResponseEntity.noContent().build();
    }
}
