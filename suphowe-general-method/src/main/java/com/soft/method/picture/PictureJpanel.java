package com.soft.method.picture;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel标签
 *
 * @author suphowe
 */
public class PictureJpanel extends JPanel {

    int index;

    ImageIcon[] imageIcons = {};

    ImageIcon imageIcon;

    public PictureJpanel(ImageIcon[] imageIcons) {
        this.imageIcons = imageIcons;
    }

    public PictureJpanel(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(imageIcons[index % imageIcons.length].getImage(), 0, 0, null);
        index++;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(imageIcon.getImage(), 0, 0, null);
        index++;
    }
}
