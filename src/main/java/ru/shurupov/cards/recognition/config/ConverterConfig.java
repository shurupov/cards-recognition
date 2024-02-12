package ru.shurupov.cards.recognition.config;

import lombok.Value;

@Value
public class ConverterConfig {
  int minColorComponentDifference = 5;
}
