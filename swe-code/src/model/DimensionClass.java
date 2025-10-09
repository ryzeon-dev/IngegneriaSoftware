package model;

public class DimensionClass {
    public final String type;

    DimensionClass(String type) {
        this.type = type;
    }

    public static boolean validate(String class_) {
        int number = class_.charAt(0) - 48;
        char character = class_.charAt(1);

        if (number > 5 || number < 0) {
            return false;
        }

        if (character < 'A' || character > 'F') {
            return false;
        }

        return true;
    }

    public String toString() {
        return this.type;
    }

    public boolean isCompatible(DimensionClass other) {
        String selfRepr = this.toString();
        int selfNumber = selfRepr.charAt(0);
        int selfLetter = selfRepr.charAt(1);

        String otherRepr = other.toString();
        int otherNumber = otherRepr.charAt(0);
        int otherLetter = otherRepr.charAt(1);

        if (selfNumber <= otherNumber && selfLetter <= otherLetter) {
            return true;
        }
        return false;
    }

    public static DimensionClass fromString(String dimClass){
        if (DimensionClass.validate(dimClass)) {
            return new DimensionClass(dimClass);

        } else {
            return null;
        }
    }
}
