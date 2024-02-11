package ru.shurupov.cards.recognition.service;

import java.awt.image.BufferedImage;

public class ImageConverter {

  public boolean[][] convert(BufferedImage image) {
    int background = image.getRGB(0, 0);
    int height = image.getHeight();
    int width = image.getWidth();

    boolean[][] result = new boolean[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        result[y][x] = background != image.getRGB(x, y);
      }
    }

    return result;
  }
}
