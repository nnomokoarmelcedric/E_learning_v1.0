package knops.dev;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RangeTest {

    @Test
    public void testGetRangeStart() {
        Range range = Range.builder().start(10).end(20).build();
        assertEquals(10, range.getRangeStart());
    }

    @Test
    public void testGetRangeEnd() {
        Range range = Range.builder().start(10).end(20).build();
        assertEquals(19, range.getRangeEnd(20));
    }

    @Test
    public void testParseHttpRangeStringWithNullInput() {
        Range range = Range.parseHttpRangeString(null, 50);
        assertEquals(0, range.getRangeStart());
        assertEquals(49, range.getRangeEnd(50));
    }

    @Test
    public void testParseHttpRangeStringWithValidInput() {
        Range range = Range.parseHttpRangeString("bytes=5-15", 50);
        assertEquals(5, range.getRangeStart());
        assertEquals(15, range.getRangeEnd(50));
    }




    @Test
    public void testParseHttpRangeStringWithInvalidInput() {
        assertThrows(NumberFormatException.class, () -> {
            Range.parseHttpRangeString("bytes=abc-15", 50);
        });
    }
}
