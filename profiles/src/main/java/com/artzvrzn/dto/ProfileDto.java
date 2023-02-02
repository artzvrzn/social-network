package com.artzvrzn.dto;

import com.artzvrzn.config.jackson.serialize.LocalDateTimeDeserializer;
import com.artzvrzn.config.jackson.serialize.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDto {
  @JsonProperty(access = Access.READ_ONLY)
  private Long id;

  @JsonProperty(access = Access.READ_ONLY)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createdAt;

  @JsonProperty(access = Access.READ_ONLY)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime modifiedAt;
  private String username;
  private String name;
  private String middleName;
  private String familyName;
  private LocalDate birthDate;
  private String email;
  private String imageSmall;
  private String imageLarge;
  private LocationDto location;

  @JsonProperty(access = Access.READ_ONLY)
  private Boolean isDeleted;

  @JsonProperty(access = Access.READ_ONLY)
  @JsonInclude(value = Include.NON_NULL)
  private Boolean isSubscriber;

  @JsonProperty(access = Access.READ_ONLY)
  @JsonInclude(value = Include.NON_NULL)
  private Boolean isSubscribed;

  @JsonProperty(access = Access.READ_ONLY)
  @JsonInclude(value = Include.NON_NULL)
  private Integer subscribers;

  @JsonProperty(access = Access.READ_ONLY)
  @JsonInclude(value = Include.NON_NULL)
  private Integer subscriptions;
}
