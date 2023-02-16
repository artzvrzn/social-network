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
  @Query("from Subscription s where s.target.id = :profileId")
  Page<Subscription> getAllSubscribers(@Param("profileId") Long profileId, Pageable pageable);

  @EntityGraph(attributePaths = "subscriber")
  @Query("from Subscription s where s.target.owner = :owner")
  Page<Subscription> getAllSubscribers(@Param("owner") String owner, Pageable pageable);

  @EntityGraph(attributePaths = "target")
  @Query("from Subscription s where s.subscriber.id = :profileId")
  Page<Subscription> getAllSubscriptions(@Param("profileId") Long profileId, Pageable pageable);

  @EntityGraph(attributePaths = "target")
  @Query("from Subscription s where s.subscriber.owner = :owner")
  Page<Subscription> getAllSubscriptions(@Param("owner") String owner, Pageable pageable);
}
