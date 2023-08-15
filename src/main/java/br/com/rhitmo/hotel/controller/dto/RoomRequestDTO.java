package br.com.rhitmo.hotel.controller.dto;

import br.com.rhitmo.hotel.model.RoomStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDTO{

        @NotBlank(message = "description must not be null or empty")
        private String description;

        @Min(value = 1L, message = "dailyRateValue must be greather than zero")
        @Digits(integer = 5, fraction = 2,message = "dailyRateValue must be in format xxxxx.xx")
        private BigDecimal dailyRateValue;

        @NotNull(message = "status must not be null")
        private RoomStatus status;
}
