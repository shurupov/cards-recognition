package ru.shurupov.cards.recognition.service;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;

import java.awt.Color;
import java.awt.image.BufferedImage;
import lombok.RequiredArgsConstructor;
import ru.shurupov.cards.recognition.config.ConverterConfig;

@RequiredArgsConstructor
public class ImageConverter {

  private final ConverterConfig config;

  public boolean[][] convert(BufferedImage image) {
    return convertToOutline(image);
  }

  public boolean[][] convertToDifferenceFromBg(BufferedImage image) {
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

  public boolean[][] convertToOutline(BufferedImage image) {
    int height = image.getHeight();
    int width = image.getWidth();

    boolean[][] result = new boolean[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        result[y][x] = isInOutline(x, y, image);
      }
    }

    return result;
  }

  public BufferedImage outlined(BufferedImage image) {
    return backToImage(convert(image));
  }

  public BufferedImage backToImage(boolean[][] converted) {
    BufferedImage image = new BufferedImage(converted[0].length, converted.length, TYPE_4BYTE_ABGR);
    int height = image.getHeight();
    int width = image.getWidth();

    int black = new Color(0,0,0).getRGB();
    int white = new Color(255,255,255).getRGB();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        image.setRGB(x, y, converted[y][x] ? black : white);
      }
    }

    return image;
  }

  private boolean isInOutline(int x, int y, BufferedImage image) {
    int color = image.getRGB(x, y);
    //Upper
    if (x > 0 && y > 0 && isDifferent(image, x-1, y-1, color)) return true;
    if (         y > 0 && isDifferent(image, x, y-1, color)) return true;
    if (x < (image.getWidth() - 1) && y > 0 && isDifferent(image, x+1, y-1, color)) return true;
    //The same line
    if (x > 0 && isDifferent(image, x-1, y, color)) return true;
    if (x < (image.getWidth() - 1) && isDifferent(image, x+1, y, color)) return true;
    //Downer
    if (x > 0 && y < (image.getHeight() - 1) && isDifferent(image, x-1, y+1, color)) return true;
    if (         y < (image.getHeight() - 1) && isDifferent(image, x, y+1, color)) return true;
    if (x < (image.getWidth() - 1) && y < (image.getHeight() - 1) && isDifferent(image, x+1, y+1, color)) return true;
    return false;
  }
  
  private boolean isDifferent(BufferedImage image, int x, int y, int color) {
    return isDifferent(image.getRGB(x, y), color);
  }

  private boolean isDifferent(int color1, int color2) {
    Color clr1 = new Color(color1);
    Color clr2 = new Color(color2);

    if (Math.abs(clr1.getRed() - clr2.getRed()) > config.getMinColorComponentDifference()) return true;
    if (Math.abs(clr1.getGreen() - clr2.getGreen()) > config.getMinColorComponentDifference()) return true;
    if (Math.abs(clr1.getBlue() - clr2.getBlue()) > config.getMinColorComponentDifference()) return true;

    return false;
  }
}
