package ru.shurupov.cards.recognition.service;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.shurupov.cards.recognition.utils.ResourceUtils.readImage;

import java.awt.image.BufferedImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shurupov.cards.recognition.config.ComparatorConfig;

class ImageRecognitionServiceTest {

  private ImageRecognitionService recognitionService;

  @BeforeEach
  public void init() {
    ComparatorConfig config = new ComparatorConfig();
    ImageConverter imageConverter = new ImageConverter();
    recognitionService = new ImageRecognitionService(config, imageConverter);
  }

  @ParameterizedTest
  @ValueSource(strings = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"})
  public void findValueName(String value) {
    BufferedImage valueImage = readImage("values/" + value + ".png");
    String foundValueName = recognitionService.findValueName(valueImage);
    assertThat(foundValueName).isEqualTo(value);
  }

  @ParameterizedTest
  @ValueSource(strings = {"c", "d", "h", "s"})
  public void findSuitName(String suit) {
    BufferedImage suitImage = readImage("suits/" + suit + ".png");
    String foundSuitName = recognitionService.findSuitName(suitImage);
    assertThat(foundSuitName).isEqualTo(suit);
  }
}