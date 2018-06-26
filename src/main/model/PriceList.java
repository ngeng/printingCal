package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aimee on 2/10/2017.
 */
public class PriceList {
    private static PriceList instance = null;
    protected PriceList() {}
    public static PriceList getInstance() {
        if(instance == null) {
            instance = new PriceList();
        }
        return instance;
    }

    List<UnitPrice> priceList = new ArrayList<UnitPrice> ();

    public List<UnitPrice> getPriceList() { return priceList; }
    public void setPriceList(List<UnitPrice> value) { this.priceList = value; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<priceList.size(); i++) {
            UnitPrice unitPrice = priceList.get(i);
            sb.append( unitPrice.toString() );
        }

        return "\n" +
                "PriceList: \n" +
                sb.toString();
    }
}
