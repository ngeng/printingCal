public class Calculator {

    public int setUnitPrice(boolean isColoured, boolean isDoubleSided) {
        int pricePerPage = 0;
        if(!isColoured && !isDoubleSided) {
            pricePerPage = 15;
        } else if(isColoured && !isDoubleSided) {
            pricePerPage = 25;
        } else if(!isColoured && isDoubleSided) {
            pricePerPage = 10;
        } else if(isColoured && isDoubleSided) {
            pricePerPage = 20;
        }
        return pricePerPage;
    }

    public int calculateJobPrice(int totalPageCount, int colouredPageCount, boolean isDoubleSided) {
        return setUnitPrice(true, isDoubleSided)*colouredPageCount
                + setUnitPrice(false, isDoubleSided)*(totalPageCount-colouredPageCount);
    }
}
