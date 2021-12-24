/*
 * Copyright (C) 2015 Winterstorm
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
package world;

/**
 *
 * @author Winterstorm
 */
public class FungusAI extends CreatureAI {

    private CreatureFactory factory;

    public static int spores = 5;
    public static double spreadchance = 0.01;

    public FungusAI(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public void onUpdate() {
        movearound();
    }

    private void movearound() {
        int direc = (int) (Math.random() * 3);
        int newx = creature.x();
        int newy = creature.y();
        switch (direc) {
        case 0:
            newx += 1;
            break;
        case 1:
            newx -= 1;
            break;
        case 2:
            newy += 1;
            break;
        case 3:
            newy -= 1;
            break;
        default:
            break;
        }
        if (!creature.canEnter(newx, newy)) {
            return;
        }
        Creature child = this.factory.newFungus();
        child.setX(newx);
        child.setY(newy);
        int hp = creature.hp();
        creature.modifyHP(-hp);
    }
}
