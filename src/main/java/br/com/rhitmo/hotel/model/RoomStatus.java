package br.com.rhitmo.hotel.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RoomStatus {

	AVAILABLE("Disponivel", 1L),
	UNAVAILABLE("Indisponivel", 2L);

	private String description;
	private Long value;

	RoomStatus(String description, Long value) {
		this.description = description;
		this.value = value;
	}

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static RoomStatus fromLong(Long value) {
		return Arrays.stream(RoomStatus.values()).filter(roomStatus -> roomStatus.value.equals(value))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("status is invalid"));
	}
}