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

public class User {

    private static User user;

    private String userName;
    private String passWord;
    private String eMail;
    private static int highScore = 0;
    public static int nowScore = 0;

    public User(String user, String pass, String email) { //constructor
        this.userName = user;
        this.passWord = pass;
        this.eMail = email;
    }

    public User(String user){
        this.userName = user;
    }
    
    public static User newUser() {
        if (user == null) {
            user = new User("", "", "");
        } else {
            return user;
        }

        return user;
    }

    //seteri
    public void setHighScore(int hiscore) {
        highScore = hiscore;
    }

    void setEmail(String email) {
        eMail = email;
    }

    void setUsername(String username) {
        userName = username;
    }

    void setPassword(String password) {
        userName = password;
    }

    public static void setNowScore() {
        nowScore++;
    }
    
    public static void setHighScore(){
        highScore = nowScore;
    }
    //geteri

    public static int getNowScore() {
        return nowScore;
    }

    public int getHighScore() {
        return this.highScore;
    }

    String getEmail() {
        return this.eMail;
    }

    public String getUsername() {
        return this.userName;
    }

    String getPassword() {
        return this.passWord;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean equals(User guy) {
        boolean ok = true;
        if ((this.userName != guy.userName) || (this.passWord != guy.passWord) || (this.eMail != guy.eMail) || (this.highScore != guy.highScore)) {
            ok = false;
        }
        return ok;
    }

}
