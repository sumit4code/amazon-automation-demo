package io.automation.amazon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class Product {

  private final String title;
  private final String brand;
  private final String color;
  private final String model;

}
