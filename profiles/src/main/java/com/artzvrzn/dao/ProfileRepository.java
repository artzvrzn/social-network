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
  @Query("""
  select p.id as id,
         count(f.target.id) as countSubscribers,
         count(s.subscriber.id) as countSubscriptions
  from Profile as p
  left join Subscription as f on p.id = f.target.id
  left join Subscription as s on p.id = s.subscriber.id
  where p.id = :id
  group by p.id
  """)
  ProfileView findProfileWithCounts(@Param("id") Long id);

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
        where s.target.owner = :subject
        ) then true else false
      end as isSubscriber,
      case when p.id in (
        select s.target.id
        from Subscription s
        where s.subscriber.owner = :subject
      ) then true else false
      end as isSubscribed
    from Profile as p
    """,
    countQuery = "select count(p) from Profile p"
  )
  Page<ProfileView> findAllProfilesWithSubscriptionStatus(
    @Param("subject") String subject, Pageable pageable);
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