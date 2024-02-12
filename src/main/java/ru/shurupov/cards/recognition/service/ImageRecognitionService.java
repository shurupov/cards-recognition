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
  private final ImageComparatorService comparatorService;

  public ImageRecognitionService(ComparatorConfig config, ImageConverter imageConverter,
      ImageComparatorService comparatorService) {
    this.config = config;
    this.imageConverter = imageConverter;
    this.comparatorService = comparatorService;
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
//      System.out.println(folderName + " " + i + " " + image);
      boolean[][] snapshot = imageConverter.toOutlineSnapshot(image);
      samples.put(names.get(i), snapshot);
    }
    return Map.copyOf(samples);
  }

  public String findSuitName(BufferedImage suit) {
    return findName(suit, suitSamples);
  }

  public String findValueName(BufferedImage suit) {
    return findName(suit, valueSamples);
  }

  public String findName(BufferedImage image, Map<String, boolean[][]> snapshots) {
    double acceptableEquivalence = config.getEquivalence();
    String result;
    do {
      result = findName(image, snapshots, acceptableEquivalence);
      acceptableEquivalence -= config.getEquivalenceStep();
    } while (acceptableEquivalence > 0 && result == null);

    return result;
  }

  private String findName(BufferedImage image, Map<String, boolean[][]> snapshots, double acceptableEquivalence) {
    for (Map.Entry<String, boolean[][]> snapshot : snapshots.entrySet()) {
      double equivalence = comparatorService.compare(snapshot.getValue(), imageConverter.toOutlineSnapshot(image));
      if (equivalence > acceptableEquivalence) {
        return snapshot.getKey();
      }
    }
    return null;
  }
}
