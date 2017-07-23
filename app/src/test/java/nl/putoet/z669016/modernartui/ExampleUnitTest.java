package nl.putoet.z669016.modernartui;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private MainActivity main = null;

    @Before
    public void setup() {
        main = new MainActivity();
    }

    @Test
    public void testAddShiftedChangeToColor() {
        assertEquals(1, main.addShiftedChangeToColor(1, 0, 0));
        assertEquals(3, main.addShiftedChangeToColor(1, 1, 1));
        assertEquals(5, main.addShiftedChangeToColor(1, 2, 1));
    }

    @Test
    public void testAddBlueToColor() {
        assertEquals(0x000001, main.addBlueToColor(1, 0));
    }

    @Test
    public void testAddGreenToColor() {
        assertEquals(0x000101, main.addGreenToColor(1, 1));
    }

    @Test
    public void testAddRedToColor() {
        assertEquals(0x010001, main.addRedToColor(1, 1));
    }
}