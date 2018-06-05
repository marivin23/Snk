/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Date;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 *
 * @author Xeno
 */
public class CurentDate {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    public CurentDate() {
    }

    public String getDate() {
        return dtf.format(now);
    }
}
