package ru.shurupov.cards.recognition;

import static ru.shurupov.cards.recognition.utils.ResourceUtils.readImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    String path = args[0];
    BufferedImage image = ImageIO.read(new File(path));
    System.out.println("image " + image);
    String situation = recognitionService.recognize(image);
    System.out.println(path + " " + situation);
  }
}
