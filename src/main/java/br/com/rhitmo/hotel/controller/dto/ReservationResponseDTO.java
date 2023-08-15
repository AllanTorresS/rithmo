package br.com.rhitmo.hotel.controller.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationResponseDTO {

    private Long id;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private BigDecimal grossAmount;
    private Long status;
}
