package br.com.rhitmo.hotel.controller.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomResponseDTO {

        private Long id;
        private String description;
        private BigDecimal dailyRateValue;
        private Long status;
        private List<ReservationResponseDTO>reservations;
}
