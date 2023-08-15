package br.com.rhitmo.hotel.service;

import br.com.rhitmo.hotel.controller.dto.ReservationRequestDTO;
import br.com.rhitmo.hotel.controller.dto.ReservationResponseDTO;

public interface ReservationService {

    public ReservationResponseDTO create(ReservationRequestDTO requestDTO, Long roomId);

    public void cancel(Long roomId);

    public void update(ReservationRequestDTO requestDTO, Long reservationId, Long roomId);
}
