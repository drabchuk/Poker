package Players;

import Cards.Card;
import Cards.CollectionCards;
import Cards.Hand;

import java.io.*;
import java.sql.*;
import java.net.Socket;

/**
 * Created by ƒенис on 15.05.2015.
 */
public class Player {
    public boolean isInGame;
    private InputStream sin;
    private OutputStream sout;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private String login;
    private int count;
    private Hand hand;
    private int position;
    private int bankroll;
    private int bet;
    private CollectionCards collection;

    public Player() {
        isInGame = false;
        hand = null;
        position = -1;
        bankroll = 0;
        bet = 0;
    }

    public String getCombination() {
        return collection.checkCombination();
    }

    public CollectionCards getCollectionCards() {
        return collection;
    }

    public void setCombination(Card... cards) {
        collection = new CollectionCards(cards);
    }

    public String getLogin() {
        return login;
    }
    public String getIP() {
        return socket.getInetAddress().toString();
    }
    public int getPosition() {
        return position;
    }
    public Hand getHand() {
        return hand;
    }

    public void Init() {
        socket = null;
        login = null;
    }

    /*public void sendUTF(String s) {
        try {
            out.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("send " + s);
    }

    public void sendInt(int x) {
        try {
            out.writeInt(x);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("send " + x);
    }*/
    public void sendUTF(String s) {
        try {
            out.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("send " + s);
    }

    public void sendInt(int x) {
        try {
            out.writeInt(x);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("send " + x);
    }

    public boolean readBoolean() {
        boolean b;
        try {
            b = in.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return b;
    }

    public int readInt() {
        int data = -2;
        try {
            data = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /*public int readInt() {
        int data = -2;
        try {
            data = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }*/

    public void setPlayer(Socket st, int c) {
        isInGame = true;
        count = c;
        socket = st;
        try {
            sin = socket.getInputStream();
            sout = socket.getOutputStream();
            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);
            login = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        position = count;//здесь можно попросить позицию
        System.out.print(login);
        System.out.println(" - " + count + "Player");
        setBankroll(1000);
    }

    public void setBankroll (int b) {
        bankroll = b;
    }

    public void SetPosition(int pos) {
        position = pos;
    }

    public int GetPosition() {
        return position;
    }

    public int getBet() {
        return bet;
    }

    public void GiveAHand(Hand h) {
        hand = h;
        //  ал€н, напиши тут код, который пошлет этому игроку его карты
        /*try {
            out.writeUTF(h.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public int getBankroll () {
        return bankroll;
    }

    public void initAMoney(int m) {
        bankroll = m;
        /*try {
            out.writeUTF("you started money");
            out.writeInt(m);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void giveMoney(int m) {
        bankroll += m;
    }

    public int action (int min, int max) {
        sendUTF("you turn");
        sendInt(min);
        sendInt(max);
        return readInt();
    }

    public void takeMoney(int m) {
        bankroll -= m;
        bet += m;
        /*try {
            out.writeUTF("give me a money");
            out.writeInt(m);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    Hand TakeAHand() {
        Hand tmp = hand;
        hand = null;
        return tmp;
    }

    public void Fold() {
        hand = null;
        isInGame = false;
    }

    public void setBet (int b) {
        bet = 0;
    }
}
