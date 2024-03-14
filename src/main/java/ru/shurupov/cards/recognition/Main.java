package ru.shurupov.cards.recognition;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import ru.shurupov.cards.recognition.config.CardPartExtractConfig;
import ru.shurupov.cards.recognition.config.CardsAreaConfig;
import ru.shurupov.cards.recognition.config.ComparatorConfig;
import ru.shurupov.cards.recognition.config.ConverterConfig;
import ru.shurupov.cards.recognition.service.AreaCardExtractService;
import ru.shurupov.cards.recognition.service.CardPartExtractService;
import ru.shurupov.cards.recognition.service.CardsRecognitionService;
import ru.shurupov.cards.recognition.service.ImageComparatorService;
import ru.shurupov.cards.recognition.service.ImageConverter;
import ru.shurupov.cards.recognition.service.ImageRecognitionService;

public class Main {

  public static void main(String[] args) throws IOException {
    CardPartExtractConfig cardPartExtractConfig = new CardPartExtractConfig();
    CardPartExtractService partExtractService = new CardPartExtractService(cardPartExtractConfig);
    CardsAreaConfig cardsAreaConfig = new CardsAreaConfig();
    AreaCardExtractService areaCardExtractService = new AreaCardExtractService(cardsAreaConfig);
    ImageConverter imageConverter = new ImageConverter(new ConverterConfig());
    ImageComparatorService comparatorService = new ImageComparatorService();
    ComparatorConfig config = new ComparatorConfig();
    ImageRecognitionService imageRecognitionService = new ImageRecognitionService(
        config, imageConverter, comparatorService);
    CardsRecognitionService recognitionService = new CardsRecognitionService(areaCardExtractService, partExtractService,
        imageRecognitionService);


    for (File file : Objects.requireNonNull(new File(args[0]).listFiles())) {
      BufferedImage image = ImageIO.read(file);
      String situation = recognitionService.recognize(image);
      System.out.println(file.getName() + " " + situation + " " + ( file.getName().equals(situation+".png") ? "\033[92mOK" : "\033[91mFAIL" ) + "\033[0m");
    }
  }
}
