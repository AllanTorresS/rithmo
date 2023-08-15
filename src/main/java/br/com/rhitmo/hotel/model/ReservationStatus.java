package br.com.rhitmo.hotel.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ReservationStatus {

	ACTIVE("ATIVA", 1L),
	CANCELED("CANCELADA", 2L);

	private String description;
	private Long value;

	ReservationStatus(String description, Long value) {
		this.description = description;
		this.value = value;
	}

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static ReservationStatus fromLong(Long value) {
		return Arrays.stream(ReservationStatus.values()).filter(roomStatus -> roomStatus.value.equals(value))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("invalid"));
	}
}