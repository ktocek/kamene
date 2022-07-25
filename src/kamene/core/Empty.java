package kamene.core;

public class Empty extends Tile {

    @Override
    public String toString() {
        if (this.getState() == State.OPEN) {
            return " ";
        }
        return super.toString();
    }
}
