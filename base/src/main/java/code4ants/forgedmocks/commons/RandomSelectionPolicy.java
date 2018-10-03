package code4ants.forgedmocks.commons;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomSelectionPolicy implements SelectionPolicy<Integer> {
    private List<Integer> remainingPorts;

    public RandomSelectionPolicy(int from, int to) {
        if (to < from) {
            throw new IllegalArgumentException("Invalid range parameters. Destination must be greater than source");
        }

        remainingPorts = IntStream.range(from, to + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(remainingPorts);
    }

    @Override
    public boolean hasNext() {
        return !remainingPorts.isEmpty();
    }

    @Override
    public Integer next() {
        if (remainingPorts.isEmpty()) {
            throw new IllegalStateException("No more options left for selection");
        }
        return remainingPorts.remove(0);
    }
}
