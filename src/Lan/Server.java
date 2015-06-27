package Lan;

import Forms.MainForm;
import Players.Player;

import javax.swing.*;
import java.sql.*;

public class Server {
    final int PLAYERS_COUNT = 2;
    public Player[] ConnectPlayers() throws ClassNotFoundException, SQLException {
        SocketServer sock = new SocketServer();
        MainForm frame = new MainForm(sock);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        for (int i = 0; i < 6; i++) {
            sock.players[i] = new Player();
        }
/*
        try {
            do {
                sock.players[sock.count].setPlayer(sock.ss.accept(), sock.count);
                sock.count++;
            } while (sock.count != PLAYERS_COUNT);
        }catch(Exception x) { x.printStackTrace(); }
*/
        Class.forName("com.mysql.jdbc.Driver");

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            if (connection != null) {
                Statement statement = connection.createStatement();

                //ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
                //while (resultSet.next()) {
                do {
                    sock.players[sock.count].setPlayer(sock.ss.accept(), sock.count);

                    statement.executeUpdate("INSERT INTO users (login) VALUES ('" +
                                                                sock.players[sock.count].getLogin() +
                                                                // "', '" +
                                                                //sock.players[sock.count].getIP() +
                                                                "')");
                    //System.out.println(resultSet.getString("login"));
                    sock.count++;//up!
                } while (sock.count != PLAYERS_COUNT);
                //}
            }
        }catch (Exception x) { x.printStackTrace(); }
        System.out.println("Game start!");
        return sock.players;
    }
}
