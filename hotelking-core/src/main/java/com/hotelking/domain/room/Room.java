package com.hotelking.domain.room;

import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.schedule.RoomSchedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "ROOM")
@Getter
public class Room extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "no", nullable = false)
  private int no;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "room_type_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_room_to_room_type")
  )
  private RoomType type;

  @BatchSize(size = 100)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
  private List<RoomSchedule> roomSchedules;
}
