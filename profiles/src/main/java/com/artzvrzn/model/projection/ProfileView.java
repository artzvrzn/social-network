package com.artzvrzn.model.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ProfileView {

  Long getId();

  LocalDateTime getCreatedAt();

  LocalDateTime getModifiedAt();

  String getUsername();

  String getName();

  String getMiddleName();

  String getFamilyName();

  LocalDate getBirthDate();

  String getEmail();

  String getImageSmall();

  String getImageLarge();

  LocationView getLocation();

  Boolean getIsDeleted();

  Integer getSubscribers();

  Integer getSubscriptions();

  Boolean getIsSubscriber();

  Boolean getIsSubscribed();
}
