/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;
import java.io.IOException;
import java.sql.*;

public class DBConnect extends SQLException {

    private static DBConnect instance = null;
    private String url = "jdbc:sqlite:D:\\SQLite\\Users.db";// schimbati cu path-ul bazei de date
    public Connection c = null;
    public Statement s = null;
    private ResultSet rs = null;

    private DBConnect() {
        try {
            if (c == null) {
                c = DriverManager.getConnection(url);
                s = c.createStatement();

            }
        } catch (SQLException e) {
            System.out.println("Database not connected!");
        }

    }

    public static DBConnect initDB() {
        if (instance == null) {
            instance = new DBConnect();
        }
        return instance;
    }
}
