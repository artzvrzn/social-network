package com.artzvrzn.dao;

import com.artzvrzn.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//  @Query("select u from User left join Subscription s on u.")
//  List<User> findAllFollowers(Long userId);

  @Query("from User u join fetch Subscription s on u.id = s.subscriber.id where s.subscriber.id = :userId")
  List<User> findAllFollowings(@Param("userId") Long userId);
}
