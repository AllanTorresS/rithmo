package br.com.rhitmo.hotel.controller;

import br.com.rhitmo.hotel.controller.dto.ReservationRequestDTO;
import br.com.rhitmo.hotel.controller.dto.ReservationResponseDTO;
import br.com.rhitmo.hotel.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    @Autowired
    private ReservationService service;

    @PostMapping("/room/{roomId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponseDTO> create(@RequestBody @Valid ReservationRequestDTO requestDTO, @PathVariable Long roomId) {
        var response = service.create(requestDTO, roomId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long reservationId) {
        service.cancel(reservationId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{reservationId}/room/{roomId}")
    public ResponseEntity<Void> update(@RequestBody @Valid ReservationRequestDTO requestDTO,
                                       @PathVariable Long reservationId, @PathVariable Long roomId) {

        service.update(requestDTO, reservationId, roomId);
        return ResponseEntity.noContent().build();
    }
}
