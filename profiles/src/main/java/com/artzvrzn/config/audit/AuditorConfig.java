package com.artzvrzn.config.audit;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class AuditorConfig {

  @Bean
  DateTimeProvider dateTimeProvider() {
    return new LocalDateTimeProvider();
  }
}
