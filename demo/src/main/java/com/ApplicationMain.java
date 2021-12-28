/*
 * Copyright (C) 2015 Aeranythe Echosong
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

import com.asciiPanel.AsciiFont;
import com.asciiPanel.AsciiPanel;
import com.screen.Screen;
import com.screen.StartScreen;

/**
 *
 * @author Aeranythe Echosong
 */
public class ApplicationMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;

    public ApplicationMain() {
        super();
        terminal = new AsciiPanel(80, 32, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        screen = new StartScreen();
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
            // TODO Auto-generated catch block
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
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

        new Thread(
                ()->{
                        while (true){
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            app.repaint();
                        }
                }
        ).start();
        
    }
}
