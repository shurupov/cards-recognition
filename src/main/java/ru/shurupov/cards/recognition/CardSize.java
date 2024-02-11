package ru.shurupov.cards.recognition;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CardSize {
  int width;
  int height;
}
