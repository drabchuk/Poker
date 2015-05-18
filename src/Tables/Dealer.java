package Tables;

import Cards.Deck;
import Cards.Hand;

/**
 * Created by ����� on 16.05.2015.
 */
public class Dealer {

    private Table table;
    private Deck deck;

    public Dealer () {
        table = new Table();
        deck = new Deck();
    }

    public void ResetDeck() {
        deck.reset();
    }
    public void DealFlop () {
        table.DealFlop(deck.retrieve(), deck.retrieve(), deck.retrieve());
    }
    public void DealTurn () {
        table.DealTurn(deck.retrieve());
    }
    public void DealRiver () {
        table.DealRiver(deck.retrieve());
    }
    public void DealHands () {
        for (int i = 0; i < 9; i++) {
            if (table.players[i] != null) {
                Hand h = new Hand(deck.retrieve(), deck.retrieve());
                table.players[i].GiveAHand(h);
            }
        }
    }
    public void Play () {
        //�������� �������
        //----------------
        while (true) {//��������
            ResetDeck();
            //�������
            DealHands();
            //�������
            Round();
            DealFlop();
            //��������
            Round();
            DealTurn();
            //Ҹ��
            Round();
            DealRiver();
            //�����
            Round();
            //��������
            //�������� �� ���-������ ����?
            //������� �� ���-������?
            table.ButtonMove();
        }
    }
    private void Round() {
        int gnida = table.button;
        do {
            gnida++;
            gnida = gnida % 9;
            //����� ����
        } while (gnida != table.button);
    }
}
