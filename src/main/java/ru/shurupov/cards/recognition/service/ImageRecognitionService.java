package ru.shurupov.cards.recognition.service;

import static ru.shurupov.cards.recognition.utils.ResourceUtils.getResourceFiles;
import static ru.shurupov.cards.recognition.utils.ResourceUtils.readImage;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.shurupov.cards.recognition.config.ComparatorConfig;

public class ImageRecognitionService {

  private final Map<String, boolean[][]> valueSamples;
  private final Map<String, boolean[][]> suitSamples;

  private final ComparatorConfig config;
  private final ImageConverter imageConverter;

  public ImageRecognitionService(ComparatorConfig config, ImageConverter imageConverter) {
    this.config = config;
    this.imageConverter = imageConverter;
    suitSamples = initSamples("suits");
    valueSamples = initSamples("values");
  }

  private Map<String, boolean[][]> initSamples(String folderName) {
    List<String> sampleFiles = getResourceFiles(folderName);
    List<String> names = sampleFiles.stream()
        .map(n -> n.substring(0, n.length() - 4)).toList();

    Map<String, boolean[][]> samples = new HashMap<>();

    for (int i = 0; i < sampleFiles.size(); i++) {
      BufferedImage image = readImage(folderName + "/" + sampleFiles.get(i));
      boolean[][] snapshot = imageConverter.convert(image);
      samples.put(names.get(i), snapshot);
    }
    return Map.copyOf(samples);
  }

  private double compare(boolean[][] snapshot1, boolean[][] snapshot2) {
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

  public String findSuitName(BufferedImage suit) {
    return findName(suit, suitSamples);
  }

  public String findValueName(BufferedImage suit) {
    return findName(suit, valueSamples);
  }

  public String findName(BufferedImage image, Map<String, boolean[][]> snapshots) {
    for (Map.Entry<String, boolean[][]> snapshot : snapshots.entrySet()) {
      double equivalence = compare(snapshot.getValue(), imageConverter.convert(image));
      if (equivalence > config.getEquivalence()) {
        return snapshot.getKey();
      }
    }
    return null;
  }
}
