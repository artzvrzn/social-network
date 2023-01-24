package com.artzvrzn.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "subscription", schema = "app")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
  @EmbeddedId
  private SubscriptionId id;
  @CreatedDate
  private LocalDateTime createdAt;
  @ManyToOne
  @MapsId("targetUserId")
  private User targetUser;
  @ManyToOne
  @MapsId("subscriberId")
  private User subscriber;

  @Embeddable
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  public static class SubscriptionId implements Serializable {
    private Long targetUserId;
    private Long subscriberId;
  }
}
