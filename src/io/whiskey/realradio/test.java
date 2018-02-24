/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.whiskey.realradio;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/** @see http://stackoverflow.com/questions/3617326 */
public class test {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setSize(300, 300);
        JLabel label = new JLabel("This is text!!!");
        frame.add(label);
        frame.setVisible(true);

        final int labelWidth = 300;
        final AtomicInteger labelPadding = new AtomicInteger();
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setBorder(new EmptyBorder(0, labelPadding.getAndIncrement() % labelWidth, 0, 0));
            }
        });
        timer.start();
    }
}

/** Side-scroll n characters of s. */
class MarqueePanel extends JPanel implements ActionListener {

    private static final int RATE = 12;
    private final Timer timer = new Timer(1000 / RATE, this);
    private final JLabel label = new JLabel();
    private final String s;
    private final int n;
    private int index;

    public MarqueePanel(String s, int n) {
        if (s == null || n < 1) {
            throw new IllegalArgumentException("Null string or n < 1");
        }
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(' ');
        }
        this.s = sb + s + sb;
        this.n = n;
        label.setFont(new Font("Arial", Font.ITALIC, 36));
        label.setText(sb.toString());
        this.add(label);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        index++;
        if (index > s.length() - n) {
            index = 0;
        }
        label.setText(s.substring(index, index + n));
    }
}