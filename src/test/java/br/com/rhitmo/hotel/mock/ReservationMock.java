package br.com.rhitmo.hotel.mock;

import br.com.rhitmo.hotel.controller.dto.ReservationRequestDTO;
import br.com.rhitmo.hotel.controller.dto.ReservationResponseDTO;
import br.com.rhitmo.hotel.controller.dto.RoomRequestDTO;
import br.com.rhitmo.hotel.controller.dto.RoomResponseDTO;
import br.com.rhitmo.hotel.model.ReservationStatus;
import br.com.rhitmo.hotel.model.RoomStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReservationMock {

    public static ReservationResponseDTO createReservationResponseDTOValid() {

        return ReservationResponseDTO.builder()
                .id(1L)
                .checkinDate(LocalDate.of(2023,2,22))
                .checkoutDate(LocalDate.of(2023,2,28))
                .status(ReservationStatus.ACTIVE.getValue())
                .grossAmount(BigDecimal.TEN)
                .build();
    }

    public static ReservationRequestDTO createReservationRequestDTOValid() {

        return ReservationRequestDTO.builder()
                .checkinDate(LocalDate.of(2023,2,22))
                .checkoutDate(LocalDate.of(2023,2,28))
                .build();
    }

    public static ReservationRequestDTO createReservationRequestDTOInvalid() {

        return ReservationRequestDTO.builder()
                .checkinDate(LocalDate.of(2023,2,22))
                .build();
    }

}
