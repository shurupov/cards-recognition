package ru.shurupov.cards.recognition.config;

import lombok.Value;

@Value
public class ComparatorConfig {
  double equivalence = 0.95;
  double equivalenceStep = 0.01;
}
