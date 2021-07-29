package latrobesafety.mad.security;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    /*@Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }*/

    @Test
    public void getMessage() {
        //MainActivity mainActivity = new MainActivity();
        Date now = new Date();
        Request request = new Request("Alicia",1,"unit_TESTING",now);
        assertEquals("unit_TESTING", request.getMessage());
    }
}