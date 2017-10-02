import model.ColourScheme;
import model.PageType;
import model.PriceList;
import model.UnitPrice;

public class Calculator {

    public int calculateUnitPrice(ColourScheme colourScheme, PageType pageType) {
        for(int i=0; i< PriceList.getInstance().getPriceList().size(); i++) {
            UnitPrice item = PriceList.getInstance().getPriceList().get(i);
            if(item.getColourScheme()==colourScheme && item.getPageType()==pageType) {
                return item.getUnitPrice();
            }
        }
        return 0;
    }


    public int calculateJobPrice(int totalPageCount, int colouredPageCount, PageType pageType) {
        return calculateUnitPrice(ColourScheme.COLOUR, pageType)*colouredPageCount
                + calculateUnitPrice(ColourScheme.BW, pageType)*(totalPageCount-colouredPageCount);
    }
}
