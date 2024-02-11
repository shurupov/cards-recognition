package ru.shurupov.cards.recognition.service;

import java.awt.image.BufferedImage;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardsRecognitionService {


  private final AreaCardExtractService areaCardExtractService;
  private final CardPartExtractService cardPartExtractService;

  public String recognize(BufferedImage image) {

    List<BufferedImage> cards = areaCardExtractService.extractCards(image);

    for (BufferedImage card : cards) {
      BufferedImage cardValue = cardPartExtractService.extractValue(card);
      BufferedImage cardSuit = cardPartExtractService.extractSuit(card);
    }

    return "QcJd5h";
  }



}
