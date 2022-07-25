package kamene;

import java.util.*;

public class BestTimes implements Iterable<BestTimes.PlayerTime> {
    private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

    public Iterator<PlayerTime> iterator() {
        return playerTimes.iterator();
    }

    public void addPlayerTime(String name, int time) {
        playerTimes.add(new PlayerTime(name, time));
        Collections.sort(playerTimes);
    }

    public String toString() {
        Formatter f = new Formatter();
        for (PlayerTime pt : playerTimes)
            f.format(pt.getName() + "%02d", pt.getTime());
        return f.toString();
    }

    public static class PlayerTime implements Comparable<PlayerTime> {
        private final String name;
        private final int time;

        public PlayerTime(String name, int time) {
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public int getTime() {
            return time;
        }

        @Override
        public int compareTo(PlayerTime o) {
            return Integer.compare(time, o.time);
        }
    }
}
