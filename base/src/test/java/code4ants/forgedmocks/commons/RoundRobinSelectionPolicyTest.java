package code4ants.forgedmocks.commons;

import code4ants.forgedmocks.commons.RoundRobinSelectionPolicy;
import code4ants.forgedmocks.commons.SelectionPolicy;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class RoundRobinSelectionPolicyTest {

    @Test(expected = RuntimeException.class)
    public void testInvalidRange() {
        new RoundRobinSelectionPolicy(1, 0);
    }

    @Test
    public void testNextValuesComeInOrder() {
        SelectionPolicy policy = new RoundRobinSelectionPolicy(0, 10);
        assertThat(policy.next(), is(equalTo(0)));
        assertThat(policy.next(), is(equalTo(1)));
    }

    @Test(expected = IllegalStateException.class)
    public void testOverflowThrowsException() {
        SelectionPolicy policy = new RoundRobinSelectionPolicy(0, 2);
        assertThat(policy.next(), is(equalTo(0)));
        assertThat(policy.next(), is(equalTo(1)));
        assertThat(policy.next(), is(equalTo(2)));
        assertThat(policy.next(), is(equalTo(0)));
    }
}