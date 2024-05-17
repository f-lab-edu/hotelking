package com.hotelking.controller;

import com.hotelking.application.OrderService;
import com.hotelking.dto.ActiveUser;
import com.hotelking.dto.request.AddOrderRequest;
import com.hotelking.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/order")
  public ApiResponse<?> addOrder(@RequestBody @Valid AddOrderRequest addOrderRequest) {
    addOrderRequest.validationCheck();
    // TODO ActiveUser 인증객체로 부터 가져오기
    ActiveUser activeUser = new ActiveUser(1L);
    orderService.addOrder(activeUser, addOrderRequest.toOrderDto());
    return ApiResponse.success();
  }
}
