package ru.shurupov.cards.recognition.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.shurupov.cards.recognition.service.TestUtils.bufferedImagesEqual;
import static ru.shurupov.cards.recognition.utils.ResourceUtils.readImage;

import java.awt.image.BufferedImage;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.shurupov.cards.recognition.config.CardPartExtractConfig;

class CardPartExtractServiceTest {

  private CardPartExtractService partExtractService;

  @BeforeEach
  public void init() {

    CardPartExtractConfig cardPartExtractConfig = new CardPartExtractConfig();
    partExtractService = new CardPartExtractService(cardPartExtractConfig);
  }

  @ParameterizedTest
  @MethodSource("getCardsAndValues")
  void extractValue(String card, String value) {
    BufferedImage cardImage = readImage("cards/" + card + ".png");
    BufferedImage correctValueImage = readImage("values/" + value + ".png");

    BufferedImage valueImage = partExtractService.extractValue(cardImage);

    assertTrue(bufferedImagesEqual(valueImage, correctValueImage));
  }

  @ParameterizedTest
  @MethodSource("getCardsAndSuits")
  void extractSuit(String card, String suit) {
    BufferedImage cardImage = readImage("cards/" + card + ".png");
    BufferedImage correctSuitImage = readImage("suits/" + suit + ".png");

    BufferedImage suitImage = partExtractService.extractSuit(cardImage);

    assertTrue(bufferedImagesEqual(suitImage, correctSuitImage));
  }

  private static Stream<Arguments> getCardsAndValues() {
    return Stream.of(
        Arguments.of("5c", "5"),
        Arguments.of("Qs", "Q"),
        Arguments.of("6h", "6"),
        Arguments.of("Kd", "K"),
        Arguments.of("As", "A")
    );
  }

  private static Stream<Arguments> getCardsAndSuits() {
    return Stream.of(
        Arguments.of("5c", "c"),
        Arguments.of("Qs", "s"),
        Arguments.of("6h", "h"),
        Arguments.of("6d", "d")
    );
  }
}