package ru.shurupov.cards.recognition;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.ClassLoaderUtils;

class CardsRecognitionServiceTest {

  private CardsRecognitionService recognitionService;

  @BeforeEach
  public void init() {

    CardsAreaBounds bounds = CardsAreaBounds.builder()
        .top(590)
        .bottom(689)
        .left(145)
        .right(509)
        .clearAreaCardY(590)
        .interval(12)
        .build();

    CardSize cardSize = CardSize.builder()
        .width(60)
        .height(80)
        .build();

    recognitionService = new CardsRecognitionService(new CardPartExtractService(), bounds, cardSize);
  }

  @ParameterizedTest
  @ValueSource(strings = {"2c7h3c10c", "5cQs6h", "4c9dJs", "6dKdAs", "8d2h7s", "9c5c9d", "AcKc10d6s"})
  void recognize(String situation) throws IOException {

    InputStream imageStream = ClassLoaderUtils.getDefaultClassLoader().getResourceAsStream("areas/" + situation + ".png");

    BufferedImage image = ImageIO.read(imageStream);

    String recognized = recognitionService.recognize(image);

    assertThat(recognized).isEqualTo("QcJd5h");
  }
}