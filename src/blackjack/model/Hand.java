package blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    final List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card){
        this.cards.add(card);
    }
}
