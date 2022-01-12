package com;

import com.world.*;
import com.screen.*;
import com.asciiPanel.AsciiPanel;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class NewClient implements Screen{
    private World world;
    private Creature player;
    private Creature player2;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    public Creature[] creaturelist = new Creature[4];

    public NewClient(int flag) throws IOException {
        this.screenWidth = 40;
        this.screenHeight = 21;
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(this.world);
        createCreatures(creatureFactory);
    }

    private void createCreatures(CreatureFactory creatureFactory) {
        this.player = creatureFactory.newPlayer(this.messages);
        this.player2 = creatureFactory.newPlayer(this.messages);
        creaturelist = new Creature[4];
        for (int i = 0; i < 4; i++) {
            creaturelist[i] = creatureFactory.newMonster();
        }
    }

    private void createWorld() {
        world = new WorldBuilder(50, 30).makeCaves().build();
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        // Show terrain
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                if (player.canSee(wx, wy)) {
                    terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                } else {
                    terminal.write(world.glyph(wx, wy), x, y, Color.DARK_GRAY);
                }
            }
        }
        // Show creatures
        for (Creature creature : world.getCreatures()) {
            if (creature.x() >= left && creature.x() < left + screenWidth && creature.y() >= top
                    && creature.y() < top + screenHeight) {
                if (player.canSee(creature.x(), creature.y())) {
                    terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
                }
            }
        }
        // Creatures can choose their next action now
        world.update();
    }

    @Override
    public Screen displayOutput(AsciiPanel terminal) {
        // Terrain and creatures
        readthetxt();
        displayTiles(terminal, getScrollX(), getScrollY());
        // Player
        terminal.write(player.glyph(), player.x() - getScrollX(), player.y() - getScrollY(), player.color());
        // Stats
        String stats = String.format("%3d/%3d hp", player.hp(), player.maxHP());
        terminal.write(stats, 1, 21);
        return this;
    }


    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        String s = "";
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.moveBy(-1, 0);
                s = "A";
                break;
            case KeyEvent.VK_RIGHT:
                player.moveBy(1, 0);
                s = "D";
                break;
            case KeyEvent.VK_UP:
                player.moveBy(0, -1);
                s = "W";
                break;
            case KeyEvent.VK_DOWN:
                player.moveBy(0, 1);
                s = "D";
                break;
        }
        return this;
    }
    
    public int getScrollX() {
        return Math.max(0, Math.min(player.x() - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.y() - screenHeight / 2, world.height() - screenHeight));
    }

    public void readthetxt() {
        try {
            File file = new File("saveai.txt");
            FileInputStream fileinput = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileinput);
            BufferedReader br = new BufferedReader(reader);
            int[] a = new int[10];
            String line = "";
            for (int i = 0; i < 10; i++) {
                line = br.readLine();
                a[i] = Integer.valueOf(line).intValue();
            }
            for (int i = 0; i < 4; i++) {
                if (a[i * 2] != -1) {
                    creaturelist[i].setX(a[i * 2]);
                    creaturelist[i].setY(a[i * 2 + 1]);
                }
                else {
                    creaturelist[i].modifyHP(-100);
                }
            }
            player2.setX(a[8]);
            player2.setY(a[9]);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
