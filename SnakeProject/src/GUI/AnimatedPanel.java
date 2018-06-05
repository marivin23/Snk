/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Xeno
 */
public class AnimatedPanel {
        public AnimatedPanel(){
            invoke();
        }
        
       static class ImagePanel extends JPanel {
        private Image image;
        ImagePanel(Image image) {
            this.image = image;
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
        }
    }
       
    public static void createAndShowUI() {
        try {
              JFrame frame;
             frame = new JFrame("Image");
           frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            Image image = Toolkit.getDefaultToolkit().getImage(
                    "C:\\Users\\Xeno\\Documents\\GitHub\\SnakeGame\\SnakeProject\\src\\GUI\\giphy1.gif");

            ImagePanel imagePanel = new ImagePanel(image);

            imagePanel.add(new JLabel("Some label"));
            frame.add(imagePanel);
            frame.setSize(1000, 1000);
            frame.setVisible(true);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invoke() {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });
    }
}
