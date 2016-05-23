package domiurg;

import java.sql.*;
import java.util.*;

public class SQLConnect {
    private static final int SIZE = 50;

    private Connection connect;
    private Statement statement;
    private ResultSet res;

    private String login;
    private String password;
    private String URL;

    private HashMap last = new HashMap();
    private HashMap curr = new HashMap();
    private boolean firstTime;

    public SQLConnect(){
        connect = null;
        statement = null;
        res = null;

        login = "arduino_user";
        password = "sometext";
        URL = "jdbc:mysql://domiurg-lab.duckdns.org/arduino_user?";

        firstTime = true;
    }

    public SQLConnect(String host, String db, String user, String pass){
        connect = null;
        statement = null;
        res = null;

        login = user;
        password = pass;
        URL = "jdbc://mysql://" + host + "/" + db + "?";

        firstTime = true;
    }

    public HashMap readDB () throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection(URL, login, password);

            statement = connect.createStatement();

            String query = "SELECT *" +
                            " FROM `90:a2:da:f8:0d:d4_data_2`" +
                            " ORDER BY `id` DESC" +
                            " LIMIT " + SIZE;
            res = statement.executeQuery(query);
            return getResult(res);

        } catch (Exception e){
            throw e;
        }
    }

    private HashMap getResult(ResultSet res) throws SQLException{
        while (res.next()){
            // Print results to console as debug info
            String id = res.getString("id");
            String time = res.getString("time");
            String temp = res.getString("temperature");
            String humi = res.getString("humidity");
            

            //add Humidity to the list
            if (firstTime){
               last.put(id, humi);
            } else
                curr.put(id, humi);

//            System.out.println("ID: " + id);
//            System.out.println("Time: " + time);
//            System.out.println("Temperature: " + temp);
//            System.out.println("Humidity: " + humi);
//            System.out.println("");
        }

        //Get new values
        if (!firstTime){
            HashMap result = new HashMap();
            Set set = curr.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()){
                Map.Entry tuple = (Map.Entry)i.next();
                if (!last.containsKey(tuple.getKey()))
                    result.put(tuple.getKey(), tuple.getValue());
            }

            //copy current HashMap into Last
            last.clear();
            last = new HashMap(curr);
            return result;
        }
        else {
            firstTime = false;
            return last;
        }
    }
}
