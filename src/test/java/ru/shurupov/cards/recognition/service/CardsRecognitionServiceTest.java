package ru.shurupov.cards.recognition.service;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shurupov.cards.recognition.utils.ResourceUtils.readImage;

import java.awt.image.BufferedImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
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
  @ValueSource(strings = {"2c7h3c10c", "5cQs6h", "4c9dJs", "6dKdAs", "8d2h7s", "9c5c9d", "AcKc10d6s"})
  void recognize(String situation) {

    BufferedImage image = readImage("areas/" + situation + ".png");

    String recognized = recognitionService.recognize(image);

    assertThat(recognized).isEqualTo(situation);
  }
}