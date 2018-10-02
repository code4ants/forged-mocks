package code4ants.forgedmocks.commons;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

public class DummyTest {

    @Test
    public void name() {
        assertThat(Dummy.reason(), is(not(nullValue())));
    }
}