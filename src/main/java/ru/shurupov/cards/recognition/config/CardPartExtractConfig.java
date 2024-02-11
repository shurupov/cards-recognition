package ru.shurupov.cards.recognition.config;

import lombok.Value;

@Value
public class CardPartExtractConfig {
  int skipTriangleSize = 5;

  int valueStartLine = 0;
  int valueStartColumn = 0;
  int valueEndLine = 28;
  int valueEndColumn = 32;
  int suitStartLine = 43;
  int suitStartColumn = 22;
}
