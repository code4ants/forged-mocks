package code4ants.forgedmocks.commons;

public class RoundRobinSelectionPolicy implements SelectionPolicy<Integer> {
    private final int from;
    private final int to;
    private int current;

    public RoundRobinSelectionPolicy(int from, int to) {
        if (to < from) {
            throw new IllegalArgumentException("Invalid range parameters. Destination must be greater than source");
        }

        this.from = from;
        this.to = to;
        this.current = this.from;
    }

    @Override
    public boolean hasNext() {
        return this.current < to;
    }

    @Override
    public Integer next() {
        if (this.current < this.to) {
            return this.current++;
        }
        throw new IllegalStateException("No more options left for selection");
    }
}
