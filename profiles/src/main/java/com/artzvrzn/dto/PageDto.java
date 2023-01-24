package com.artzvrzn.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {
  private List<T> content;
  private int number;
  private int totalPages;
  private int totalElements;
  private boolean first;
  private boolean last;

  public void setNumber(int number) {
    this.number = number + 1;
  }
}
