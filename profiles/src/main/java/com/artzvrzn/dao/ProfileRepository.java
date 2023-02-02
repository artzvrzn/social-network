package com.artzvrzn.dao;

import com.artzvrzn.domain.Profile;
import com.artzvrzn.model.projection.ProfileView;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
  Optional<Profile> findProfileByOwner(String owner);

  @Query(value = """
    select
       p.id as id,
       p.createdAt as createdAt,
       p.modifiedAt as modifiedAt,
       p.username as username,
       p.name as name,
       p.middleName as middleName,
       p.familyName as familyName,
       p.birthDate as birthDate,
       p.email as email,
       p.imageSmall as imageSmall,
       p.imageLarge as imageLarge,
       p.location as location,
       p.isDeleted as isDeleted,
      case when p.id in (
        select s.subscriber.id
        from Subscription s
        where s.target.owner = :owner
        ) then true else false
      end as isSubscriber,
      case when p.id in (
        select s.target.id
        from Subscription s
        where s.subscriber.owner = :owner
      ) then true else false
      end as isSubscribed
    from Profile as p
    """,
    countQuery = "select count(p) from Profile p"
  )
  Page<ProfileView> findAllWithSubscriptionStatus(
    @Param("owner") String owner, Pageable pageable);

  @Query(value = """
    select
       p.id as id,
       p.createdAt as createdAt,
       p.modifiedAt as modifiedAt,
       p.username as username,
       p.name as name,
       p.middleName as middleName,
       p.familyName as familyName,
       p.birthDate as birthDate,
       p.email as email,
       p.imageSmall as imageSmall,
       p.imageLarge as imageLarge,
       p.location as location,
       p.isDeleted as isDeleted,
      case when p.id in (
        select s.subscriber.id
        from Subscription s
        where s.target.owner = :owner
        ) then true else false
      end as isSubscriber,
      case when p.id in (
        select s.target.id
        from Subscription s
        where s.subscriber.owner = :owner
      ) then true else false
      end as isSubscribed,
      (select count(s.subscriber) from Subscription s where s.target.id = p.id) as subscribers,
      (select count(s.target) from Subscription s where s.subscriber.id = p.id) as subscriptions
    from Profile as p
    """,
    countQuery = "select count(p) from Profile p"
  )
  Page<ProfileView> findAllWithSubscriptionStatusAndQuantity(
    @Param("owner") String owner, Pageable pageable);

  @Query("""
    select
      p.id as id,
      p.createdAt as createdAt,
      p.modifiedAt as modifiedAt,
      p.username as username,
      p.name as name,
      p.middleName as middleName,
      p.familyName as familyName,
      p.birthDate as birthDate,
      p.email as email,
      p.imageSmall as imageSmall,
      p.imageLarge as imageLarge,
      p.location as location,
      p.isDeleted as isDeleted,
      case when p.id in (
        select s.subscriber.id
        from Subscription s
        where s.target.owner = :owner
        ) then true else false
      end as isSubscriber,
      case when p.id in (
        select s.target.id
        from Subscription s
        where s.subscriber.owner = :owner
        ) then true else false
      end as isSubscribed,
      (select count(s.subscriber) from Subscription s where s.target.id = p.id) as subscribers,
      (select count(s.target) from Subscription s where s.subscriber.id = p.id) as subscriptions
    from Profile as p
    where p.id = :id
   """)
  Optional<ProfileView> findByIdWithSubscriptionStatusAndQuantity(
    @Param("id") Long id, @Param("owner") String owner);

  @Query("""
    select
       p.id as id,
       p.createdAt as createdAt,
       p.modifiedAt as modifiedAt,
       p.username as username,
       p.name as name,
       p.middleName as middleName,
       p.familyName as familyName,
       p.birthDate as birthDate,
       p.email as email,
       p.imageSmall as imageSmall,
       p.imageLarge as imageLarge,
       p.location as location,
       p.isDeleted as isDeleted,
      case when p.id in (
        select s.subscriber.id
        from Subscription s
        where s.target.owner = :owner
        ) then true else false
      end as isSubscriber,
      case when p.id in (
        select s.target.id
        from Subscription s
        where s.subscriber.owner = :owner
      ) then true else false
      end as isSubscribed
    from Profile as p
    where p.id = :id
    """
  )
  Optional<ProfileView> findByIdWithSubscriptionStatus(
    @Param("id") Long id, @Param("owner") String owner);

  @Query("""
    select
      p.id as id,
      p.createdAt as createdAt,
      p.modifiedAt as modifiedAt,
      p.username as username,
      p.name as name,
      p.middleName as middleName,
      p.familyName as familyName,
      p.birthDate as birthDate,
      p.email as email,
      p.imageSmall as imageSmall,
      p.imageLarge as imageLarge,
      p.location as location,
      p.isDeleted as isDeleted,
      (select count(s.subscriber) from Subscription s where s.target.id = p.id) as subscribers,
      (select count(s.target) from Subscription s where s.subscriber.id = p.id) as subscriptions
    from Profile as p
    where p.owner = :owner
   """)
  Optional<ProfileView> findByOwnerWithSubscriptionQuantity(@Param("owner") String owner);
}
//   p.id as id,
//   p.createdAt as createdAt,
//   p.modifiedAt as modifiedAt,
//   p.username as username,
//   p.name as name,
//   p.middleName as middleName,
//   p.familyName as familyName,
//   p.birthDate as birthDate,
//   p.email as email,
//   p.imageSmall as imageSmall,
//   p.imageLarge as imageLarge,
//   p.location as location,
//   p.isDeleted as isDeleted,
//  p.createdAt as createdAt,
//  p.modifiedAt as modifiedAt,
//  p.owner as owner,
//  p.username as username,
//  p.name as name,
//  p.middleName as middleName,
//  p.familyName as familyName,
//  p.birthDate as birthDate,
//  p.email as email,
//  p.imageSmall as imageSmall,
//  p.imageLarge as imageLarge,
//  p.isDeleted as isDeleted,