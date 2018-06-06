/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SnakeGame;

import DataBase.User;

/**
 *
 * @author Cucc
 */
public class LevelLogic {
    
    public static void changeSpeed(){
        if(User.getNowScore() > 5 && User.getNowScore() < 10)
            ThreadsController.speed = 85;
        else 
            ThreadsController.speed = 75;
    }
    
    
}
