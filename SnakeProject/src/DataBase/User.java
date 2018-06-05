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
    private int highScore;
    private int nowScore;

    public User(String user, String pass, String email) { //constructor
        this.userName = user;
        this.passWord = pass;
        this.eMail = email;
        /*this.highScore = hiscore;
        this.nowScore = nowscore;*/
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

    void setNowScore(int score) {
        nowScore = score;
    }
    //geteri

    int getNowScore() {
        return this.nowScore;
    }

    int getHighScore() {
        return this.highScore;
    }

    String getEmail() {
        return this.eMail;
    }

    String getUsername() {
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
