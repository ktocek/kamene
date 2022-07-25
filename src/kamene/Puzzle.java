package kamene;

import kamene.consoleui.ConsoleUI;
import kamene.core.Field;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Puzzle {

    private ConsoleUI userInterface;

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private static Puzzle instance;

    private Settings setting;

    private long startMillis;

    private BestTimes bestTimes = new BestTimes();

    public BestTimes getBestTimes() {
        return bestTimes;
    }

    public int getPlayingSeconds() {
        return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startMillis);
    }

    public void setSetting(Settings setting) {
        this.setting = setting;
        this.setting.save();
    }

    public Settings getSetting() {
        return this.setting;
    }

    public static Puzzle getInstance() {
        if (instance == null) {
            new Puzzle();
        }
        return instance;
    }

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    private Puzzle() {
        instance = this;
        userInterface = new ConsoleUI();
        System.out.println("Hello enter your name: ");
        String name = readLine();
        setting = Settings.load();
        setSetting(setting);
        startMillis = System.currentTimeMillis();
        bestTimes.addPlayerTime(name, getPlayingSeconds());

        try
        {
            String filename= "BestTimes.txt";
            FileWriter fw = new FileWriter(filename,true);
            fw.write(String.valueOf(bestTimes));
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

        Field field = new Field(
                setting.getRowCount(),
                setting.getColumnCount()
        );
        userInterface.newGameStarted(field);
    }

    public static void main(String[] args) {
        Puzzle.getInstance();
    }
}
