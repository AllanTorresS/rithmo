package br.com.rhitmo.hotel.controller;

import br.com.rhitmo.hotel.mock.ReservationMock;
import br.com.rhitmo.hotel.mock.RoomMock;
import br.com.rhitmo.hotel.service.ReservationService;
import br.com.rhitmo.hotel.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService service;

    private final String BASE_URL = "/v1/reservation";

    @Test
    @DisplayName("save valid reservation with success")
    public void when_save_valid_reservation_must_return_201() throws Exception {

        var response = ReservationMock.createReservationResponseDTOValid();
        var request = ReservationMock.createReservationRequestDTOValid();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(service.create(any(),any())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL+"/room/{roomId}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("throw error on try save invalid reservation request")
    public void when_try_save_invalid_reservation_must_return_400() throws Exception {

        var errorMessage = "checkoutDate must not be null";
        var response = ReservationMock.createReservationResponseDTOValid();
        var request = ReservationMock.createReservationRequestDTOInvalid();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(service.create(any(),any())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL+"/room/{roomId}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[*]").value(errorMessage));
    }

    @Test
    @DisplayName("cancel reservation with success")
    public void when_cancel_reservation_with_success_must_return_204() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .patch(BASE_URL+"/{reservationId}/cancel",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
