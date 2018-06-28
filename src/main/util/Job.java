package main.util;

import main.model.ColourScheme;
import main.model.PageSize;
import main.model.PageType;

public class Job {
    private PageSize pageSize;
    private PageType pageType;
    private ColourScheme colourScheme;
    private int totalPageCount, colouredPageCount;
    private int totalPrice;

    public Job(int totalPageCount, int colouredPageCount, PageType pageType) {
        this.totalPageCount = totalPageCount;
        this.colouredPageCount = colouredPageCount;
        this.pageType = pageType;
        setTotalPrice();
    }

    public String getPageSize() {
        return pageSize.toString();
    }
    public String getPageType() {
        return pageType.toString();
    }
    public String getColourScheme() {
        return colourScheme.toString();
    }
    public int getTotalPageCount() {
        return totalPageCount;
    }
    public int getColouredPageCount() {
        return colouredPageCount;
    }
    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice() {
       this.totalPrice = new Calculator().calculateJobPrice(totalPageCount, colouredPageCount,
               pageType);
    }

    public void printDetails() {
        System.out.println("Job details: "+totalPageCount+" pages, "+colouredPageCount+" coloured pages, "
                +(pageType==PageType.SINGLE?"single":"double")+" sized."
                +" Price: "+totalPrice+" cents.");
    }
}
