package tests;

import blackjack.BlackJack;
import blackjack.model.Card;
import blackjack.model.Hand;
import blackjack.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static blackjack.model.Card.*;
import static junit.framework.TestCase.*;

public class BlackJackTest {

    private final Hand blackJackHand = new Hand(Arrays.asList(_10, Ace));
    private final Hand bet1 = new Hand(Arrays.asList(_10,Ace,Ace,_7));
    private final Hand bet2 = new Hand(Arrays.asList(_7,King,_9));
    private final Hand bet3 = new Hand(Arrays.asList(_9,Ace,Ace,Ace));
    private final List<Card> deck = new ArrayList<>(Arrays.asList(_8,_7,_3,Jack,_7,Queen,_4,_9,King));

    @Test
    public void checkingFacesAndNormalCards(){
        assertFalse(Ace.isFace());
        assertTrue(Ace.isAce());
        assertFalse(_3.isAce());
        assertTrue(King.isFace());
    }

    @Test
    public void checkingCardValue(){
        assertEquals(Ace.value(), 11);
        assertEquals(King.value(), 10);
        assertEquals(_3.value(), 3);
        assertEquals(_8.value(), 8);
    }

    @Test
    public void returnPlayerHand(){
        Player player = new Player("Player", bet1);
        assertEquals(player.getBet(), bet1);
    }

    @Test
    public void addCardsToBet(){
        Player player = new Player("Player", bet2);
        player.getBet().addCard(_4);
        assertEquals(player.getBet().getCards(), Arrays.asList(_7,King,_9,_4));
    }

    @Test
    public void givenABetToCalculateShouldCalculateBetValue() {
        assertEquals(BlackJack.calculateBetValue(bet1), 19);
        assertEquals(BlackJack.calculateBetValue(bet2), 26);
        assertEquals(BlackJack.calculateBetValue(blackJackHand), 21);
    }

    @Test
    public void givenAHandCheckingIfIsBlackjack() {
        assertTrue(BlackJack.isBlackJack(blackJackHand));
    }

    @Test
    public void givenACroupierHandUnder17ShouldTakeCards() {
        Player croupier = new Player("Croupier", new Hand(Arrays.asList(_2,_10)));
        BlackJack.croupierTakeCard(croupier, deck);
        assertEquals(croupier.getBet().getCards(), Arrays.asList(_2,_10,_8));
    }

    @Test
    public void givenAGameWinsCroupier(){
        List<Player> players = new ArrayList<>();
        Player croupier = new Player("Croupier", blackJackHand);
        Player p1 = new Player("P1", bet1);
        players.add(p1);
        Player p2 = new Player("P2", bet2);
        players.add(p2);
        Player p3 = new Player("P3", bet3);
        players.add(p3);
        List<Player> winners = BlackJack.getWinners(players, croupier, deck);
        assertEquals(List.of(), winners);
    }

    @Test
    public void givenAGameWinsPlayer1() {
        Player p1 = new Player("P1", new Hand(Arrays.asList(Card.Jack, Card.Ace)));
        Player p2 = new Player("P2", new Hand(Arrays.asList(Card._10, Card._5, Card._6)));
        Player p3 = new Player("P3", new Hand(Arrays.asList(Card._3, Card._6, Card.Ace, Card._3, Card.Ace, Card.King)));
        Player croupier = new Player("Croupier", new Hand(Arrays.asList(Card._9, Card._7)));
        List<Card> deck = new ArrayList<>(Arrays.asList(Card._5, Card._4, Card.King, Card._2));

        assertEquals(List.of(p1), BlackJack.getWinners(Arrays.asList(p1,p2,p3),croupier,deck));
    }

    @Test
    public void givenAGameWinsPlayer1Player3() {
        Player p1 = new Player("p1", new Hand(Arrays.asList(Card._10, Card.King)));
        Player p2 = new Player("p2", new Hand(Arrays.asList(Card._10, Card._2, Card._6)));
        Player p3 = new Player("p3", new Hand(Arrays.asList(Card._8, Card._8, Card._5)));
        Player croupier = new Player("Croupier", new Hand(Arrays.asList(Card._5, Card._10)));
        List<Card> deck = new ArrayList<>(Arrays.asList(Card.Ace, Card._3, Card.King, Card._2));

        assertEquals(Arrays.asList(p1,p3), BlackJack.getWinners(Arrays.asList(p1,p2,p3),croupier,deck));
    }
}
