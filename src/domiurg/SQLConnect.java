package domiurg;

import java.sql.*;

public class SQLConnect {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet res = null;

    private String URL = "jdbc:mysql://domiurg-lab.duckdns.org/arduino_user?";

    public void readDB () throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection(URL, "arduino_user", "sometext");

            statement = connect.createStatement();

            String query = "SELECT *" +
                            " FROM `90:a2:da:f8:0d:d4_data_2`" +
                            " ORDER BY `id` DESC" +
                            " LIMIT 50";
            res = statement.executeQuery(query);
            getResult(res);

        } catch (Exception e){
            throw e;
        }
    }

    public void getResult(ResultSet res) throws SQLException{
        while (res.next()){
            // Print results to console as debug info
            String id = res.getString("id");
            String time = res.getString("time");
            String temp = res.getString("temperature");
            String humi = res.getString("humidity");

            System.out.println("ID: " + id);
            System.out.println("Time: " + time);
            System.out.println("Temperature: " + temp);
            System.out.println("Humidity: " + humi);
            System.out.println("");
        }
    }

}
