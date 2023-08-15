package br.com.rhitmo.hotel.controller;

import br.com.rhitmo.hotel.mock.RoomMock;
import br.com.rhitmo.hotel.service.RoomService;
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

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomService roomService;

    private final String BASE_URL = "/v1/room";

    @Test
    @DisplayName("list all rooms but the result is empty")
    public void when_find_all_rooms_and_result_is_empty_must_return_204() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("list all rooms but the result is not empty")
    public void when_find_all_rooms_and_result_is_not_empty_must_return_200() throws Exception
    {
        var response = RoomMock.createRoomResponseDTOValid();
        when(roomService.findAll()).thenReturn(List.of(response));

        mvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(1));
    }


    @Test
    @DisplayName("save valid room with success")
    public void when_save_valid_room_must_return_201() throws Exception
    {
        var response = RoomMock.createRoomResponseDTOValid();

        when(roomService.create(any())).thenReturn((response));

        mvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"description\",\"dailyRateValue\": \"1\", \"status\":\"1\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }


    @Test
    @DisplayName("throw error 400 when try save invalid room")
    public void when_try_save_invalid_room_must_return_400() throws Exception
    {
        var response = RoomMock.createRoomResponseDTOValid();
        var errorMessage ="dailyRateValue must be greather than zero";

        when(roomService.create(any())).thenReturn((response));

        mvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"description\",\"dailyRateValue\": \"0\", \"status\":\"1\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[*]").value(errorMessage));
    }
}
