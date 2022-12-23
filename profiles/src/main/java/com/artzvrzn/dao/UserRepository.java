package com.artzvrzn.dao;

import com.artzvrzn.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("from User u join fetch u.subscriptions s where s.targetUser.id = :userId")
  List<User> findAllSubscribers(@Param("userId") Long userId);

  @Query("from User u join fetch u.subscribers s where s.subscriber.id = :userId")
  List<User> findAllSubscriptions(@Param("userId") Long userId);
}
