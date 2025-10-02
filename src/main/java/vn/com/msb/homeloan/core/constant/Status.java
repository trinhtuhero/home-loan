package vn.com.msb.homeloan.core.constant;

public enum Status {

    ACTIVE(1), INACTIVE(0);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
