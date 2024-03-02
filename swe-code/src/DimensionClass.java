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
}
