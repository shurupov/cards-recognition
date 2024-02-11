package ru.shurupov.cards.recognition.service;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shurupov.cards.recognition.utils.ResourceUtils.getResourceFiles;
import static ru.shurupov.cards.recognition.utils.ResourceUtils.readImage;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shurupov.cards.recognition.config.CardPartExtractConfig;
import ru.shurupov.cards.recognition.config.CardsAreaConfig;
import ru.shurupov.cards.recognition.config.ComparatorConfig;

class CardsRecognitionServiceTest {

  private CardsRecognitionService recognitionService;

  @BeforeEach
  public void init() {

    CardPartExtractConfig cardPartExtractConfig = new CardPartExtractConfig();
    CardPartExtractService partExtractService = new CardPartExtractService(cardPartExtractConfig);
    CardsAreaConfig cardsAreaConfig = new CardsAreaConfig();
    AreaCardExtractService areaCardExtractService = new AreaCardExtractService(cardsAreaConfig);
    ImageConverter imageConverter = new ImageConverter();
    ComparatorConfig config = new ComparatorConfig();
    ImageRecognitionService recognitionService = new ImageRecognitionService(
        config, imageConverter);
    this.recognitionService = new CardsRecognitionService(areaCardExtractService, partExtractService,
        recognitionService);
  }

  @ParameterizedTest
  @MethodSource("getSituations")
  void recognize(String situation) {

    BufferedImage image = readImage("areas/" + situation + ".png");

    String recognized = recognitionService.recognize(image);

    assertThat(recognized).isEqualTo(situation);
  }

  private static Stream<Arguments> getSituations() {
    List<String> sampleFiles = getResourceFiles("areas");
    List<String> names = sampleFiles.stream()
        .map(n -> n.substring(0, n.length() - 4)).toList();

    return names.stream()
        .map(Arguments::of);
  }
}