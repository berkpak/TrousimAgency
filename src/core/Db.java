package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    //Singleton Design Pattern
    private static Db instance = null;
    private Connection connection = null;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/trouismagency";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASS = "postgres";

    //Database baglantisi
    private Db() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    //Nesne uretmeden geriye connection donduren metot
    public static Connection getInstance() {
        try{
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Db();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return instance.getConnection();
    }
}
