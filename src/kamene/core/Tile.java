package kamene.core;

public class Tile {
    public enum State {
        OPEN
    }

    private State state = State.OPEN;

    public State getState() {
        return state;
    }

}
