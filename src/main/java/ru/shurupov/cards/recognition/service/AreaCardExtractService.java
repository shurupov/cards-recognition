package ru.shurupov.cards.recognition.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ru.shurupov.cards.recognition.config.CardsAreaConfig;

@RequiredArgsConstructor
public class AreaCardExtractService {

  private static final int ACCEPTABLE_GRAY = new Color(120, 120, 120).getRGB();
  private static final int ACCEPTABLE_WHITE = Color.WHITE.getRGB();

  private static final Collection<Integer> ACCEPTABLE_CARD_COLORS = List.of(ACCEPTABLE_WHITE, ACCEPTABLE_GRAY);

  private final CardsAreaConfig cardsAreaConfig;

  List<BufferedImage> extractCards(BufferedImage area) {

    List<BufferedImage> cards = new ArrayList<>();

    int x = cardsAreaConfig.getLeft();
    int color;

    do {
      do {
        color = area.getRGB(x, cardsAreaConfig.getTop());
        x++;
      } while (x < cardsAreaConfig.getRight() && !ACCEPTABLE_CARD_COLORS.contains(color));
      int cardLeft = x;
      do {
        color = area.getRGB(x, cardsAreaConfig.getTop());
        x++;
      } while (x < cardsAreaConfig.getRight() && ACCEPTABLE_CARD_COLORS.contains(color));
      int cardWidth = x - cardLeft - 1;

      if (cardWidth > 1) {
        BufferedImage card = area.getSubimage(cardLeft, cardsAreaConfig.getTop(), cardWidth, cardsAreaConfig.getHeight());
        cards.add(card);
      }

    } while (x < cardsAreaConfig.getRight());

    return cards;
  }
}
