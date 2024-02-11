package ru.shurupov.cards.recognition;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CardsAreaBounds {
  int left;
  int right;
  int top;
  int bottom;
  int clearAreaCardY;
  int interval;
}
