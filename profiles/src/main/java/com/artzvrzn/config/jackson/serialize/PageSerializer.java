package com.artzvrzn.config.jackson.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

@JsonComponent
public class PageSerializer extends JsonSerializer<Page<?>> {

  @Override
  public void serialize(Page<?> page, JsonGenerator jg, SerializerProvider sp)
    throws IOException {
    jg.writeStartObject();
    jg.writeObjectField("content", page.getContent());
    jg.writeNumberField("number", page.getNumber() + 1);
    jg.writeNumberField("size", page.getSize());
    jg.writeNumberField("totalPages", page.getTotalPages());
    jg.writeNumberField("totalElements", page.getTotalElements());
    jg.writeBooleanField("first", page.isFirst());
    jg.writeBooleanField("last", page.isLast());
    jg.writeEndObject();
  }
}