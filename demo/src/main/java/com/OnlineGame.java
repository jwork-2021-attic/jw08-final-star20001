package com;
import com.NewClient;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

import com.asciiPanel.AsciiFont;
import com.asciiPanel.AsciiPanel;
import com.screen.Screen;
import com.screen.StartScreen;

public class OnlineGame extends JFrame implements KeyListener{
    private AsciiPanel terminal;
    private Screen screen;

    public OnlineGame() {
        super();
        terminal = new AsciiPanel(80, 32, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        try {
            screen = new NewClient(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        addKeyListener(this);
        repaint();
    }
    @Override
    public void repaint() {
        terminal.clear();
        screen=screen.displayOutput(terminal);
        super.repaint();
    }

    /**
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        try {
            screen = screen.respondToUserInput(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        repaint();
    }

    /**
     *
     * @param e
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     *
     * @param e
     */
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        OnlineGame app1 = new OnlineGame();
        app1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app1.setVisible(true);
        new Thread(
                () -> {
                    while (true) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        app1.repaint();
                    }
                }).start();

    }
}
