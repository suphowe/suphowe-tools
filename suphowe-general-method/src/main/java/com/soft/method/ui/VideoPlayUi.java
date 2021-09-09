package com.soft.method.ui;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * video播放UI
 *
 * @author suphowe
 */
public class VideoPlayUi extends JPanel {

    public VideoPlayUi(URL mediaURL) {
        //Use a BorderLayout.
        setLayout(new BorderLayout());
        Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
        try {
            Player mediaPlayer = Manager.createRealizedPlayer(mediaURL);
            Component video = mediaPlayer.getVisualComponent();
            Component controls = mediaPlayer.getControlPanelComponent();

            if (video != null) {
                add(video, BorderLayout.CENTER);
            }
            if (controls != null) {
                add(controls, BorderLayout.SOUTH);
            }
            mediaPlayer.start();
        } catch (NoPlayerException e) {
            JOptionPane.showMessageDialog(getRootPane(), "No media player found!");
        } catch (CannotRealizeException e) {
            JOptionPane.showMessageDialog(getRootPane(), "Can not realize media player!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(getRootPane(), "Error reading from the source!");
        }
    }
}
