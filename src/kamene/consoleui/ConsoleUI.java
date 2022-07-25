package kamene.consoleui;

import kamene.Puzzle;
import kamene.Settings;
import kamene.core.Field;
import kamene.core.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements UserInterface {

    private Field field;
    private static final Pattern PATTERN = Pattern.compile("(x|n|a|w|d|s|X|N|A|W|D|S)");
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        System.out.println("Enter game difficulty:");
        System.out.println("(1) BEGINNER, (2) INTERMEDIATE, (3) EXPERT, (ENTER) DEFAULT");
        String level = readLine();
        if (level != null && !level.equals("")) {
            try {
                int intLevel = Integer.parseInt(level);
                Settings s = switch (intLevel) {
                    case 2 -> Settings.INTERMEDIATE;
                    case 3 -> Settings.EXPERT;
                    default -> Settings.BEGINNER;
                };
                Puzzle.getInstance().setSetting(s);
                this.field = new Field(s.getRowCount(), s.getColumnCount());
            } catch (NumberFormatException e) {
                //empty
            }
        }
        do {
            System.out.println("Time: " + Puzzle.getInstance().getPlayingSeconds());
            update();
            processInput();
            if (field.getState() == GameState.SOLVED) {
                System.out.println("YOU WON!!");
                System.exit(0);
            }
        } while (true);
    }

    @Override
    public void update() {

        for (int row = 0; row < field.getRowCount(); row++) {
            for (int col = 0; col < field.getColumnCount(); col++) {
                System.out.printf("%4s", field.getTile(row, col));
            }
            System.out.println();
        }
        System.out.println("Please enter your selection <X> EXIT, <N> NEW GAME");
        System.out.println("<W> Up, <S> Down, <A> Left, <D> Right :");
    }

    private void processInput() {
        String line = readLine();

        try {
            handleInput(line);
        } catch (WrongFormatException ex) {
            System.out.println(ex.getMessage());
        }


    }

    void handleInput(String input) throws WrongFormatException {

        Matcher matcher = PATTERN.matcher(input);

        if (matcher.matches()) {

            if (matcher.group(1).toLowerCase().equals("x")) {
                System.exit(0);
            }
            if (matcher.group(1).toLowerCase().equals("n")) {
                newGameStarted(new Field(field.getRowCount(), field.getColumnCount()));
            }

            for (int row = 0; row < field.getRowCount(); row++) {
                for (int col = 0; col < field.getColumnCount(); col++) {
                    if (field.getTile(row, col).equals(field.getEmpty())) {
                        switch (matcher.group(1).toLowerCase()) {
                            case "w" -> {
                                if (row < (field.getRowCount() - 1)) field.goUp(row, col);
                            }
                            case "s" -> {
                                if (row > 0) field.goDown(row, col);
                            }
                            case "a" -> {
                                if (col < (field.getColumnCount() - 1)) field.goLeft(row, col);
                            }
                            case "d" -> {
                                if (col > 0) field.goRight(row, col);
                            }
                        }
                    }
                }
            }


        } else throw new WrongFormatException("Bad input!");
    }

}
