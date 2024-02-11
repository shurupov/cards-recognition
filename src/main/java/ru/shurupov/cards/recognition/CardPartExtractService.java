package ru.shurupov.cards.recognition;

import java.awt.image.BufferedImage;

public class CardPartExtractService {

  private static final int SKIP_TRIANGLE_SIZE = 5;

  private static final int VALUE_START_LINE = 0;
  private static final int VALUE_START_COLUMN = 0;
  private static final int VALUE_END_LINE = 28;
  private static final int VALUE_END_COLUMN = 32;
  private static final int SUIT_START_LINE = 43;
  private static final int SUIT_START_COLUMN = 22;

  public BufferedImage extractValue(BufferedImage card) {
    return extractImagePart(card, VALUE_START_LINE, VALUE_START_COLUMN, VALUE_END_LINE, VALUE_END_COLUMN, false);
  }

  public BufferedImage extractSuit(BufferedImage card) {
    return extractImagePart(card, SUIT_START_LINE, SUIT_START_COLUMN ,true);
  }

  public BufferedImage extractImagePart(BufferedImage card, int startY, int startX, boolean checkTriangle) {
    return extractImagePart(card, startY, startX, card.getHeight(), card.getWidth(), checkTriangle);
  }

  public BufferedImage extractImagePart(BufferedImage card, int startY, int startX, int endY, int endX, boolean checkTriangle) {
    int background = card.getRGB(0, 0);
    boolean started = false;
    int top = -1;
    int bottom = -1;
    int left = -1;
    int right = -1;
    for (int y = startY; y < endY; y++) {
      int lineImageStart = -1;
      int lineImageEnd = -1;
      for (int x = startX; x < endX; x++) {
        if (checkTriangle && matchedToLeftTopTriangle(x, y, startX, startY)) {
          continue;
        }
        if (background != card.getRGB(x, y)) {
          if (lineImageStart == -1) {
            lineImageStart = x;
          }
          lineImageEnd = x;
        }
      }
      if (lineImageStart > -1) {
        started = true;
        if (top == -1) {
          top = y;
        }
        bottom = y;
        if (left == -1 || left > lineImageStart) {
          left = lineImageStart;
        }
        if (right == -1 || right < lineImageEnd) {
          right = lineImageEnd;
        }
      } else {
        if (started) {
          break;
        }
      }
    }

    return card.getSubimage(left, top, right - left + 1, bottom - top + 1);
  }

  private boolean matchedToLeftTopTriangle(int x, int y, int startX, int startY) {
    int value = (x + y) - (startX + startY);
    return value < SKIP_TRIANGLE_SIZE;
  }
}
