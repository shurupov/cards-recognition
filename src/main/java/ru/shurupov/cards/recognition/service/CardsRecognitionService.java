package ru.shurupov.cards.recognition.service;

import java.awt.image.BufferedImage;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardsRecognitionService {

  private final AreaCardExtractService areaCardExtractService;
  private final CardPartExtractService cardPartExtractService;
  private final ImageRecognitionService recognitionService;

  public String recognize(BufferedImage image) {

    List<BufferedImage> cards = areaCardExtractService.extractCards(image);

    StringBuilder sb = new StringBuilder();

    for (BufferedImage card : cards) {
      BufferedImage cardValueImage = cardPartExtractService.extractValue(card);
      String value = recognitionService.findValueName(cardValueImage);
      sb.append(value);

      BufferedImage cardSuitImage = cardPartExtractService.extractSuit(card);
      String suit = recognitionService.findSuitName(cardSuitImage);
      sb.append(suit);
    }

    return sb.toString();
  }
}
