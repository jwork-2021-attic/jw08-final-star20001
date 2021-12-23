package screen;
import asciiPanel.AsciiPanel;

public class ScoreList extends RestartScreen {
    @Override
    public Screen displayOutput(AsciiPanel terminal) {
        terminal.write("Top 10 player.", 0, 0);
        return this;
    }
}
