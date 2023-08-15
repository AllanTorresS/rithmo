package br.com.rhitmo.hotel.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationRequestDTO {

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "checkinDate must not be null")
    private LocalDate checkinDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "checkoutDate must not be null")
    private LocalDate checkoutDate;
}
