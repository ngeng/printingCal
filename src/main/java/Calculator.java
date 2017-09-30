public class Calculator {

    public int setPricePerPage(boolean isColoured, boolean isDoubleSided) {
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
        return setPricePerPage(true, isDoubleSided)*colouredPageCount
                + setPricePerPage(false, isDoubleSided)*(totalPageCount-colouredPageCount);
    }
}
