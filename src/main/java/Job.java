import model.ColourScheme;
import model.PageSize;
import model.PageType;

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
               pageType==PageType.DOUBLE?true:false);
    }

    public void printDetails() {
        System.out.println("Details: "+totalPageCount+" pages, "+colouredPageCount+" coloured pages, "
                +getPageType()+" sized."
                +" totalPrice: "+totalPrice+" cents.");
    }
}
