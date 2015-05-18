package Tables;

import Cards.Deck;
import Cards.Hand;

/**
 * Created by ����� on 16.05.2015.
 */
public class Dealer {

    private Table table;
    private Deck deck;

    protected Dealer () {
        table = new Table();
        deck = new Deck();
    }

    public void ResetDeck() {
        deck.reset();
    }
    public void DealFlop () {
        commonCards.SetFlop(deck.retrieve(), deck.retrieve(), deck.retrieve());
    }
    public void DealTurn () {
        commonCards.SetTurn(deck.retrieve());
    }
    public void DealRiver () {
        commonCards.SetRiver(deck.retrieve());
    }
    public void DealHands () {
        for (int i = 0; i < 9; i++) {
            if (players[i] != null) {
                Hand h = new Hand(deck.retrieve(), deck.retrieve());
                players[i].GiveAHand(h);
            }
        }
    }
    public void Play () {
        //�������� �������

        //----------------
        while (true) {//��������
            table.ResetDeck();
            //�������
            table.DealHands();
            //�������
            BetRound();
            table.DealFlop();
            //��������
            BetRound();
            table.DealTurn();
            //Ҹ��
            BetRound();
            table.DealRiver();
            //�����
            BetRound();
            //��������
            //�������� �� ���-������ ����?
            //������� �� ���-������?
            table.ButtonMove();
        }
    }
}
