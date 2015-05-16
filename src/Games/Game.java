package Games;

import Players.Player;
import Tables.Table;

/**
 * Created by ����� on 16.05.2015.
 */
public class Game {
    Table table;

    public Game () {
        table = new Table();
    }

    public void addPlayer (Player p, int position) {
        p.SetPosition(position);
        table.addPlayer(p);
    }
    public void DealHands () {
        table.DealHands();
    }
    public void DealFlop () {
        table.DealFlop();
    }
    public void DealTurn () {
        table.DealTurn();
    }
    public void DealRiver () {
        table.DealRiver();
    }
    public void ShowCards () {
        table.ShowCards();
    }
    public void ResetDeck () {
        table.ResetDeck();
    }
    public void Fold (int pos) {
        table.Fold(pos);
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

    private void BetRound () {
        int gnida = table.button;
        do {
            gnida++;
            gnida = gnida % 9;
            //����� ����
        } while (gnida != table.button);
    }
}
