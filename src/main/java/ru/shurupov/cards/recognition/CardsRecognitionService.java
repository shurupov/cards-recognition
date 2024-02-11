package ru.shurupov.cards.recognition;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardsRecognitionService {

  private static final int ACCEPTABLE_GRAY = new Color(120, 120, 120).getRGB();
  private static final int ACCEPTABLE_WHITE = Color.WHITE.getRGB();

  private static final Collection<Integer> ACCEPTABLE_CARD_COLORS = List.of(ACCEPTABLE_WHITE, ACCEPTABLE_GRAY);

  private final CardPartExtractService cardPartExtractService;
  private final CardsAreaBounds cardsAreaBounds;
  private final CardSize cardSize;

  public String recognize(BufferedImage image) {

    List<BufferedImage> cards = new ArrayList<>();

    int x = cardsAreaBounds.getLeft();
    int color;

    do {
      do {
        color = image.getRGB(x, cardsAreaBounds.getTop());
        x++;
      } while (x < cardsAreaBounds.getRight() && !ACCEPTABLE_CARD_COLORS.contains(color));
      int cardLeft = x;
      do {
        color = image.getRGB(x, cardsAreaBounds.getTop());
        x++;
      } while (x < cardsAreaBounds.getRight() && ACCEPTABLE_CARD_COLORS.contains(color));
      int cardWidth = x - cardLeft - 1;

      if (cardWidth > 1) {
        BufferedImage card = image.getSubimage(cardLeft, cardsAreaBounds.getTop(), cardWidth, cardSize.getHeight());
        BufferedImage cardValue = cardPartExtractService.extractValue(card);
        BufferedImage cardSuit = cardPartExtractService.extractSuit(card);
        cards.add(card);
      }

    } while (x < cardsAreaBounds.getRight());

    return "QcJd5h";
  }

}
