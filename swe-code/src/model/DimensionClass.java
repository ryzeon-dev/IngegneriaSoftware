package model;

import system.ManagementSystem;

public enum DimensionClass {
    C4, C3, E4;

    @Override
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

    public boolean isCompatible(DimensionClass other) {
        String selfRepr = this.toString();
        int selfNumber = selfRepr.charAt(0);
        int selfLetter = selfRepr.charAt(1);

        String otherRepr = other.toString();
        int otherNumber = otherRepr.charAt(0);
        int otherLetter = otherRepr.charAt(1);

        if (selfNumber >= otherNumber && selfLetter >= otherLetter) {
            return true;
        }
        return false;
    }
}
