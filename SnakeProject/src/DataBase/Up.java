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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Up {

    ResultSet rs;
    DBConnect con = null;

    public Up()  {
        con = DBConnect.initDB();
                        
    }

    private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Eroare FileNotFound: ");
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.out.println("Eroare I/O : ");
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }
    public void uploadPicture(User cineva, String fileName, String filePath) { // In table-ul [table] vrem sa adaugam la id-ul [id] cu filename-ul [filename]

        String updateSQL = "INSERT INTO "+cineva.getUsername()+"(fileName,picture)\n" 
                + "VALUES"
                + "(?,?);";
        try (PreparedStatement pstmt = con.c.prepareStatement(updateSQL)) {
           // pstmt.setString(1, cineva.getUsername());
            pstmt.setString(1, fileName);
         
            pstmt.setBytes(2, readFile(filePath));
            
            pstmt.executeUpdate();
            System.out.println("Fotografie stocata in baza de date");

        } catch (SQLException e) {
            System.out.println("Eroare SQL in uploadPicture(): "+e.getMessage());
        }
    }
    public void deletePicture(User somebody, String fileName){
        String inject = "DELETE FROM "+somebody.getUsername()+" WHERE fileName = ?;";
        try {
            PreparedStatement ps = con.c.prepareStatement(inject);
            ps.setString(1, fileName);
            ps.executeUpdate();
            System.out.println("Deletat Poza: " + fileName);
        } catch (SQLException e) {
            System.out.println("Eroare SQL in deletePicture():" + e);
        }
    }
    public String getUpDate(User cineva, String fileName){
        try{
        rs = con.s.executeQuery("SELECT * FROM "+cineva.getUsername()+" WHERE fileName = " + fileName + ";");
        String data = rs.getString("dateAdded");
        return data;
        }catch(SQLException e){
            System.out.println("Exceptie SQL in getUpDate():"+e);
        }
      return null;  
    }

    public boolean checkIfUsrExists(String nameToCheck) throws SQLException {

        boolean exists = false;

        rs = con.s.executeQuery("SELECT * FROM Users WHERE usr = '"+nameToCheck+"';");
        try {
            if (rs.getString("usr").equals(nameToCheck)) {
                exists = true;
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL in checkIfUserExists():");
            System.out.println(e);
        }
        return exists;
    }
    public boolean checkIfUsrExists(String nameToCheck, String pass){
       
        boolean exists = false;

        try {
            rs = con.s.executeQuery("SELECT * FROM Users WHERE usr = "+nameToCheck+" AND pwd = "+pass+";");
            if ((rs.getString("usr").equals(nameToCheck)) &(rs.getString("pwd").equals(pass))) {
                exists = true;
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL in checkIfUserExists():");
            System.out.println(e);
        }
        return exists;
    }

}
