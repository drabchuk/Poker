import Lan.Server;
import Tables.Dealer;

import java.sql.SQLException;

/**
 * Created by Δενθρ on 15.05.2015.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Dealer dealer = new Dealer();
        dealer.seatClients();
        dealer.Play();
    }
}
