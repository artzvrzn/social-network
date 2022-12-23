package com.artzvrzn.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user", schema = "app")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @CreatedDate
  @Column(columnDefinition = "timestamp(3)")
  private LocalDateTime createdAt;
  @Version
  @LastModifiedDate
  @Column(columnDefinition = "timestamp(3)")
  private LocalDateTime modifiedAt;
  private String username;
  private String name;
  private String middleName;
  private String familyName;
  private String fullName;
  private LocalDate birthDate;
  private String email;
  @Embedded
  private Location location;
  @OneToMany(mappedBy = "targetUser", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Subscription> subscribers = new ArrayList<>();
  @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Subscription> subscriptions = new ArrayList<>();
}
