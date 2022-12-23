package com.artzvrzn.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "subscription", schema = "app")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
  @EmbeddedId
  private SubscriptionId id;
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
