package Tables;

import Cards.Deck;
import Cards.Hand;
import Lan.Server;
import Players.Player;

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

    public void seatClients () {
        //��������� ������ ������
        Server server = new Server();
        Player[] players = server.ConnectPlayers();
        table.addPlayers(players);
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
        table.bank = 0;
        while (true) {//��������
            table.bank = 0;
            ResetDeck();
            //�������
            DealHands();
            //�������
            System.out.println("Preflop started");
            Round();
            System.out.println("Preflop end");
            DealFlop();
            System.out.println("Postflop started");
            //��������
            Round();
            System.out.println("Postflop ended");
            DealTurn();
            System.out.println("Turn started");
            //Ҹ��
            Round();
            System.out.println("Turn finished");
            DealRiver();
            System.out.println("River started");
            //�����
            Round();
            System.out.println("Kto pobedil sami reshayte");
            //��������
            //�������� �� ���-������ ����?
            //������� �� ���-������?
            table.ButtonMove();
        }
    }
    private void Round() {
        int gnida = table.button;
        int currentBet = 0;
        int previousBet;
        do {
            previousBet = currentBet;
            gnida = gnidaMove(gnida);
            //�����, ������ ��� ���, ������� ������ ������ ������ gnida ������ �� ���
            table.players[gnida].Send("You turn");
            // ���� ������ �� ������ �����
            int massage;
            massage = table.players[gnida].GetInt();
            if (massage == -1) {
                table.players[gnida].Fold();
                System.out.println("player " + gnida + " fold");
            } else {
                if (massage == 0) {
                    System.out.println("player " + gnida + " check/call");
                } else {
                    System.out.println("player " + gnida + " rize" + massage);
                    currentBet += massage;
                    table.bank += currentBet + massage;
                    System.out.println("Bank" + table.bank);
                }
            }
        } while (!(gnida == table.button && previousBet == currentBet));
    }

    private int gnidaMove (int g) {
        int gTmp = g;
        do {
            gTmp++;
            gTmp =  gTmp % 9;
        } while (table.players[gTmp] == null);
        return gTmp;
    }
}
