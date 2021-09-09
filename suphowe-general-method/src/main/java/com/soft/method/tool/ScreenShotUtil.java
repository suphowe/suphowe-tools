package com.soft.method.tool;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 截图工具
 *
 * @author suphowe
 */
public class ScreenShotUtil extends JFrame implements Serializable {

    private static final long serialVersionUID = -2062674403029145745L;

    int orgx, orgy, endx, endy;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    BufferedImage image;
    BufferedImage tempImage;
    BufferedImage saveImage;
    Graphics g;

    @Override
    public void paint(Graphics g) {
        //缩放因子和偏移量
        RescaleOp ro = new RescaleOp(0.8f, 0, null);
        tempImage = ro.filter(image, null);
        g.drawImage(tempImage, 0, 0, this);
    }

    public ScreenShotUtil() {
        snapshot();
        setVisible(true);
        //setSize(d);//最大化窗口
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                orgx = e.getX();
                orgy = e.getY();
            }
        });
        //鼠标运动监听器
        this.addMouseMotionListener(new MouseMotionAdapter() {
            //鼠标拖拽事件
            @Override
            public void mouseDragged(MouseEvent e) {
                endx = e.getX();
                endy = e.getY();
                g = getGraphics();
                g.drawImage(tempImage, 0, 0, ScreenShotUtil.this);
                int x = Math.min(orgx, endx);
                int y = Math.min(orgy, endy);
                //加上1，防止width,height为0
                int width = Math.abs(endx - orgx) + 1;
                int height = Math.abs(endy - orgy) + 1;
                g.setColor(Color.BLUE);
                g.drawRect(x - 1, y - 1, width + 1, height + 1);
                //减1，加1都是为了防止图片将矩形框覆盖掉
                saveImage = image.getSubimage(x, y, width, height);
                g.drawImage(saveImage, x, y, ScreenShotUtil.this);
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            //按键释放
            public void keyReleased(KeyEvent e) {
                //按Esc键退出
                if (e.getKeyCode() == 27) {
                    saveToFile();
                    System.exit(0);
                }
            }
        });
    }

    public void saveToFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String name = sdf.format(new Date());
        File path = FileSystemView.getFileSystemView().getHomeDirectory();
        String format = "bmp";
        File f = new File(path + File.separator + name + "." + format);
        try {
            ImageIO.write(saveImage, format, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void snapshot() {
        try {
            Robot robot = new Robot();
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            image = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height));

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //全屏运行
        ScreenShotUtil rd = new ScreenShotUtil();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
        gd.setFullScreenWindow(rd);
    }
}
