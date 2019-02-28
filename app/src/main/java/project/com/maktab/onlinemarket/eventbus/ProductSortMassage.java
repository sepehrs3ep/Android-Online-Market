package project.com.maktab.onlinemarket.eventbus;

public class ProductSortMassage {
    private int enumIndex;

    public ProductSortMassage(int enumIndex) {
        this.enumIndex = enumIndex;
    }

    public int getEnumIndex() {
        return enumIndex;
    }

    public void setEnumIndex(int enumIndex) {
        this.enumIndex = enumIndex;
    }
}
