package domiurg;

import java.sql.*;

public class SQLConnect {
    private static final int SIZE = 50;

    private Connection connect;
    private Statement statement;
    private ResultSet res;

    private String login;
    private String password;
    private String URL;

    private double[] last = new double[SIZE];
    private double[] curr = new double[SIZE];
    private boolean firstTime;

    public SQLConnect(){
        this.connect = null;
        this.statement = null;
        this.res = null;

        this.login = "arduino_user";
        this.password = "sometext";
        this.URL = "jdbc:mysql://domiurg-lab.duckdns.org/arduino_user?";

        for (int i = 0; i < SIZE; i++){
            this.last[i] = 0;
            this.curr[i] = 0;
        }

        this.firstTime = true;
    }

    public SQLConnect(String host, String db, String user, String pass){
        this.connect = null;
        this.statement = null;
        this.res = null;

        this.login = user;
        this.password = pass;
        this.URL = "jdbc://mysql://" + host + "/" + db + "?";

        for (int i = 0; i < SIZE; i++){
            this.last[i] = 0;
            this.curr[i] = 0;
        }

        this.firstTime = true;
    }

    public double[] readDB () throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection(URL, this.login, this.password);

            statement = connect.createStatement();

            String query = "SELECT *" +
                            " FROM `90:a2:da:f8:0d:d4_data_2`" +
                            " ORDER BY `id` DESC" +
                            " LIMIT " + SIZE;
            res = statement.executeQuery(query);
            getResult(res);

        } catch (Exception e){
            throw e;
        }

        return curr;
    }

    private double[] getResult(ResultSet res) throws SQLException{
        int i = 0;
        while (res.next()){
            // Print results to console as debug info
            String id = res.getString("id");
            String time = res.getString("time");
            String temp = res.getString("temperature");
            String humi = res.getString("humidity");

            if (this.firstTime){
                this.last[i] = Double.parseDouble(humi);
            } else
                this.curr[i] = Double.parseDouble(humi);

            System.out.println("ID: " + id);
            System.out.println("Time: " + time);
            System.out.println("Temperature: " + temp);
            System.out.println("Humidity: " + humi);
            System.out.println("");

            i++;
        }

        return curr;
    }

}
