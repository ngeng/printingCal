package model;

import static java.lang.String.format;

/**
 * Created by Aimee on 2/10/2017.
 */
public class UnitPrice {
    private PageSize pageSize = PageSize.A4;
    private PageType pageType = PageType.SINGLE;
    private ColourScheme colourScheme = ColourScheme.COLOUR;
    private int unitPrice = 0;

    public PageSize getPageSize() { return pageSize; }
    public PageType getPageType() { return pageType; }
    public ColourScheme getColourScheme() { return colourScheme; }
    public int getUnitPrice() { return unitPrice; }

    public void setPageSize(PageSize value) { this.pageSize = value; }
    public void setPageType(PageType value) { this.pageType = value; }
    public void setColourScheme(ColourScheme value) { this.colourScheme = value; }
    public void setUnitPrice(int value) { this.unitPrice = value; }

    @Override
    public String toString() {
        return "\n" +
                "UnitPrice: \n" +
                format("\t pageSize: %s\n", pageSize) +
                format("\t pageType: %s\n", pageType) +
                format("\t colourScheme: %s\n", colourScheme) +
                format("\t unitPrice: %s", unitPrice);
    }
}
