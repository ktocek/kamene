package kamene.core;

public class Number extends Tile {
    private final int value;

    public int getValue() {
        return value;
    }

    public Number(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (this.getState() == State.OPEN) {
            return String.valueOf(value);
        }
        return super.toString();
    }
}
