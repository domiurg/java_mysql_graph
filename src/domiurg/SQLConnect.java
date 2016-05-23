package domiurg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLConnect {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet res = null;

    private String URL = "jdbc:mysql://domiurg-lab.duckdns.org/arduino_user?";

    public void readDB () throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection(URL, "arduino_user", "sometext");

        } catch (Exception e){
            throw e;
        }
    }

}
