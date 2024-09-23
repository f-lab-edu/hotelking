package com.hotelking.admin.service;

public record Paging<T>(T list, boolean isEnd, long totalCount) {

  public static <T> Paging<T> from(T list, boolean isEnd, long totalCount) {
    return new Paging<>(list, isEnd, totalCount);
  }
}
