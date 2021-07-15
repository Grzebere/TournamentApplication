package com.sda.tournamentApp.model;

public enum Category {
    BOARDGAMES("Board games"),
    BASKETBALL("Basketball");

    private final String displayValue;

    private Category(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
