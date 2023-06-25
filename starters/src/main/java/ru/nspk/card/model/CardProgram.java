package ru.nspk.card.model;

public enum CardProgram {
    GOLD("48211212"),
    SUPREME("22000101");

    private final String programPrefix;

    CardProgram(String programPrefix) {
        this.programPrefix = programPrefix;
    }

    public String getProgramPrefix() {
        return programPrefix;
    }
}
