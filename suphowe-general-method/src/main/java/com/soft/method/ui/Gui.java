package com.soft.method.ui;

import com.soft.method.dll.JnaDll;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 读取dll测试类UI
 * @author suphowe
 */
public class Gui {

    private JFrame frame;
    private JTextField tfb;
    private JTextField tfa;
    private JTextField tfadd;
    private JTextField tfn;
    private JTextField tffactorial;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Gui window = new Gui();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Gui() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton bntcompute = new JButton("compute");
        bntcompute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfadd.setText(""+ JnaDll.INSTANCEDLL.add(Integer.valueOf(tfb.getText()), Integer.valueOf(tfa.getText())));
                tffactorial.setText(""+ JnaDll.INSTANCEDLL.factorial(Integer.valueOf(tfn.getText())));
            }
        });
        bntcompute.setBounds(286, 180, 93, 23);
        frame.getContentPane().add(bntcompute);

        tfb = new JTextField();
        tfb.setText("3");
        tfb.setBounds(171, 111, 66, 21);
        frame.getContentPane().add(tfb);
        tfb.setColumns(10);

        tfa = new JTextField();
        tfa.setText("2");
        tfa.setBounds(74, 111, 66, 21);
        frame.getContentPane().add(tfa);
        tfa.setColumns(10);

        tfadd = new JTextField();
        tfadd.setEditable(false);
        tfadd.setBounds(286, 111, 66, 21);
        frame.getContentPane().add(tfadd);
        tfadd.setColumns(10);

        tfn = new JTextField();
        tfn.setText("10");
        tfn.setBounds(74, 142, 66, 21);
        frame.getContentPane().add(tfn);
        tfn.setColumns(10);

        tffactorial = new JTextField();
        tffactorial.setEditable(false);
        tffactorial.setBounds(286, 142, 66, 21);
        frame.getContentPane().add(tffactorial);
        tffactorial.setColumns(10);
    }
}
