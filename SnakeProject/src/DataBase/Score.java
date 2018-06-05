/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

/**
 *
 * @author Xeno
 */
public class Score {

    public int Id;
    public String Usr;
    public int Score;

    Score(int id, String usr, int score) {
        Id = id;
        Usr = usr;
        Score = score;
    }

    void setScore(int score) {
        Score = score;
    }

    void setId(int id) {
        Id = id;
    }

    void setUsr(String usr) {
        Usr = usr;
    }

    int getScore() {
        return Score;
    }

    int getId() {
        return Id;
    }

    String getUsr() {
        return Usr;
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    public boolean equals(Score s){
        boolean ok = true;
        if( (this.Id !=s.getId()) || (this.Usr !=s.getUsr()) ){
            ok = false;
        }
        return ok;
    }
}
