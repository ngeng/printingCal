import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestCalculator {
    Calculator calculator = new Calculator();

    @Test
    public void testSetUnitPrice() {
        assertEquals(15, calculator.setUnitPrice(false, false));
        assertEquals(25, calculator.setUnitPrice(true, false));
        assertEquals(10, calculator.setUnitPrice(false, true));
        assertEquals(20, calculator.setUnitPrice(true, true));
    }

    @Test
    public void testCalculateJobPrice() {
        assertEquals(10, calculator.calculateJobPrice(1,1,true));
    }
}
