package ru.shurupov.cards.recognition.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.shurupov.cards.recognition.service.TestUtils.bufferedImagesEqual;
import static ru.shurupov.cards.recognition.service.TestUtils.readImage;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.shurupov.cards.recognition.config.CardsAreaConfig;

class AreaCardExtractServiceTest {

  private AreaCardExtractService service;

  @BeforeEach
  public void init() {
    service = new AreaCardExtractService(new CardsAreaConfig());
  }

  @ParameterizedTest
  @MethodSource("getAreaAndCards")
  void extractCards(String area, List<String> cards) {
    BufferedImage areaImage = readImage("areas/" + area + ".png");
    List<BufferedImage> correctCardImages = cards.stream()
        .map(c -> readImage("cards/" + c + ".png")).toList();

    List<BufferedImage> cardImages = service.extractCards(areaImage);

    for (int i = 0; i < cardImages.size(); i++) {
      assertTrue(bufferedImagesEqual(cardImages.get(i), correctCardImages.get(i)));
    }
  }

  private static Stream<Arguments> getAreaAndCards() {
    return Stream.of(
        Arguments.of("5cQs6h", List.of("5c", "Qs", "6h")),
        Arguments.of("4c9dJs", List.of("4c", "9d", "Js")),
        Arguments.of("6dKdAs", List.of("6d", "Kd", "As"))
    );
  }
}