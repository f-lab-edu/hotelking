package com.hotelking.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.hotelking.dto.AppUser;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

@DataRedisTest
class OrderRepositoryTest {

  @Autowired
  OrderRepository orderRepository;

  final UUID orderKey = UUID.randomUUID();
  final Order order = new Order(
      orderKey,
      LocalDate.now(),
      LocalDate.now(),
      1L,
      1L,
      100_000,
      new AppUser(1L)
  );

  @BeforeEach
  void initSave() {
    orderRepository.save(order);
  }

  @Test
  void findOrderKey() {
    Order savedOrder = orderRepository.findById(orderKey).get();
    assertThat(savedOrder).isEqualTo(order);
  }

}