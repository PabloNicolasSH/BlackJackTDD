package blackjack.model;

public class Player {

    private final String name;
    private final Hand bet;

    public Player(String name, Hand bet) {
        this.bet = bet;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Hand getBet() {
        return bet;
    }
}