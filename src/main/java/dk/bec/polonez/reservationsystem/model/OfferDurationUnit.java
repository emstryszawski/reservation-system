package dk.bec.polonez.reservationsystem.model;

public enum OfferDurationUnit {
    MINUTE("MINUTE"),
    HOUR("HOUR"),
    DAY("DAY");

    private final String unit;

    OfferDurationUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
