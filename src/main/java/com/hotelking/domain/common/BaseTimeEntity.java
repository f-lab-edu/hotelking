package com.hotelking.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

  @CreatedDate
  @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP(0)")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", columnDefinition = "TIMESTAMP(0)")
  private LocalDateTime updatedAt;

}