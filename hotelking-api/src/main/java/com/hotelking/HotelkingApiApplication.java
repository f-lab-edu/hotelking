package com.hotelking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HotelkingApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(HotelkingApiApplication.class, args);
  }
}