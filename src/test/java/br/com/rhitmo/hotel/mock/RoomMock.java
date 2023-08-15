package br.com.rhitmo.hotel.mock;

import br.com.rhitmo.hotel.controller.dto.ReservationResponseDTO;
import br.com.rhitmo.hotel.controller.dto.RoomRequestDTO;
import br.com.rhitmo.hotel.controller.dto.RoomResponseDTO;
import br.com.rhitmo.hotel.model.RoomStatus;

import java.math.BigDecimal;
import java.util.List;

public class RoomMock {

    public static RoomResponseDTO createRoomResponseDTOValid() {

        return RoomResponseDTO.builder()
                .id(1L)
                .description("description")
                .dailyRateValue(BigDecimal.ONE)
                .status(RoomStatus.AVAILABLE.getValue())
                .reservations(List.of(ReservationResponseDTO.builder().build()))
                .build();
    }

    public static RoomRequestDTO createRoomRequestDTOValid() {

        return RoomRequestDTO.builder()
                .description("description")
                .dailyRateValue(BigDecimal.ONE)
                .status(RoomStatus.AVAILABLE)
                .build();
    }

}
