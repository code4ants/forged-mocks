package code4ants.forgedmocks.http;

import code4ants.forgedmocks.commons.RandomSelectionPolicy;
import code4ants.forgedmocks.commons.SelectionPolicy;

public class PortRange {
    private final int fromPort;
    private final int toPort;
    private final SelectionPolicy<Integer> selectionPolicy;

    public PortRange(int from, int to) {
        this.fromPort = from;
        this.toPort = to;
        this.selectionPolicy = new RandomSelectionPolicy(from, to);
    }

    int nextPort() {
        return this.selectionPolicy.next();
    }
}
