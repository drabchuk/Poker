package Cards;

/**
 * Created by ����� on 15.05.2015.
 */
public class Hand {
    public Card c1;
    public Card c2;
    public Hand (Card card1, Card card2) {
        c1 = card1;
        c2 = card2;
    }
    public String toString(){
        return c1.toString() + "&" + c2.toString() + ".";
    }
}
