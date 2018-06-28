import main.util.Calculator;
import main.model.ColourScheme;
import main.model.PageType;
import main.model.PriceList;
import org.junit.BeforeClass;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCalculator {

    Calculator calculator = new Calculator();

    @BeforeClass
    public static void testLoadPrice() {
        PriceList list = null;
        Yaml yaml = new Yaml();
        try(InputStream in = ClassLoader.getSystemResourceAsStream("UnitPrice.yaml")) {
            list = yaml.loadAs(in, PriceList.class);
            PriceList.getInstance().setPriceList(list.getPriceList());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testSetUnitPrice() {
        assertEquals(15, calculator.calculateUnitPrice(ColourScheme.BW, PageType.SINGLE));
        assertEquals(25, calculator.calculateUnitPrice(ColourScheme.COLOUR, PageType.SINGLE));
        assertEquals(10, calculator.calculateUnitPrice(ColourScheme.BW, PageType.DOUBLE));
        assertEquals(20, calculator.calculateUnitPrice(ColourScheme.COLOUR, PageType.DOUBLE));
    }

    @Test
    public void testCalculateJobPrice() {
        assertEquals(20, calculator.calculateJobPrice(1,1,PageType.DOUBLE));
    }
}
