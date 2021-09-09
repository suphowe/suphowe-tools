package com.soft.method.picture;

import javax.swing.*;

/**
 * 图片展示
 * @author suphowe
 */
public class PicturePlayer extends JFrame {

    ImageIcon[] imageIcons = {};

    ImageIcon imageIcon;


    public PicturePlayer(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }


    public PicturePlayer(ImageIcon[] imageIcons) {
        this.imageIcons = imageIcons;
    }

    /**
     * 打开单张图片
     */
    public void playImage() {
        PictureJpanel pictureJpanel = new PictureJpanel(imageIcon);
        this.add(pictureJpanel);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Picture");
        this.setVisible(true);
        pictureJpanel.repaint();
    }

    /**
     * 循环播放多张图片
     */
    public void playImages(int cycleTime) {
        PictureJpanel pictureJpanel = new PictureJpanel(imageIcons);
        this.add(pictureJpanel);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Picture");
        this.setVisible(true);
        // 循环播放imageIcons内容
        Timer timer = new Timer(cycleTime, e -> pictureJpanel.repaint());
        timer.start();
    }

}
