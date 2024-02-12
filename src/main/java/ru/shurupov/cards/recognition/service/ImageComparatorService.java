package ru.shurupov.cards.recognition.service;

public class ImageComparatorService {

  public double compare(boolean[][] snapshot1, boolean[][] snapshot2) {
    int height = Math.min(snapshot1.length, snapshot2.length);
    int width = Math.min(snapshot1[0].length, snapshot2[0].length);

    int all = 0;
    int equal = 0;

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        all++;
        if (snapshot1[y][x] == snapshot2[y][x]) {
          equal++;
        }
      }
    }

    return (double) equal / all;
  }
}
