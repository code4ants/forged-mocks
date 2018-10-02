package code4ants.forgedmocks.http;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class LauncherTest {

    @Test
    public void testThatTestingWorks() {
        assertThat(new Object().hashCode(), is(not(equalTo(0))));
    }
}