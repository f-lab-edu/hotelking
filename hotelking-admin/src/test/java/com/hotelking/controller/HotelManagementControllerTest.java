package com.hotelking.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hotelking.dto.AddHotelDto;
import com.hotelking.service.AddHotelService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HotelManagementController.class)
class HotelManagementControllerTest {

  @MockBean
  private AddHotelService addHotelService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("호텔 등록 컨트롤러 테스트 - 성공")
  void registerHotel() throws Exception {
    AddHotelDto addHotelDto = new AddHotelDto(
        "hotelKing",
        "hotelKingAddress",
        37.123211,
        127.123123
    );
    when(addHotelService.registerHotel(addHotelDto)).thenReturn(100L);

    this.mockMvc.perform(
            post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "hotelName": "hotelKing",
                      "hotelAddress": "hotelKingAddress",
                      "hotelLat": 37.123211,
                      "hotelLng": 127.123123
                    }
                    """)
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(header().stringValues("Location", "/hotels/100"));
  }
}
