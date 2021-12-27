package com.world;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonsterAI extends CreatureAI {
    private CreatureFactory factory;

    public MonsterAI(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
    }

    @Override
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
        }
    }

    @Override
    public void run(){
        while(this.creature.hp()>0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random rand = new Random();
            switch(rand.nextInt(4)){
                case 0:
                    this.creature.moveBy(1,0);
                    break;
                case 1:
                    this.creature.moveBy(-1,0);
                    break;
                case 2:
                    this.creature.moveBy(0,1);
                    break;
                case 3:
                    this.creature.moveBy(0,-1);
                    break;
            }
        }
    }

}
