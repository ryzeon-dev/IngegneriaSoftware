package model;

public enum DimensionClass {
    C4, C3, E4;

    public String toString() {
        if (this.equals(DimensionClass.C3)) {
            return "3C";

        } else if (this.equals(DimensionClass.C4)) {
            return "4C";

        } else if (this.equals(DimensionClass.E4)) {
            return "4E";

        } else {
            return "";
        }
    }

    //Aircraft is lhs Airport is rhs
    //Check if an Aircraft(lhs) can land on Airport(rhs) 
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
        DimensionClass dimensionClass;
        switch (dimClass.trim()) {
            case "3C":
                dimensionClass = DimensionClass.C3;
                break;

            case "4C":
                dimensionClass = DimensionClass.C4;
                break;

            case "4E":
                dimensionClass = DimensionClass.E4;
                break;

            default:
                dimensionClass = DimensionClass.E4;
                break;
        }
            return dimensionClass;
    }
}
