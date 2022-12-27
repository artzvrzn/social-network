package com.artzvrzn.dao;

import com.artzvrzn.domain.Subscription;
import com.artzvrzn.domain.Subscription.SubscriptionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {

  @EntityGraph(attributePaths = "subscriber")
  @Query("from Subscription s where s.targetUser.id = :userId")
  Page<Subscription> getAllSubscribers(@Param("userId") Long userId, Pageable pageable);

  @EntityGraph(attributePaths = "targetUser")
  @Query("from Subscription s where s.subscriber.id = :userId")
  Page<Subscription> getAllSubscriptions(@Param("userId") Long userId, Pageable pageable);
}
