/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Xeno
 */
package DataBase;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBManager{ // e pentru crearea de tabele pentru useri tabele de poze pentru fiecare user etc. (chestii de genul) 

    DBConnect managerCon = null;
    private ResultSet rs;

    public DBManager() {
        managerCon = DBConnect.initDB();
        creareTableUseri();
        createScoreBoard();
    }

    void creareTableUseri() {
        try {
            Statement st = managerCon.c.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT, usr text  NOT NULL, pwd text NOT NULL, email text NOT NULL, highscore INTEGER);");
            System.out.println("Table useri creat daca nu exista deja");
        } catch (SQLException e) {
            System.out.println("Eroare de SQL:" + e);
        }
    }

    int countExistingUsers() {
        int count = 0;
        try {
            rs = managerCon.s.executeQuery("SELECT COUNT(id) FROM Users;");
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            System.out.println("Eroare de SQL:" + e);
            return -1;
        }
    }

    void creareTabelePoze() { // daca nu exista deja
        String uName;

        try {
            rs = managerCon.s.executeQuery("SELECT * FROM Users;");
            while (rs.next()) {
                uName = rs.getString("usr");
                creareTabelUser(uName);
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL in creareTabelePoze(); " + e);
        }
    }

    void creareTabelUser(String user) {
        String inject;

        inject = "CREATE TABLE IF NOT EXISTS " + user + "(id INTEGER PRIMARY KEY, fileName text, picture BLOB);";
        try {
            Statement auxStat = managerCon.c.createStatement();
            auxStat.executeUpdate(inject);
            System.out.println("Tabel " + user + " creat daca nu exista deja");
        } catch (SQLException e) {
            System.out.println("Eroare SQL in creareTabelUser(); " + e);
        }
    }

    public void addNewUser(User cineva) {

        String updateSQL = "INSERT INTO Users (usr,pwd,email)\n"
                + "VALUES"
                + " (?,?,?)";

        try {
            PreparedStatement ps = managerCon.c.prepareStatement(updateSQL);

            ps.setString(1, cineva.getUsername());
            ps.setString(2, cineva.getPassword());
            ps.setString(3, cineva.getEmail());
            ps.executeUpdate();
            System.out.println("User nou creat cu numele cu numele: " + cineva.getUsername());
            //creareTabelePoze();
        } catch (SQLException e) {
            System.out.println("Eroare SQL in addNewUser(): ");
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(User cineva) { // presupunem ca avem deja userul acela facut ca obiect si initiat cum trebuie
        String inject = "DELETE FROM Users WHERE usr = ? AND pwd = ? AND email = ?;";
        try {
            PreparedStatement ps = managerCon.c.prepareStatement(inject);
            ps.setString(1, cineva.getUsername());
            ps.setString(2, cineva.getPassword());
            ps.setString(3, cineva.getEmail());
            ps.executeUpdate();
            System.out.println("Deletat din Useri: " + cineva.getUsername());
            //deletat din Users
            inject = "DROP TABLE " + cineva.getUsername();
            Statement s = managerCon.c.createStatement();
            s.executeUpdate(inject);
            System.out.println("Deletat tabela de poze a: " + cineva.getUsername());
            //deletat tabela de poze a userului
        } catch (SQLException e) {
            System.out.println("Eroare SQL in deleteUser():" + e);
        }
    }

    public void createScoreBoard() {
        try {
            managerCon.s.executeUpdate("CREATE TABLE IF NOT EXISTS ScoreBoard (id INTEGER PRIMARY KEY AUTOINCREMENT, usr text,score INTEGER);");
            System.out.println("ScoreBoard creat daca nu exista deja");
        } catch (SQLException e) {
            System.out.println("Eroare de SQL in createScoreBoard(): " + e);
        }
    }
    public void addScore(String user, int score){
        String updateSQL = "INSERT INTO ScoreBoard (usr,score)\n"
                + "VALUES"
                + " (?,?)";

        try {
            PreparedStatement ps = managerCon.c.prepareStatement(updateSQL);

            ps.setString(1, user);
            ps.setInt(2, score);
            ps.executeUpdate();
            //System.out.println("User nou creat cu numele cu numele: " + cineva.getUsername());
            creareTabelePoze();
        } catch (SQLException e) {
            System.out.println("Eroare SQL in addNewUser(): ");
            System.out.println(e.getMessage());
        }
    }
    public void sortScoreBoard(){
        String sqlinject = "SELECT\n"
                + " id,\n"
                + "usr,\n"
                + "score\n"
                + "FROM\n"
                + "ScoreBoard\n"
                + "ORDER BY\n"
                + "score DESC;";
        try{
            Statement stat = managerCon.c.createStatement();
        ResultSet res = stat.executeQuery(sqlinject);
        managerCon.s.executeUpdate("CREATE TABLE IF NOT EXISTS ScoreBoardAux (id INTEGER PRIMARY KEY , usr text,score INTEGER);");
        //creat tabela auxiliara
        System.out.println("-----------Creat tabela aux------------");
        while(res.next()){
            injectIntoAuxBoard(res);
        }
        //pus in tabela auxiliara scoruri sortate
           System.out.println("-----------Pus in tabela aux scoruri sortate ------------");
        String dropInject = "DROP TABLE ScoreBoard";
            Statement s = managerCon.c.createStatement();
            s.executeUpdate(dropInject);
            //sters tabela veche
                System.out.println("-----------Sters tabela veche ------------");
            String alterInject = "ALTER TABLE ScoreBoardAux\n"
                    + "  RENAME TO ScoreBoard;";
            s = managerCon.c.createStatement();
            s.executeUpdate(alterInject);
            // redenumit tabela auxiliara 
             System.out.println("-----------Redenumit tabela auxiliara ------------");
            //finish
        }catch(SQLException e){
            System.out.println("Eroare SQL in sortScoreBoard(): "+ e);
        }
    }
    private void injectIntoAuxBoard(ResultSet r){
         String updateSQL = "INSERT INTO ScoreBoardAux (usr,score)\n"
                + "VALUES"
                + " (?,?)";

        try {
            PreparedStatement ps = managerCon.c.prepareStatement(updateSQL);
            String usrToSet = r.getString("usr");
            int scoreToSet = r.getInt("score");
            ps.setString(1, usrToSet);
            ps.setInt(2, scoreToSet);
            ps.executeUpdate();
            System.out.println("Injectat in tabela de scor auxiliara: id = ; usr = "+usrToSet+"; score = "+scoreToSet);
        } catch (SQLException e) {
            System.out.println("Eroare SQL in injectIntoAuxBoard(): ");
            System.out.println(e.getMessage());
        }
    }
       public boolean checkIfUsrExists(String nameToCheck) throws SQLException {

        boolean exists = false;

        rs = managerCon.s.executeQuery("SELECT * FROM Users WHERE usr = '"+nameToCheck+"';");
        try {
            if (rs.getString("usr").equals(nameToCheck)) {
                exists = true;
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL in checkIfUserExists(usr):");
            System.out.println(e);
        }
        return exists;
    }
    public boolean checkIfUsrExists(String nameToCheck, String pass){
      
        boolean exists = false;

        try {
            PreparedStatement ps = managerCon.c.prepareStatement("SELECT * FROM Users WHERE usr = ? AND pwd = ?;");
             ResultSet r;
            ps.setString(1, nameToCheck);
            ps.setString(2, pass);
           r = ps.executeQuery();
            if ((r.getString("usr").equals(nameToCheck)) &(r.getString("pwd").equals(pass))) {
                exists = true;
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL in checkIfUserExists(usr,pass):");
            System.out.println(e);
        }
        return exists;
    }
    public ArrayList<Score> getScoreBoard(){
        ArrayList <Score> Result =new ArrayList<Score>();
        sortScoreBoard();
             try {
            Statement ps = managerCon.c.createStatement();
             ResultSet r;
        
           r = ps.executeQuery("SELECT * FROM ScoreBoard;");
            while(rs.next()){
                Score gottenScore =new Score(rs.getInt("id"),rs.getString("usr"),rs.getInt("score"));
                Result.add(gottenScore);
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL in getScoreBoard() : "+e);
        }
        return Result;
    }

    /*void recountUsersID() {
        String inject = "SET count = 0;\n" +
        "UPDATE Users SET Users.id = count:= count + 1;";
        try {
            Statement s = managerCon.c.createStatement();
            s.executeUpdate(inject);
        } catch (SQLException e) {
            System.out.println("Eroare SQL in recountUsersID(): "+e);
        }

    }*/
}
