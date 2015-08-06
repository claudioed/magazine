package domain.event.barcode;

public class PlainBarcode {

    private final String plainBarcode;

    private PlainBarcode(String plainBarcode) {
        this.plainBarcode = plainBarcode;
    }

    public static PlainBarcode newPlainBarcode(String plainBarcode) {
        return new PlainBarcode(plainBarcode);
    }
    
    public String barcode() {
        return this.plainBarcode.substring(0, 8);
    }

    public String plainBarcode() {
        return this.plainBarcode;
    }

    public String edition() {
        return this.plainBarcode.substring(8, this.plainBarcode.length());
    }

}