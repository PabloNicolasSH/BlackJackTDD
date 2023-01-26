package blackjack;

import blackjack.model.Card;
import blackjack.model.Hand;
import blackjack.model.Player;

import java.util.ArrayList;
import java.util.List;

public class BlackJack {
    public static int calculateBetValue(Hand bet){
        int aces = 0;
        int value = 0;
        for(Card card : bet.getCards()){
            if (card.isAce()) aces++;
            value += card.value();
        }
        while (value > 21 && aces != 0) value -= 10;
        return value;
    }

    public static boolean isBlackJack(Hand bet){
        return calculateBetValue(bet) == 21 && bet.getCards().size() == 2;
    }

    public static void croupierTakeCard(Player croupier, List<Card> deck){
        while(calculateBetValue(croupier.getBet()) < 17) croupier.getBet().addCard(deck.remove(0));
    }

    public static List<Player> getWinners(List<Player> players, Player croupier, List<Card> deck){
        List<Player> winners = new ArrayList<>();
        croupierTakeCard(croupier, deck);
        int croupierHandValue = calculateBetValue(croupier.getBet());

        if (isBlackJack(croupier.getBet())) return winners;

        for(Player player : players){
            if (isBlackJack(player.getBet())){
                winners.add(player);
                continue;
            }
            if (calculateBetValue(player.getBet()) > croupierHandValue && calculateBetValue(player.getBet()) <= 21) winners.add(player);
        }

        return winners;
    }
}
