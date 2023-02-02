package com.artzvrzn.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "profile", schema = "app")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Profile {
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
  @Column(unique = true)
  private String owner;
  private String username;
  private String name;
  private String middleName;
  private String familyName;
  private LocalDate birthDate;
  @Column(unique = true)
  private String email;
  private String imageSmall;
  private String imageLarge;
  private Boolean isDeleted = false;
  @Embedded
  private Location location;
  @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Subscription> subscribers = new ArrayList<>();
  @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Subscription> subscriptions = new ArrayList<>();

  public Profile() {}

  public static class Builder {
    private final Profile instance;

    private Builder() {
      this.instance = new Profile();
    }

    public static Builder builder() {
      return new Builder();
    }

    public Profile build() {
      return instance;
    }

    public Builder owner(String owner) {
      instance.owner = owner;
      return this;
    }

    public Builder username(String username) {
      instance.username = username;
      return this;
    }

    public Builder name(String name) {
      instance.name = name;
      return this;
    }

    public Builder middleName(String middleName) {
      instance.middleName = middleName;
      return this;
    }

    public Builder familyName(String familyName) {
      instance.familyName = familyName;
      return this;
    }

    public Builder birthDate(LocalDate birthDate) {
      instance.birthDate = birthDate;
      return this;
    }

    public Builder email(String email) {
      instance.email = email;
      return this;
    }

    public Builder imageSmall(String imageSmall) {
      instance.imageSmall = imageSmall;
      return this;
    }

    public Builder imageLarge(String imageLarge) {
      instance.imageLarge = imageLarge;
      return this;
    }

    public Builder deleted(Boolean deleted) {
      instance.isDeleted = deleted;
      return this;
    }

    public Builder location(Location location) {
      instance.location = location;
      return this;
    }
  }
}
