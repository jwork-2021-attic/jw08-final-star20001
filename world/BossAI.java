package world;
import java.util.Random;

public class BossAI extends CreatureAI {

    private Creature player;

    public BossAI(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

    @Override
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
        }
    }

    public void onUpdate() {
    }

    private int getdirection(int x, int tx) {
        if (x == tx) {
            return 0;
        }
        if (x < tx) {
            return 1;
        }
        return -1;
    }
    @Override
    public void run(){
        while(this.creature.hp()>0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int dx, dy;
            dx = getdirection(this.creature.x(), player.x());
            dy = getdirection(this.creature.y(), player.y());
            if (dx == 0 || dy == 0) {
                this.creature.moveBy(dx, dy);
            }
            else {
                Random rand = new Random();
                switch (rand.nextInt(2)) {
                case 0:
                    this.creature.moveBy(dx, 0);
                    break;
                case 1:
                    this.creature.moveBy(0, dy);
                    break;
                }
            }
        }
    }
}
