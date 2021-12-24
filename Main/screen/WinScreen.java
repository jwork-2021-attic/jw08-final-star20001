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
package screen;
import java.io.IOException;
import asciiPanel.AsciiPanel;


/**
 *
 * @author Aeranythe Echosong
 */
public class WinScreen extends RestartScreen {

    private long score;
    private String result = "";

    private int flag=0;

    public WinScreen(long score) {
        this.score = score;
        if (this.result == "" && flag == 0) {
            System.out.println("Get Result");
            getresult();
            flag = 1;
            System.out.println("Got");
        }
    }

    private void getresult() {
        try {
            Client client = new Client(score);
            this.result = client.getResult();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public Screen displayOutput(AsciiPanel terminal) {
        String message = String.format("You win! Your time-cost are %d", score);
        terminal.write(message, 0, 0);
        terminal.write(result, 0, 1);
        return this;
    }

}
